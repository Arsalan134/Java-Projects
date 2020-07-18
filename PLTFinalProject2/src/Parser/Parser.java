package Parser;

import java.util.ArrayList;
import java.util.Stack;

import Expression.Boolean;
import Expression.Exp1;
import Expression.Exp2;
import Expression.Increment;
import Expression.Number;
import Expression.Value;
import Expression.Variable;
import Statement.Assignment;
import Statement.Block;
import Statement.Expression;
import Statement.For;
import Statement.Function;
import Statement.FunctionCall;
import Statement.If;
import Statement.Program;
import Statement.Return;
import Statement.Switch;
import Type.Type;
import defaul.Comparison;
import defaul.Condition;
import defaul.Parameter;
import defaul.ParameterList;
import defaul.Statement;
import defaul.Token;
import defaul.TokenType;
import defaul.While;

public class Parser {

	Program program;
	Program programAfterInterpretation;
	ArrayList<Token> tokens;
	Stack<Scope> scopes;
	ArrayList<Function> functions;
	ParameterList currentSignature = null;
	Function currentFunction = null;
	Value returnedValue = null;
	ArrayList<String> textToConsole = new ArrayList<>();

	private int index = 0;

	public Parser(ArrayList<Token> arrayList) {
		this.program = new Program();
		this.tokens = arrayList;
		this.scopes = new Stack<>();
		this.scopes.push(new Scope());
		this.functions = new ArrayList<>();
		this.programAfterInterpretation = new Program();
	}

	private void addVar(Variable variable) {
		this.scopes.peek().getVariables().add(variable);
		print(variable + " was added to the scope");
	}

	// private Value converExpToValue(Expression exp) {
	//
	// if (exp instanceof Number) {
	// Expression num = evalExpression(exp, "state");
	// return new Value(Type.Double, num);
	// }
	//
	// print("Sooiodiosidosidosd");
	// return null;
	// }

	private Type convertTokenToType(Token tokenType) {
		if (tokenType.getType() == TokenType.INTType || tokenType.getType() == TokenType.INT)
			return Type.Int;
		else if (tokenType.getType() == TokenType.DoubleType)
			return Type.Double;
		else if (tokenType.getType() == TokenType.Void)
			return Type.Void;
		else if (tokenType.getType() == TokenType.IDENT)
			return Type.Var;

		exit("unknown type: '" + tokenType.getType() + "' from convert token to type", tokenType.getPosition());
		return null;
	}

	private void createParseTree() {
		index = 0;
		while (index < tokens.size())
			program.getStatements().add(parseStatement());

	}

	private Type DoubleOrInt(String number) {
		if (number.indexOf(',') == -1 || number.indexOf('.') == -1)
			return Type.Int;
		return Type.Double;
	}

