package Statement;

import java.util.ArrayList;

import defaul.Statement;

public class Block extends Statement {

	private ArrayList<Statement> statements = new ArrayList<>();

	public Block(ArrayList<Statement> statements) {
		this.statements = statements;
	}

	public Block() {
		this.statements = new ArrayList<>();
	}

	public ArrayList<Statement> getStatements() {
		return statements;
	}

	@Override
	public String toString() {
		return statements.toString();
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

}
