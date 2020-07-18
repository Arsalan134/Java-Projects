package Expression;

import Parser.Value;
import Statement.Expression;

public class Variable extends Expression {

	private String name;
	private Value value;

	public Variable(String name, Value value) {
		super();
		this.name = name;
		this.value = value;
	}

	public void setValue(Value value) {
		System.out.println("Value for " + name + " changed from: " + this.value + " to " + value);
		this.value = value;
	}

	public boolean equals(String var) {
		if (var.equals(this.name))
			return true;
		return false;
	}

	public boolean equals(Variable var) {
		if (var.getName().equals(this.name))
			return true;
		return false;
	}

	public String getName() {
		return name;
	}

	public Value getValue() {
		return value;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return value.getType() + " " + name + " = " + value.getValue();
	}

}
