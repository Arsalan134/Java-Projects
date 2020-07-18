package Expression;

import Statement.Expression;
import Type.Type;

public class Number extends Expression {

	private String number;
	private Type type;

	public Number(String number, Type type, int index) {
		this.number = number;
		this.type = type;
		assignIndex(index);
	}

	public Type getType() {
		return type;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	public String getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return number + " of type: " + type;
	}

}
