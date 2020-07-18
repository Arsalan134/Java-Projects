package Parser;

import java.util.ArrayList;

import Expression.Variable;

public class Scope {

	private ArrayList<Variable> variables;

	public Scope() {
		variables = new ArrayList<>();
	}

	public Scope(ArrayList<Variable> variables) {
		super();
		this.variables = variables;
	}

	public ArrayList<Variable> getVariables() {
		return variables;
	}

	@Override
	public String toString() {
		return variables.toString();
	}

}
