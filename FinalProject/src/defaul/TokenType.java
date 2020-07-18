package defaul;

public enum TokenType {

	DOUBLE("-?([0-9]+\\.[0-9]*)|([0-9]*\\.[0-9]+)"), FUNCTIONCALL("[a-zA-Z_][a-zA-Z0-9_]*\\("), INT("-?[0-9][0-9]*"), NUMBER("[1-9][0-9]*|0|[0-9]*\\.[0-9][0-9]*"), EQUAL(
			"\\=="), ASSIGN("\\="), GREATEREQUAL(">\\="), LESSEQUAL("<\\="), GREATER(">"), LESS("<"), NOT("\\!"), AND("\\$"), OR("\\|"), COMMENT("//"), LEFTBRACKET(
					"\\("), Case("case"), RIGHTBRACKET("\\)"), LEFTCURLYBRACKET("\\{"), RIGHTCURLYBRACKET("\\}"), LEFTARRBRACKET("\\["), RIGHTARRBRACKET("\\]"), IF(
							"if"), ELSE("else"), BREAK("break"), Default("default"), CONTINUE("continue"), BOOLVALUE("true|false"), INTType("int"), DoubleType(
									"double"), IDENT("[a-zA-Z_][a-zA-Z0-9_]*"), STRING("String"), CHARVALUE("'.'"), SEMICOLON(
											";"), COLON(":"), COMMA(","), MINUS("-"), MULT("\\*"), PLUS("\\+"), DIV("/"), BINARYOPERATOR("\\+\\-\\*\\/");
	public final String pattern;

	private TokenType(String pattern) {
		this.pattern = pattern;
	}

}