	private Value evalExpression(Expression exp) {
		print("Evaluate expression " + exp + "  with " + exp.getClass());
		String left = "";

		if (exp instanceof Number) {
			print("Number: " + ((Number) exp).getNumber() + "\t\t Class: " + ((Number) exp).getClass());
			left = ((Number) exp).getNumber();
			return new Value(DoubleOrInt(left), left);
		}

		else if (exp instanceof Ident) {
			Ident i = (Ident) exp;
			Variable v = lookFirstVarThatFaces(i.getName());
			// return new Value(v.getType(),
			// evalExpression(v.getExpression()).getValue());

			return new Value(v.getType(), evalExpression(lookFirstVarThatFaces(v.getName()).getExpression()).getValue());
		}

		else if (exp instanceof Value) {
			return (Value) exp;
		}

		else if (exp instanceof Parameter) {
			Parameter p = (Parameter) exp;
			return new Value(evalExpression(p.getExpression()).getType(), evalExpression(p.getExpression()).getValue());
		}

		else if (exp instanceof Comparison) {
			Comparison c = (Comparison) exp;

			Value l = evalExpression(c.getLeft());
			Value r = evalExpression(c.getRight());

			Double ld = Double.parseDouble(l.getValue());
			Double rd = Double.parseDouble(r.getValue());

			if (c.getSign().equals("<")) {
				if (ld < rd)
					return new Value(Type.Boolean, "true");
				return new Value(Type.Boolean, "false");
			}

			else if (c.getSign().equals("<=")) {
				if (ld <= rd)
					return new Value(Type.Boolean, "true");
				return new Value(Type.Boolean, "false");
			}

			else if (c.getSign().equals(">")) {
				if (ld > rd)
					return new Value(Type.Boolean, "true");
				return new Value(Type.Boolean, "false");
			}

			else if (c.getSign().equals(">=")) {
				if (ld >= rd)
					return new Value(Type.Boolean, "true");
				return new Value(Type.Boolean, "false");
			}

			else if (c.getSign().equals("==")) {
				if (ld.equals(rd))
					return new Value(Type.Boolean, "true");
				else
					return new Value(Type.Boolean, "false");
			}

		}

		else if (exp instanceof Exp1) {
			Exp1 e = (Exp1) exp;
			print("Left: " + e.getLeft() + "\t\t Class: " + e.getLeft().getClass());
			print("Right: " + e.getRight() + "\t\t Class: " + e.getRight().getClass());

			left = evalExpression(e.getLeft()).getValue();
			if (e.getSign().equals("+")) {
				String vv;
				if (DoubleOrInt(left) == Type.Int)
					vv = Integer.toString(Integer.parseInt(left) + Integer.parseInt(evalExpression(e.getRight()).getValue()));
				else
					vv = Double.toString(Double.parseDouble(left) + Double.parseDouble(evalExpression(e.getRight()).getValue()));
				Value v = new Value(DoubleOrInt(left), vv);
				return v;
			} else {
				Value v;
				if (DoubleOrInt(left) == Type.Double)
					v = new Value(Type.Double, Double.toString(Double.parseDouble(left) - Double.parseDouble(evalExpression(e.getRight()).getValue())));
				else
					v = new Value(Type.Int, Integer.toString(Integer.parseInt(left) - Integer.parseInt(evalExpression(e.getRight()).getValue())));

				return v;
			}
		} else if (exp instanceof Exp2) {
			Exp2 e = (Exp2) exp;
			print("Left: " + e.getLeft() + "\t\t Class: " + e.getLeft().getClass());
			print("Right: " + e.getRight() + "\t\t Class: " + e.getRight().getClass());

			left = evalExpression(e.getLeft()).getValue();

			if (e.getSign().equals("*")) {
				String vv;
				if (DoubleOrInt(left) == Type.Int) {
					vv = Integer.toString(Integer.parseInt(left) * Integer.parseInt(evalExpression(e.getRight()).getValue()));
				} else
					vv = Double.toString(Double.parseDouble(left) * Double.parseDouble(evalExpression(e.getRight()).getValue()));
				Value v = new Value(DoubleOrInt(left), vv);
				return v;
			} else {
				Value v;
				if (DoubleOrInt(left) == Type.Double)
					v = new Value(Type.Double, Double.toString(Double.parseDouble(left) / Double.parseDouble(evalExpression(e.getRight()).getValue())));
				else
					v = new Value(Type.Int, Integer.toString(Integer.parseInt(left) / Integer.parseInt(evalExpression(e.getRight()).getValue())));

				return v;
			}

			// if (DoubleOrInt(left) == Type.Double)
			// if (e.getSign().equals("*"))
			// return new Value(DoubleOrInt(left),
			// Double.toString(Double.parseDouble(left) *
			// Double.parseDouble(evalExpression(((Exp2)
			// exp).getRight()).getValue())));
			// else
			// return new Value(DoubleOrInt(left),
			// Double.toString(Double.parseDouble(left) /
			// Double.parseDouble(evalExpression(((Exp2)
			// exp).getRight()).getValue())));
			//
			// else if (((Exp2) exp).getSign().equals("*"))
			// return new Value(Type.Int,
			// Integer.toString(Integer.parseInt(left) *
			// Integer.parseInt(evalExpression(((Exp2)
			// exp).getRight()).getValue())));
			// else
			// return new Value(Type.Int,
			// Integer.toString(Integer.parseInt(left) /
			// Integer.parseInt(evalExpression(((Exp2)
			// exp).getRight()).getValue())));

		} else if (exp instanceof Variable) {
			Variable v = (Variable) exp;
			print("KLKLKL " + v);
			return new Value(v.getType(), null);
			// return new Value(((Variable) (exp)).getType(),
			// (evalExpression((exp))).getValue());

		} else if (exp instanceof FunctionCall) {
			print("EVAL function call " + exp);
			FunctionCall f = (FunctionCall) exp;
			Function calledFunc = lookUpFunction(f.getNameOfFunction());

			if (calledFunc == null) {
				exit("Undeclared function", f.getIndex());
			}

			ParameterList formerSignature = lookUpFunction(calledFunc.getName()).getSignature();
			ParameterList temp = new ParameterList(-1);

			// evaluate each parameter
			for (Parameter parameter : f.getSignature().getParameters()) {
				temp.getParameters().add(new Parameter(evalExpression(parameter.getExpression()), parameter.getIndex()));
			}

			print("Done evaluation of each parameter");

			currentSignature = temp;

			print("assigned to current signature: " + temp);

			matchsignatures(formerSignature, temp);

			interpretStatement(calledFunc);
			return returnedValue;
		}

		exit("Problem in evalExpression", index);
		return null;
	}

