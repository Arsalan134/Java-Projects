package Expression;

import Statement.Expression;

public class Number extends Expression {

	private String number;

	public Number(String number) {
		this.number = number;
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
		return number;
	}

}
