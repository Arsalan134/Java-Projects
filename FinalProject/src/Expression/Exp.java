package Expression;

import Statement.Expression;

public class Exp extends Expression {

	private Expression left;
	private String sign;
	private Expression right;

	public Exp(Expression left, String string, Expression right) {
		super();
		this.left = left;
		this.sign = string;
		this.right = right;
	}

	public Expression getLeft() {
		return left;
	}

	public String getSign() {
		return sign;
	}

	public Expression getRight() {
		return right;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return left + " " + sign + " [ " + right + " ]";
	}

}
