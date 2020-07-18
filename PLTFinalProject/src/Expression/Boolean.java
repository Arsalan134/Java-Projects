package Expression;

import Statement.Expression;

public class Boolean extends Expression {

	private String bool;

	public Boolean(String data) {
		this.bool = data;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return bool;
	}

}