	private void exit(String errorMessage, int index) {
		System.out.println(errorMessage + " at :\t" + index);
		printToConsole(errorMessage.concat(" at: " + index));
	}

	private Token ignore(TokenType tokenNew) {
		Token token = peek();
		TokenType tokenOld = token.getType();
		if (tokenOld == tokenNew || (tokenNew == TokenType.BINARYOPERATOR
				&& (tokenOld == TokenType.PLUS || tokenOld == TokenType.MINUS || tokenOld == TokenType.MULT || tokenOld == TokenType.DIV)))
			index++;
		else
			exit("Didn't see " + tokenNew + ". Instead: " + tokenOld, token.getPosition());
		// print("ignored: " + tokenNew);
		return token;
	}

	private int indexOfToken(Token token) {
		for (Token i : tokens) {
			if (token.equals(i))
				return tokens.indexOf(i);
		}
		return -1;
	}

	private void interpret() {
		print("\n\nInterpretation\n\n");

		index = 0;

		for (int i = 0; i < program.getStatements().size(); i++)
			if (program.getStatements().get(i) instanceof Function) {
				Function f = (Function) program.getStatements().get(i);
				if (f.getName().equals("main"))
					index = i;
			}

		// Collect global variables
		for (int i = 0; i < program.getStatements().size(); i++)
			if (program.getStatements().get(i) instanceof Declaration) {
				Declaration d = (Declaration) program.getStatements().get(i);
				String name = d.getVar().getName();
				Type type = d.getVar().getType();
				Expression e = d.getExpression();

				Variable v = new Variable(name, type, e, d.getIndex());
				addVar(v);
			}

		printCurrentScope();

		program.getStatements().get(index);

		interpretStatement(program.getStatements().get(index));

		print("Done Interpretation");
	}

	private void updateValueOfVariable(String name, Expression exp) {
		for (Scope scope : scopes)
			for (Variable var : scope.getVariables())
				if (var.getName().equals(name))
					var.setExpression(evalExpression(exp));
	}

