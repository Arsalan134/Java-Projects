package Expression;

import Statement.Expression;

public class Increment extends Expression {

	private String var;

	public Increment(String data) {
		this.var = data;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return var + "++";
	}

}
