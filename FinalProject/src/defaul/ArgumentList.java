package defaul;

import java.util.ArrayList;

public class ArgumentList {

	private ArrayList<Argument> arguments;

	public ArgumentList(ArgumentList signature) {
		this.arguments = signature.getArguments();
	}

	public ArgumentList(ArrayList<Argument> arguments) {
		this.arguments = arguments;
	}

	public ArgumentList() {
		this.arguments = new ArrayList<>();
	}

	public ArrayList<Argument> getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		return arguments.toString();
	}

}