	private void interpretStatement(Statement stm) {
		print("Recieved " + stm.getClass() + " : " + stm);

		if (stm instanceof Declaration) {
			print("Interpret Declaration");
			Declaration d = (Declaration) stm;
			Value value = evalExpression(d.getVar().getExpression());
			Variable var = new Variable(d.getVar().getName(), d.getVar().getType(), value, d.getIndex());
			addVar(var);
		}

		else if (stm instanceof Assignment) {
			Assignment a = (Assignment) stm;

			// find ident in scope and update it
			if (lookFirstVarThatFaces(a.getVar().getName()) != null)
				updateValueOfVariable(a.getVar().getName(), a.getExpression());
			else
				exit("Undeclared Variable: " + a.getVar().getName(), a.getIndex());

		}

		else if (stm instanceof Function) {
			print("Interpret Function " + ((Function) (stm)).getName());
			print("ADD SCOPE");

			currentFunction = (Function) stm;

			scopes.add(new Scope());

			Function f = (Function) stm;

			for (int i = 0; i < f.getSignature().getParameters().size(); i++) {
				print(f.getSignature().getParameters().get(i));
				Token t = peek();
				String name = ((Variable) (f.getSignature().getParameters().get(i).getExpression())).getName();
				Type type = ((Variable) (f.getSignature().getParameters().get(i).getExpression())).getType();

				Number e = new Number(((Value) (currentSignature.getParameters().get(i).getExpression())).getValue(),
						((Value) (currentSignature.getParameters().get(0).getExpression())).getType(), -1);

				Variable v = new Variable(name, type, e, t.getPosition());

				if (lookFirstVarThatFaces(name) != null)
					updateValueOfVariable(name, e);
				else
					addVar(v);
			}

			interpretStatement(f.getBody());

			print("POP SCOPE : " + scopes.peek() + " because function: " + f.getName() + " ends");
			scopes.pop();
		}

		else if (stm instanceof Block) {
			print("Interpret Block");
			Block b = (Block) stm;
			for (Statement s : b.getStatements()) {
				print(s);
				interpretStatement(s);
			}
		}

		else if (stm instanceof FunctionCall) {
			print("Interpret FunctionCall");
			FunctionCall f = (FunctionCall) stm;

			if (f.getNameOfFunction().equals("print")) {
				Expression e;

				if (f.getSignature().getParameters().size() > 0)
					e = evalExpression(f.getSignature().getParameters().get(0).getExpression());
				else
					e = new Value(Type.Void, " ");

				if (e instanceof Number) {
					Number num = (Number) e;
					printToConsole(num.getNumber());
				} else if (e instanceof Variable) {
					Variable v = (Variable) e;
					printToConsole(evalExpression(v.getExpression()).getValue());
				} else if (e instanceof Value) {
					Value v = (Value) e;
					printToConsole(v.getValue());
				} else {
					System.err.println("PROBLEMMM " + e.getClass());
				}

			} else {
				// check whether exist
				// match signature with function call
				Function calledFunc = lookUpFunction(f.getNameOfFunction());
				ParameterList formerSignature = lookUpFunction(calledFunc.getName()).getSignature();
				matchsignatures(formerSignature, f.getSignature());

				currentSignature = f.getSignature();
				// print("assigned to succrent signature: " + f);

				interpretStatement(calledFunc);
			}
		}

		else if (stm instanceof If) {
			If f = (If) stm;

			if (evalExpression(f.getCondition()).getValue().equals("true")) {
				System.err.println("IF Condition is  true");
				interpretStatement(f.getIfClause());
			} else {
				System.err.println("IF Condition is  false");
				if (f.getElseClause() != null)
					interpretStatement(f.getElseClause());
			}
		}

		else if (stm instanceof For) {
			For f = (For) stm;
			print("Interpret For Loop ");
			print("ADD SCOPE");

			scopes.add(new Scope());

			Declaration d = (Declaration) f.getInit();
			Value value = evalExpression(d.getVar().getExpression());
			Variable var = new Variable(d.getVar().getName(), d.getVar().getType(), value, d.getIndex());

			addVar(var);

			// for (int i = 0; i < 5; i = i + 1;) {
			// print(i);
			// }

			while (evalExpression(f.getCondition()).getValue().equals("true")) {
				interpretStatement(f.getBody());
				interpretStatement(f.getIncrement());
			}

			print("POP SCOPE : " + scopes.peek() + " because for loop ends");
			scopes.pop();
		}

		else if (stm instanceof While) {
			While w = (While) stm;

			while (evalExpression(w.getCondition()).getValue().equals("true")) {
				interpretStatement(w.getBody());
			}
		}

		else if (stm instanceof Switch) {
			Switch s = (Switch) stm;
			// switch (key) {
			// case value:
			//
			// break;
			//
			// default:
			// break;
			// }

			String key = evalExpression(s.getKey()).getValue();

			boolean found = false;
			for (Case c : s.getCases())
				if (evalExpression(c.getExpression()).getValue().equals(key)) {
					interpretStatement(c.getBlock());
					found = true;
				}

			if (!found)
				if (s.getDefaultBlock() != null)
					interpretStatement(s.getDefaultBlock());

		}

		else if (stm instanceof Return) {
			Return r = (Return) stm;
			Value v = evalExpression(r.getReturnExp());

			// match types of return value and returtn type of function
			if (v.getType() != currentFunction.getType()) {
				printToConsole("DISMATCH TYPES OF function: " + currentFunction.getName() + " and " + v.getValue());
			}

			returnedValue = v;
		}
	}

	private boolean isBinaryOperator(Token token) {
		TokenType tokenType = token.getType();
		if (tokenType == TokenType.PLUS || tokenType == TokenType.MINUS || tokenType == TokenType.MULT || tokenType == TokenType.DIV)
			return true;
		return false;
	}

	private boolean isCorrectType(Token token) {
		TokenType temp = token.getType();
		if (temp == TokenType.INTType || temp == TokenType.DoubleType || temp == TokenType.Void) {
			return true;
		}
		return false;
	}

	private boolean isNumber(Token token) {
		TokenType tokt = token.getType();
		if (tokt == TokenType.INT || tokt == TokenType.DOUBLE)
			return true;
		return false;
	}

	private Variable lookFirstVarThatFaces(String var) {

		@SuppressWarnings("unchecked")
		Stack<Scope> temp = (Stack<Scope>) scopes.clone();

		System.out.println("FFF " + scopes);
		System.out.println("TTT " + temp);

		while (temp.size() > 0) {
			for (Variable variable : temp.pop().getVariables())
				if (variable.getName().equals(var)) {
					print("Found:" + variable);
					return variable;
				}
		}

		print("No variable with " + var + " were found");
		return null;
	}

	private Function lookUpFunction(String name) {

		for (Function function : functions) {
			if (function.getName().equals(name))
				return function;
		}
		print("No function with " + name + " were found");
		return null;
	}

