package Parser;

import Type.Type;

public class Value {

	private Type type;
	private String value;

	public Value(Type type, String expression) {
		super();
		this.type = type;
		this.value = expression;
	}

	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return type + " " + value.toString();
	}

}
