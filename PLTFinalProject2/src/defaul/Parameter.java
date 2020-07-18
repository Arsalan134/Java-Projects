package defaul;

import Statement.Expression;

public class Parameter extends Expression {

	private Expression expression;

	public Parameter(Expression expression, int index) {
		this.expression = expression;
		assignIndex(index);
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return expression.toString();
	}

}
