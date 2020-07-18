package Statement;

import Parser.Ident;
import defaul.Statement;

public class Assignment extends Statement {

	private Ident var;
	private Expression expression;

	public Assignment(Ident var, Expression expression, int index) {
		super();
		this.var = var;
		this.expression = expression;
		assignIndex(index);
	}

	public Ident getVar() {
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
		return var + " = " + expression;
	}

}
