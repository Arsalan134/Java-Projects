package Statement;

import defaul.Condition;
import defaul.Statement;

public class For extends Statement {

	private Statement init;
	private Condition condition;
	private Statement increment;
	private Block body;

	public Statement getInit() {
		return init;
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getIncrement() {
		return increment;
	}

	public Block getBody() {
		return body;
	}

	public For(Statement init, Condition condition, Statement s, Block body) {
		this.init = init;
		this.condition = condition;
		this.increment = s;
		this.body = body;
	}

	@Override
	public String toString() {
		return "for (" + init + ";" + condition + ";" + increment + ";) {\n" + body + "\n }";
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

}
