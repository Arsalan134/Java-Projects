package Statement;

import defaul.Statement;

public class Return extends Statement {

	Expression returnExp;

	public Return(Expression e, int index) {
		this.returnExp = e;
		assignIndex(index);
	}

	public Expression getReturnExp() {
		return returnExp;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "return " + returnExp;
	}

}
