package Statement;

import java.util.ArrayList;

import Parser.Case;
import defaul.Statement;

public class Switch extends Statement {

	private Expression key;
	private ArrayList<Case> cases;
	private Block defaultBlock;

	public Switch(Expression key, ArrayList<Case> cases, Block defaultBlock) {
		super();
		this.key = key;
		this.cases = cases;
		this.defaultBlock = defaultBlock;
	}

	public Block getDefaultBlock() {
		return defaultBlock;
	}

	public Expression getKey() {
		return key;
	}

	public ArrayList<Case> getCases() {
		return cases;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "switch(" + key + ") {\n" + cases + "}";
	}

}
