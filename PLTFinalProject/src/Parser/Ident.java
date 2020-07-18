package Parser;

import Statement.Expression;

public class Ident extends Expression {

	private String name;

	public Ident(String name, int index) {
		this.name = name;
		assignIndex(index);
	}

	public String getName() {
		return name;
	}

	@Override
	protected void assignIndex(int index) {

		this.index = index;
	}

	@Override
	public String toString() {
		return name;
	}

}