	private boolean matchsignatures(ParameterList formerSignature, ParameterList newSignature) {
		print("\nMatch signatures");
		print("Former : " + formerSignature);
		print("New : " + newSignature);

		if (formerSignature.getParameters().size() != newSignature.getParameters().size()) {
			exit("Mismatch in number of arguments in function call", newSignature.getIndex());
		}

		for (int i = 0; i < formerSignature.getParameters().size(); i++) {
			if (((Variable) (formerSignature.getParameters().get(i).getExpression())).getType() != evalExpression(newSignature.getParameters().get(i)).getType())
				exit("Type mismatch", newSignature.getParameters().get(i).getIndex());
		}

		print("OK");
		return true;
	}

	private Assignment parseAssignment() {
		print("Parse assignment");

		Token t = peek();
		Ident id = new Ident(t.getData(), t.getPosition());

		index++;

		ignore(TokenType.ASSIGN);

		Expression expression = parseExpression();

		ignore(TokenType.SEMICOLON);

		return new Assignment(id, expression, t.getPosition());
	}

	private Block parseBlock() {
		print("Parse Block");
		Token token = peek();
		Block block = new Block();

		printCurrentScope();

		while (token.getType() != TokenType.RIGHTCURLYBRACKET && token.getType() != TokenType.BREAK) {
			block.getStatements().add(parseStatement());
			token = peek();
		}
		return block;
	}

	private Declaration parseDeclaration() {
		print("Parsing declaration");

		Variable var;
		Token tokenType = peek();
		Type varType = convertTokenToType(tokenType);

		index++;

		String ident = ignore(TokenType.IDENT).getData();

		if (lookFirstVarThatFaces(ident) != null)
			exit("Redeclaration of " + ident, index);

		ignore(TokenType.ASSIGN);

		Expression expression = null;

		if (peek().getType() != TokenType.SEMICOLON) {
			expression = parseExpression();
		}

		print("Expression is " + expression);

		var = new Variable(ident, varType, expression, tokenType.getPosition());

		Declaration dec = new Declaration(var, expression, var.getIndex());

		ignore(TokenType.SEMICOLON);
		return dec;
	}

