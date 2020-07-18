package Parser;

import Statement.Expression;

public class FunctionCall extends Expression {

	private String nameOfFunction;

	public FunctionCall(String nameOfFunction) {
		this.nameOfFunction = nameOfFunction;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return nameOfFunction + "()";
	}

}
