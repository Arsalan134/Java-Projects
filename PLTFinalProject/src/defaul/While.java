package defaul;

import Statement.Block;
import Statement.Expression;

public class While extends Statement {

	public final Expression condition;
	public final Block body;

	public While(Expression condition, Block body, int index) {

		this.condition = condition;
		this.body = body;
		assignIndex(index);
	}

	public Expression getCondition() {
		return condition;
	}

	public Block getBody() {
		return body;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "while(" + condition + ") \n {\n" + body + "\n}";
	}

}
