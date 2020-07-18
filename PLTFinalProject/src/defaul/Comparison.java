package defaul;

import Statement.Expression;

public class Comparison extends Condition {

	Expression left;
	Expression right;
	String sign;

	public Comparison(Expression left, String sign, Expression right, int index) {
		super();
		this.left = left;
		this.right = right;
		this.sign = sign;
		assignIndex(index);
	}

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}

	public String getSign() {
		return sign;
	}

	@Override
	public String toString() {
		return left + sign + right;
	}

	@Override
	protected void assignIndex(int index) {
	}

}
