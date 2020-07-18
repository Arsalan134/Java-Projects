package defaul;

import Statement.Expression;
import Type.Type;

public class Argument extends Expression {

	private Type type;
	private String name;

	public Argument(Type type, String name) {
		this.type = type;
		this.name = name;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return type + " " + name;
	}

}
