package defaul;

import java.util.ArrayList;

import Statement.Expression;

public class ParameterList extends Expression {

	private ArrayList<Parameter> parameters;

	public ParameterList(ParameterList signature, int index) {
		this.parameters = signature.getParameters();
		assignIndex(index);
	}

	public ParameterList(ArrayList<Parameter> parameters) {
		this.parameters = parameters;
	}

	public ParameterList(int index) {
		this.parameters = new ArrayList<>();
		assignIndex(index);
	}

	public ArrayList<Parameter> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return parameters.toString();
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

}
