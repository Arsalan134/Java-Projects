package Type;

public enum TokenType {

	// Token types cannot have underscores
	Void("void"), DOUBLE("-?([0-9]+\\.[0-9]*)|([0-9]*\\.[0-9]+)"), WHILE("while"), FOR("for"), FUNCTIONCALL("[a-zA-Z_][a-zA-Z0-9_]*\\("), INT("-?[0-9][0-9]*"), NUMBER(
			"[1-9][0-9]*|0|[0-9]*\\.[0-9][0-9]*"), EQUAL("\\=="), ASSIGN("\\="), GREATEREQUAL(">\\="), LESSEQUAL("<\\="), GREATER(">"), LESS("<"), NOT(
					"\\!"), AND("\\$"), OR("\\|"), COMMENT("//"), LEFTBRACKET("\\("), Case("case"), RIGHTBRACKET("\\)"), LEFTCURLYBRACKET(
							"\\{"), RIGHTCURLYBRACKET("\\}"), LEFTARRBRACKET("\\["), RIGHTARRBRACKET("\\]"), IF("if"), Switch("switch"), ELSE("else"), BREAK(
									"break"), Default("default"), CONTINUE("continue"), RETURN("return"), BOOLVALUE("true|false"), INTType("int"), DoubleType(
											"double"), IDENT("[a-zA-Z_][a-zA-Z0-9_]*"), STRING("String"), CHARVALUE("'.'"), SEMICOLON(";"), COLON(
													":"), COMMA(","), MINUS("-"), MULT("\\*"), Increment("\\+\\+"), PLUS("\\+"), DIV("/"), BINARYOPERATOR("\\+\\-\\*\\/");
	// ,WHITESPACE("[ ]+")
	public final String pattern;

	private TokenType(String pattern) {
		this.pattern = pattern;
	}

}