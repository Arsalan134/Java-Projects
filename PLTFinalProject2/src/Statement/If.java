package Statement;

import defaul.Statement;

public class If extends Statement {

	private Expression condition;
	private Block ifClause;
	private Block elseClause;

	public If(Expression condition, Block ifClause) {
		super();
		this.condition = condition;
		this.ifClause = ifClause;
		this.elseClause = null;
	}

	public If(Expression condition2, Block ifClause, Block elseClause) {
		super();
		this.condition = condition2;
		this.ifClause = ifClause;
		this.elseClause = elseClause;
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getIfClause() {
		return ifClause;
	}

	public Statement getElseClause() {
		return elseClause;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		if (elseClause != null) {
			return "if (" + condition + ") {" + ifClause + "} else { " + elseClause + "}";
		}
		return "if (" + condition + ") {" + ifClause + "}";
	}

}