	private Expression parseExpression() {
		print("Parse Expression");
		Token leftPart = peek();
		Token rightPart;
		// if (leftPart.getType() == TokenType.FUNCTIONCALL) {
		// rightPart = peekElementAt(2);
		// } else

		rightPart = peekElementAt(1);

		print("leftPart is \t" + leftPart);
		print("rightPart is \t" + rightPart);

		// Number
		if (((isNumber(leftPart) || leftPart.getType() == TokenType.FUNCTIONCALL || leftPart.getType() == TokenType.IDENT)
				&& (rightPart.getType() == TokenType.SEMICOLON))) {
			if (leftPart.getType() == TokenType.FUNCTIONCALL) {
				ignore(TokenType.FUNCTIONCALL);
				ignore(TokenType.RIGHTBRACKET);
				ParameterList signature = parseSignature();
				return new FunctionCall(leftPart.getData().substring(0, leftPart.getData().length() - 1), signature, leftPart.getPosition());
			} else if (leftPart.getType() == TokenType.IDENT) {
				ignore(TokenType.IDENT).getData();
				return new Ident(leftPart.getData(), index);
			} else {
				index++;
				print("Return number2");
				return new Number(leftPart.getData(), DoubleOrInt(leftPart.getData()), leftPart.getPosition());
			}

		} else if (leftPart.getType() == TokenType.BOOLVALUE) {
			ignore(TokenType.BOOLVALUE);
			return new Boolean(leftPart.getData());

			// EXP1
		} else if ((isNumber(leftPart) || leftPart.getType() == TokenType.FUNCTIONCALL || leftPart.getType() == TokenType.IDENT)
				&& (rightPart.getType() == TokenType.PLUS || rightPart.getType() == TokenType.MINUS)) {
			print("Parse EXP1");
			Expression leftExp = null;
			if (leftPart.getType() == TokenType.FUNCTIONCALL) {
				ignore(TokenType.FUNCTIONCALL);
				ignore(TokenType.RIGHTBRACKET);
				ParameterList ar = parseSignature();
				leftExp = new FunctionCall(leftPart.getData().substring(0, leftPart.getData().length() - 1), ar, leftPart.getPosition());

			} else if (leftPart.getType() == TokenType.IDENT) {
				ignore(TokenType.IDENT).getData();
				leftExp = new Ident(leftPart.getData(), index);
			} else {
				leftExp = new Number(peek().getData(), DoubleOrInt(peek().getData()), leftPart.getPosition());
				index++;
			}

			if (isBinaryOperator(peek()))
				ignore(TokenType.BINARYOPERATOR);

			return new Exp1(leftExp, rightPart.getData(), parseExpression());

			// EXP2
		} else if ((isNumber(leftPart) || leftPart.getType() == TokenType.FUNCTIONCALL || leftPart.getType() == TokenType.IDENT)
				&& (rightPart.getType() == TokenType.MULT || rightPart.getType() == TokenType.DIV)) {
			Expression leftExp = null;
			if (leftPart.getType() == TokenType.FUNCTIONCALL) {
				ignore(TokenType.FUNCTIONCALL);
				ignore(TokenType.RIGHTBRACKET);
				ParameterList ar = parseSignature();
				leftExp = new FunctionCall(leftPart.getData().substring(0, leftPart.getData().length() - 1), ar, leftPart.getPosition());
			} else if (leftPart.getType() == TokenType.IDENT) {
				ignore(TokenType.IDENT);
				leftExp = new Ident(leftPart.getData(), index);
			} else {
				leftExp = new Number(peek().getData(), DoubleOrInt(peek().getData()), leftPart.getPosition());
				index++;
			}

			if (isBinaryOperator(peek()))
				ignore(TokenType.BINARYOPERATOR);

			return new Exp2(leftExp, rightPart.getData(), parseExpression());

		} else if (leftPart.getType() == TokenType.IDENT && rightPart.getType() == TokenType.Increment) {
			index++;
			index++;
			return new Increment(leftPart.getData());

		} else if (rightPart.getType() == TokenType.RIGHTCURLYBRACKET || rightPart.getType() == TokenType.RETURN) {
			exit("Missed SEMICOLON", rightPart.getPosition());

		} else if (isNumber(leftPart) && (rightPart.getType() == TokenType.RIGHTBRACKET || rightPart.getType() == TokenType.COLON)) {
			index++;
			print("2222 + " + leftPart.getData());
			return new Number(leftPart.getData(), DoubleOrInt(leftPart.getData()), leftPart.getPosition());
		}

		else if (leftPart.getType() == TokenType.FUNCTIONCALL) {
			ignore(TokenType.FUNCTIONCALL);
			ParameterList signature = parseSignatureOfCalledFunction();
			String name = leftPart.getData().substring(0, leftPart.getData().length() - 1);
			ignore(TokenType.RIGHTBRACKET);
			return new FunctionCall(name, signature, leftPart.getPosition());
		}

		else if (isNumber(leftPart) && rightPart.getType() == TokenType.COMMA) {
			Number n = new Number(peek().getData(), DoubleOrInt(peek().getData()), peek().getPosition());

			index += 2;
			return n;
		}

		else if (leftPart.getType() == TokenType.IDENT && rightPart.getType() == TokenType.COMMA) {
			Ident n = new Ident(peek().getData(), peek().getPosition());
			index += 2;
			return n;
		}

		else if (leftPart.getType() == TokenType.IDENT && rightPart.getType() == TokenType.RIGHTBRACKET) {
			Token ident = ignore(TokenType.IDENT);
			return new Ident(ident.getData(), ident.getPosition());
		}

		else if ((isNumber(leftPart) || leftPart.getType() == TokenType.IDENT || leftPart.getType() == TokenType.FUNCTIONCALL) && isComparisonSign(rightPart.getType())) {
			index++;
			index++;
			Expression l = null;
			String sign;

			if (isNumber(leftPart)) {
				l = new Number(leftPart.getData(), convertTokenToType(leftPart), -1);
			} else if (leftPart.getType() == TokenType.IDENT) {
				l = new Ident(leftPart.getData(), leftPart.getPosition());
			} else if (leftPart.getType() == TokenType.FUNCTIONCALL) {
				l = new FunctionCall(nameOfFunctionCall(leftPart.getData()), parseSignature(), leftPart.getPosition());
			}

			sign = rightPart.getData();

			return new Comparison(l, sign, parseExpression(), l.getIndex());
		}

		else
			exit("Not yet implemented", -1);

		print("reached end and null is returned");
		return null;
	}

	private String nameOfFunctionCall(String name) {
		String s = name.substring(0, name.indexOf("("));
		return s;
	}

	private boolean isComparisonSign(TokenType tokenType) {
		print("COMPARING  " + tokenType);
		if (tokenType == TokenType.GREATER || tokenType == TokenType.LESS || tokenType == TokenType.GREATEREQUAL || tokenType == TokenType.LESSEQUAL
				|| tokenType == TokenType.EQUAL)
			return true;
		return false;
	}

