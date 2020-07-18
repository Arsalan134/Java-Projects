package Parser;

import java.util.ArrayList;

import Expression.Boolean;
import Expression.Exp;
import Expression.Number;
import Expression.Variable;
import Statement.Block;
import Statement.Expression;
import Statement.If;
import Type.Type;
import defaul.Argument;
import defaul.ArgumentList;
import defaul.Statement;
import defaul.Token;
import defaul.TokenType;

public class Parser {

	ArrayList<Token> tokens;
	private int index = 0;

	public Parser(ArrayList<Token> arrayList) {
		this.tokens = arrayList;
		while (index < tokens.size())
			parseStatement();
	}

	private void exit(String errorMessage) {
		System.err.println(errorMessage);
		System.exit(1);
	}

	private Token ignore(TokenType tokenNew) {
		Token token = peek();
		TokenType tokenOld = token.getType();
		if (tokenOld == tokenNew || (tokenNew == TokenType.BINARYOPERATOR
				&& (tokenOld == TokenType.PLUS || tokenOld == TokenType.MINUS || tokenOld == TokenType.MULT || tokenOld == TokenType.DIV))) {
			index++;
		} else
			exit("Recieved " + tokenNew + ", when expected " + tokenOld);
		return token;
	}

	private boolean isNumber2(Token token) {
		TokenType tokt = token.getType();
		if (tokt == TokenType.INT || tokt == TokenType.DOUBLE)
			return true;
		return false;
	}

	private Block parseBlock() {
		Token token = peek();
		Block block = new Block();
		while (token.getType() != TokenType.RIGHTCURLYBRACKET && token.getType() != TokenType.BREAK) {
			block.getStatements().add(parseStatement());
			token = peek();
		}
		return block;
	}

	private Variable parseDeclaration() {
		Variable var;
		Type varType = Type.Int;
		index++;
		String ident = ignore(TokenType.IDENT).getData();
		ignore(TokenType.ASSIGN);
		Expression expression = parseExpression();
		var = new Variable(ident, new Value(varType, expression.toString()));
		ignore(TokenType.SEMICOLON);
		return var;
	}

	private Expression parseExpression() {
		Token leftPart = peek();
		Token rightPart = peekElementAt(1);

		if ((isNumber2(leftPart) || leftPart.getType() == TokenType.FUNCTIONCALL || leftPart.getType() == TokenType.IDENT)
				&& rightPart.getType() == TokenType.SEMICOLON) {
			if (leftPart.getType() == TokenType.FUNCTIONCALL) {
				ignore(TokenType.FUNCTIONCALL);
				ignore(TokenType.RIGHTBRACKET);
				return new FunctionCall(leftPart.getData().substring(0, leftPart.getData().length() - 1));
			} else if (leftPart.getType() == TokenType.IDENT) {
				ignore(TokenType.IDENT);
				Variable v = new Variable(leftPart.getData(), new Value(Type.Int, null));
				if (v != null)
					return new Variable(leftPart.getData(), v.getValue());
			} else {
				index++;
				return new Number(leftPart.getData());
			}
		} else if (leftPart.getType() == TokenType.BOOLVALUE) {
			ignore(TokenType.BOOLVALUE);
			return new Boolean(leftPart.getData());

		} else if ((isNumber2(leftPart) || leftPart.getType() == TokenType.FUNCTIONCALL)
				&& (rightPart.getType() == TokenType.PLUS || rightPart.getType() == TokenType.MINUS)) {
			Expression leftExp = null;
			if (leftPart.getType() == TokenType.FUNCTIONCALL) {
				ignore(TokenType.FUNCTIONCALL);
				ignore(TokenType.RIGHTBRACKET);
				if (leftPart.getData() != null)
					leftExp = new FunctionCall(leftPart.getData().substring(0, leftPart.getData().length() - 1));
			} else {
				leftExp = new Number(peek().getData());
				index++;
			}
			return new Exp(leftExp, rightPart.getData(), parseExpression());
		}
		return null;
	}

	private Function parseFunctionDeclaration() {
		int indexOfType = peek().getPosition();
		index++;
		String name = ignore(TokenType.FUNCTIONCALL).getData();
		ArgumentList signature = parseSignature();
		ignore(TokenType.LEFTCURLYBRACKET);
		Block body = parseBlock();
		ignore(TokenType.RIGHTCURLYBRACKET);
		Function f = new Function(Type.Int, name.substring(0, name.length() - 1), signature, body, indexOfType);
		return f;
	}

	private If parseIfStatment() {
		Block ifStatement;
		Block elseStatement;
		ignore(TokenType.IF);
		ignore(TokenType.LEFTBRACKET);
		Expression condition = parseExpression();
		ignore(TokenType.RIGHTBRACKET);
		ignore(TokenType.LEFTCURLYBRACKET);
		ifStatement = parseBlock();
		ignore(TokenType.RIGHTCURLYBRACKET);
		Token token = peek();
		If ifs = null;
		if (token.getType() == TokenType.ELSE) {
			ignore(TokenType.ELSE);
			ignore(TokenType.LEFTCURLYBRACKET);
			elseStatement = parseBlock();
			ignore(TokenType.RIGHTCURLYBRACKET);
			ifs = new If(condition, ifStatement, elseStatement);
		} else
			ifs = new If(condition, ifStatement);
		return ifs;
	}

	private ArgumentList parseSignature() {
		ArgumentList signature = new ArgumentList();
		while (peek().getType() != TokenType.RIGHTBRACKET) {
			if (peek().getType() == TokenType.COMMA)
				ignore(TokenType.COMMA);
			ignore(TokenType.INTType);
			Type type = Type.Int;
			String name = ignore(TokenType.IDENT).getData();
			signature.getArguments().add(new Argument(type, name));
		}
		ignore(TokenType.RIGHTBRACKET);
		return new ArgumentList(signature);
	}

	private Statement parseStatement() {
		Token current = peek();
		Token next = peekElementAt(1);
		if (current.getType() == TokenType.FUNCTIONCALL) {
			Token name = peek();
			ignore(TokenType.FUNCTIONCALL);
			parseSignature();
			ignore(TokenType.SEMICOLON);
			return new FunctionCall(name.getData().substring(0, name.getData().length() - 1));
		} else if (current.getType() == TokenType.IF)
			return parseIfStatment();
		else if (next.getType() == TokenType.FUNCTIONCALL)
			return parseFunctionDeclaration();
		else if (next.getType() == TokenType.IDENT)
			return parseDeclaration();
		return null;
	}

	private Token peek() {
		return peekElementAt(0);
	}

	private Token peekElementAt(int offset) {
		if (index + offset < tokens.size())
			return tokens.get(index + offset);
		return null;
	}
}
