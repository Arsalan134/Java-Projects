package Parser;

import Expression.Variable;
import Statement.Expression;
import defaul.Statement;

public class Declaration extends Statement {

	private Variable var;
	private Expression expression;

	public Declaration(Variable var, Expression expression, int index) {
		this.var = var;
		this.expression = expression;
		assignIndex(index);
	}

	public Variable getVar() {
		return var;
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
		return var.toString();
	}

}