	private Statement parseForStatment() {
		// for (int i = 4; i > 0; i = i + 1) { print(i); }
		print("Parsing For statement");

		ignore(TokenType.FOR);
		ignore(TokenType.LEFTBRACKET);

		Declaration varDeclaration = (Declaration) parseStatement();

		Condition cond = (Condition) parseExpression();

		ignore(TokenType.SEMICOLON);

		Assignment statement = (Assignment) parseStatement();

		print(statement);

		ignore(TokenType.RIGHTBRACKET);
		ignore(TokenType.LEFTCURLYBRACKET);

		Block block = parseBlock();

		ignore(TokenType.RIGHTCURLYBRACKET);

		For f = new For(varDeclaration, cond, statement, block);
		print(f);
		return f;
	}

	private FunctionCall parseFunctionCall() {
		print("Parse Function Call");
		Token t = peek();
		String name = peek().getData();
		name = name.substring(0, name.length() - 1);
		ignore(TokenType.FUNCTIONCALL);

		ParameterList newSignature = parseSignatureOfCalledFunction();

		ignore(TokenType.RIGHTBRACKET);
		ignore(TokenType.SEMICOLON);

		return new FunctionCall(name, newSignature, t.getPosition());
	}

	private Function parseFunctionDeclaration() {
		print("parse FUNC declaration");

		Token t = peek();
		Type type = convertTokenToType(peek());

		index++;
		String name = ignore(TokenType.FUNCTIONCALL).getData();
		name = name.substring(0, name.length() - 1);

		ParameterList signature = parseSignature();

		ignore(TokenType.LEFTCURLYBRACKET);

		Block body = parseBlock();

		boolean falseStatementExists = false;

		for (Statement statement : body.getStatements())
			if (statement instanceof Return)
				falseStatementExists = true;

		if (!falseStatementExists && type != Type.Void)
			exit("Return statement is missed in function:\t" + name, indexOfToken(t));

		ignore(TokenType.RIGHTCURLYBRACKET);

		boolean isMain = false;
		if (name.equals("main"))
			isMain = true;

		Function f = new Function(type, name, signature, body, isMain, indexOfToken(t));
		print(f);
		functions.add(f);
		System.out.println("\n\ndeclaration added with name " + f.getName() + "\n");
		return f;
	}

	private If parseIfStatment() {
		print("PArse IF Statement");
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
		print(ifs);
		return ifs;
	}

	private Return parseReturn() {
		int indexOfReturn = peek().getPosition();
		ignore(TokenType.RETURN);
		Expression e = parseExpression();
		ignore(TokenType.SEMICOLON);
		return new Return(e, indexOfReturn);
	}

	private ParameterList parseSignature() {
		print("Parse Signature" + peek());

		ParameterList signature = new ParameterList(index);

		while (peek().getType() != TokenType.RIGHTBRACKET) {
			if (peek().getType() == TokenType.COMMA)
				ignore(TokenType.COMMA);
			Token t = peek();
			Type type = convertTokenToType(peek());
			index++;
			String name = ignore(TokenType.IDENT).getData();
			signature.getParameters().add(new Parameter(new Variable(name, type, null, t.getPosition()), signature.getIndex()));
		}
		ignore(TokenType.RIGHTBRACKET);
		return new ParameterList(signature, signature.getIndex());
	}

	// private Variable parseVariable() {
	// if (isCorrectType(pop())) {
	// Type type = convertTokenToType(pop());
	// index++;
	// Token ident = ignore(TokenType.IDENT);
	// return new Variable(ident.getData(), type);
	// }
	//
	// exit("problem with variable");
	// return null;
	// }

	private ParameterList parseSignatureOfCalledFunction() {
		print("Parse signature of Called Function");

		ParameterList ar = new ParameterList(index);

		while (peek().getType() != TokenType.RIGHTBRACKET) {
			if (peek().getType() == TokenType.COMMA)
				ignore(TokenType.COMMA);

			Expression e = parseExpression();

			ar.getParameters().add(new Parameter(e, e.getIndex()));

			if (peek().getType() == TokenType.COMMA)
				ignore(TokenType.COMMA);
		}

		return ar;
	}

