package Parser;

import Statement.Block;
import Statement.Expression;
import defaul.Statement;

public class Case extends Statement {

	private Expression expression;
	private Block block;

	public Case(Expression expression, Block block, int index) {
		super();
		this.expression = expression;
		this.block = block;
		assignIndex(index);
	}

	@Override
	protected void assignIndex(int index) {
	}

	public Block getBlock() {
		return block;
	}

	public Expression getExpression() {
		return expression;
	}

}
