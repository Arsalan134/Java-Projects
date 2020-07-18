package defaul;

public class Token {

	private TokenType type;
	private String data;
	private int position;

	public Token(TokenType type, String data, int position) {
		this.type = type;
		this.data = data;
		this.position = position;
	}

	public String getData() {
		return data;
	}

	public int getPosition() {
		return position;
	}

	public TokenType getType() {
		return type;
	}

	public boolean equals(Token newToken) {
		if (type == newToken.getType() && data == newToken.getData())
			return true;
		return false;
	}

	@Override
	public String toString() {
		// return String.format("[ %s \t|\t%s\t|\t%d ]", type, data, position);
		return String.format("[ %s   \t|\t%s\t %d]", type, data, position);
	}
}