	private Statement parseStatement() {
		print("Parse Statement");
		Token current = peek();
		Token next = peekElementAt(1);

		print("current token is " + current);
		print("next token is " + next);

		if (current.getType() == TokenType.FUNCTIONCALL) {
			return parseFunctionCall();
		}

		else if (current.getType() == TokenType.WHILE)
			return parseWhileStatement();

		else if (current.getType() == TokenType.IF)
			return parseIfStatment();

		else if (current.getType() == TokenType.FOR)
			return parseForStatment();

		else if (current.getType() == TokenType.Switch)
			return parseSwitchStatment();

		else if (isCorrectType(current) && next.getType() == TokenType.FUNCTIONCALL)
			return parseFunctionDeclaration();

		else if (isCorrectType(current) && next.getType() == TokenType.IDENT)
			return parseDeclaration();

		else if (current.getType() == TokenType.IDENT && next.getType() == TokenType.ASSIGN)
			return parseAssignment();

		else if (current.getType() == TokenType.RETURN)
			return parseReturn();

		else if (current.getType() == TokenType.INT && next.getType() == TokenType.RIGHTCURLYBRACKET) {
			exit("Missed semicolon ", current.getPosition());
		} else if (current.getType() == TokenType.RIGHTCURLYBRACKET) {
			print("END");
		}

		exit("null return statement", -1);
		return null;
	}

	private Statement parseSwitchStatment() {
		// switch (4) {
		// case 3:
		// int a = 3;
		// break;
		// case 4:
		// int b = 4;
		// break;
		// }

		print("Parse Switch statement");

		ignore(TokenType.Switch);
		ignore(TokenType.LEFTBRACKET);

		Expression key = parseExpression();

		ignore(TokenType.RIGHTBRACKET);
		ignore(TokenType.LEFTCURLYBRACKET);

		Token token = peek();

		ArrayList<Case> cases = new ArrayList<>();

		while (token.getType() != TokenType.RIGHTCURLYBRACKET && token.getType() != TokenType.Default) {
			ignore(TokenType.Case);
			Expression exp = parseExpression();
			ignore(TokenType.COLON);

			Block block = parseBlock();
			cases.add(new Case(exp, block, exp.getIndex()));

			ignore(TokenType.BREAK);
			ignore(TokenType.SEMICOLON);
			token = peek();
		}

		// switch (key) {
		// case value:
		//
		// break;
		//
		// default:
		// print();
		// break;
		// }
		Block defBlock = null;
		if (token.getType() == TokenType.Default) {
			ignore(TokenType.Default);
			ignore(TokenType.COLON);

			defBlock = parseBlock();

			ignore(TokenType.BREAK);
			ignore(TokenType.SEMICOLON);
		}

		ignore(TokenType.RIGHTCURLYBRACKET);
		Switch s = new Switch(key, cases, defBlock);
		print(s);
		return s;
	}

	// for (int i = 0 ; i < 3; i = i + 1) {
	// print(i);
	// }

	private While parseWhileStatement() {
		print("Parse While Statement");

		int indexOfWhile = peek().getPosition();

		ignore(TokenType.WHILE);
		ignore(TokenType.LEFTBRACKET);

		Expression condition = parseExpression();

		ignore(TokenType.RIGHTBRACKET);

		ignore(TokenType.LEFTCURLYBRACKET);

		Block body = parseBlock();

		ignore(TokenType.RIGHTCURLYBRACKET);

		While w = new While(condition, body, indexOfWhile);
		print(w);
		return w;
	}

	private Token peek() {
		return peekElementAt(0);
	}

	private Token peekElementAt(int offset) {
		if (index + offset < tokens.size())
			return tokens.get(index + offset);
		return null;
	}

	private void print(Object data) {
		System.out.println(data);
	}

	private void printCurrentScope() {

		int size = scopes.peek().getVariables().size();
		print("Scope size:\t" + size);

		if (size > 0) {
			print("Print Scope:");
			for (Variable var : scopes.peek().getVariables())
				print(var);
		}
	}

	private void printToConsole(Object obj) {
		textToConsole.add(obj.toString());
	}

	public void printAST() {
		print("AST is :");
		print(program);
	}

	public void printFuncPrototypes() {
		print("\nFunc prototypes: ");
		for (Function funcPrototype : functions)
			print(funcPrototype);
	}

	// private Token tokenAt(int index) {
	// for (Token token : tokens) {
	// if (token.getPosition() == index)
	// return token;
	// }
	// return null;
	// }
	//
	// private void showTokenAt(int index) {
	// print(tokens.get(index));
	// }

	public String getTextToConsole() {

		String s = "";
		for (String string : textToConsole) {
			s += string + " \n";
		}
		return s;
	}

	public void printTokens() {
		for (Token token : tokens) {
			print(token);
		}
	}

	public void start() {
		createParseTree();
		// printAST();

		interpret();
	}
}
