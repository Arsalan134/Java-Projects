package Expression;

import Statement.Expression;
import Type.Type;

public class Value extends Expression {

	private Type type;
	private String value;

	public Value(Type type, String value) {
		super();
		this.type = type;
		this.value = value;
		assignIndex(0);
	}

	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return type + " " + value;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

}
