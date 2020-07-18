package Statement;

import defaul.ParameterList;

public class FunctionCall extends Expression {

	private String nameOfFunction;
	private ParameterList signature;

	public FunctionCall(String nameOfFunction, ParameterList newSignature, int index) {
		this.nameOfFunction = nameOfFunction;
		this.signature = newSignature;
		assignIndex(index);
	}

	public String getNameOfFunction() {
		return nameOfFunction;
	}

	public void setSignature(ParameterList signature) {
		this.signature = signature;
	}

	public ParameterList getSignature() {
		return signature;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return nameOfFunction + "( " + signature + ")";
	}

}
