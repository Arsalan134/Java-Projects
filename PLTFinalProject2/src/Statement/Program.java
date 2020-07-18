package Statement;

import java.util.ArrayList;

import defaul.Statement;

public class Program {

	private ArrayList<Statement> statements;

	public Program() {
		this.statements = new ArrayList<>();
	}

	public ArrayList<Statement> getStatements() {
		return statements;
	}

	@Override
	public String toString() {
		return this.statements.toString();
	}

}
