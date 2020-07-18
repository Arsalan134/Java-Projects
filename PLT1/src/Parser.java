
import java.util.ArrayList;
import java.util.List;

import tkn.Token;
import tkn.Type;
import static tkn.Token.Type.*;


public class Parser {
    private ArrayList<Token> input;
    private int inputIndex;
    private Token EPSILON;

    public Parser(ArrayList<Token> input) {
        this.input = input;
        this.inputIndex = 0;
        if (input.isEmpty()) {
            this.EPSILON = new Token(EOF, "<EOF>", 0, 0);
        } else {
            Token last = input.get(input.size() - 1);
            this.EPSILON = new Token(EOF, "<EOF>", last.line, last.endCol);
        }
    }
    
    public Statement parseStatement() {
        Token first = peek();
        Token second = peekAtOffset(1);
        if (first.type == OpenBrace) {
            return parseBlock();
        } else if (first.type == While) {
            return parseWhileStatement();
        } else if (first.type == If) {
            return parseIfStatment();
            
        } else if ((first.type == CharType || first.type == IntType||first.type == BooleanType||first.type == FloatConstant) && second.type == ID) {
            return parseDeclaration();
        } else if (first.type == ID && second.type == Equal) {
            return parseAssignment();
        } else {
            Expression expr = parseExpr();
            ignore(Semicolon);
            return expr;
        }
    }
    
    public Block parseBlock() {
        ignore(OpenBrace);
        ArrayList<Statement> statements = new ArrayList<Statement>();
        while (true) {
            Token t = peek();
            if (t.type == CloseBrace) {
                break;
            } else {
                statements.add(parseStatement());
            }
        }
        ignore(CloseBrace);
        return new Block(statements);
    }
    
    private WhileLoop parseWhileStatement() {
        ignore(While);
        ignore(OpeningCurlyBrace);
        Expression head = parseExpr();
        Statement body = parseStatement();
        ignore(ClosingCurlyBrace);
        return new WhileLoop(head, body);
    }
    
    private IfStatement parseIfStatment() {
        ignore(If);
        ignore(OpeningCurlyBrace);
        Expression expression = parseExpr();
        Statement statement = parseStatement();
        if (peek().type == Else) {
            ignore(Else);
            Statement elseClause = parseStatement();
            ignore(ClosingCurlyBrace);

            return new IfStatement(expression, statement, elseClause);
        } else {
            ignore(ClosingCurlyBrace);

            return new IfStatement(expression, statement);
        }
    }
    
    private Statement parseDeclaration() {
        String varName = ignore(ID).name;
        tkn.Token.Type type = parseType();
        ignore(Equal);
        Expression expr = parseExpr();
        Statement decl = new Declaration(varName, type, expr);
        ignore(Semicolon);
        return decl;
    }

    private Statement parseAssignment() {
        String varName = ignore(ID).name;
        ignore(Equal);
        Expression expr = parseExpr();
        Statement assignment = new Assign(varName, expr);
        ignore(Semicolon);
        return assignment;
    }
    
    public Expression parseExpr() {
        Expression left = parseSubfactor();
        Token second = peek();
        while (!looksLikeExprEnd(second)) {
            left = parseTerm(left);
            second = peek();
        }
        return left;
    }
    
    private boolean looksLikeExprEnd(Token t) {
        switch (t.type) {
            case ClosingCurlyBrace:
            case Semicolon:
            case Comma:
            case Else:
            case EOF:
                return true;
            default:
                return false;
        }
    }
    
    private Expression parseTerm(Expression left) {
        Token op = peek();
        Expression right;
        switch (op.type) {
            case And:
            case Or:
            case Plus:
            	 ignore();
                 right = parseExpr();
                 break;
            case Minus:
                ignore();
                right = parseExpr();
                break;
            default:
                return parseFactor(left);
        }
        return new BinaryOp(left, op.name, right);
    }
    
    private Expression parseFactor(Expression left) {
        Token op = peek();
        Expression right;
        switch (op.type) {
            case EqualEqual:
            case NotEqual:
            case Less:
            case Greater:
            case IessOrEqual:
            case GreaterOrEqual:
            case Multiply:
            case Divide:
                ignore();
                right = parseSubfactor();
                break;
            default:
                return parseSubfactor();
        }
        return new BinaryOp(left, op.name, right);
    }
    
    private Expression parseSubfactor() {
        Token t = ignore();
        
        System.out.println("TYPE "+ t.type);
        if (t.type == OpeningCurlyBrace) {
            ignore(OpeningCurlyBrace);
            Expression e = parseExpr();
            ignore(ClosingCurlyBrace);
            return e;
        } else {
            switch (t.type) {
                case Minus: return new UnaryOp("-", parseSubfactor());  
                case Plus: return new UnaryOp("+", parseSubfactor());   
                case Multiply: return new UnaryOp("*", parseSubfactor());
                case Divide: return new UnaryOp("/", parseSubfactor());

                case Not: return new UnaryOp("!", parseSubfactor());
                case IntConstant: return new IntegerConstant(Integer.parseInt(t.name));
                case BooleanConstant: return new BooleanConstant(Boolean.parseBoolean(t.name));
                case FloatConstant: return new FloatConstant(Float.parseFloat(t.name));
                case CharConstant: return new CharConstant(t.name.charAt(0));

                case ID:
                    if (peek().type == OpeningCurlyBrace) {
                        String functionName = t.name;
                        ignore(OpeningCurlyBrace);
                        List<Expression> args = parseArguments();
                        ignore(ClosingCurlyBrace);
                        return new CallFunction(functionName, args);
                    } else {
                        return new Var(t.name);
                    }
                default: return fail("integer,char,float boolean or variable expected");
            }
        }
    }
    
    private List<Expression> parseArguments() {
        ArrayList<Expression> result = new ArrayList<Expression>();
        while (peek().type != ClosingCurlyBrace) {
            result.add(parseExpr());
            if (peek().type == Comma) {
                ignore(Comma);
            }
        }
        return result;
    }
    
    private tkn.Token.Type parseType() {
        Token t = ignore(ID);
        if (t.type==IntType) {
            return IntType;
        } else if (t.type==BooleanType) {
            return BooleanType;
        }
        else if (t.type==CharType) {
            return CharType;
        }
        else if (t.type==FloatType) {
            return FloatType;
        }else {
            return fail(t.name + " is not a known type");
        }
    }

    private Token peek() {
        return peekAtOffset(0);
    }
    
    private Token peekAtOffset(int offset) {
        if (inputIndex + offset < input.size()) {
            return input.get(inputIndex + offset);
        } else {
            return EPSILON;
        }
    }
    
    private Token ignore(Token.Type expected) {
        Token actual = peek();
        if (actual.type == expected) {
            inputIndex++;
            return actual;
        } else {
            return fail(expected + " expected");
        }
    }
    
    private Token ignore() {
        Token tok = peek();
        inputIndex++;
        return tok;
    }
    
    private <T> T fail(String error) {
        Token t = peek();
        throw new IllegalArgumentException("Parse error near line " + t.line + " col " + t.column + ": " + error);
    }
}