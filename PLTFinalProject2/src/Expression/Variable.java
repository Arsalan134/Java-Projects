package Expression;

import Statement.Expression;
import Type.Type;

public class Variable extends Expression {

	private Type type;
	private String name;
	private Expression expression;

	public Variable(String name, Type type, Expression expression, int index) {
		super();
		this.name = name;
		this.type = type;
		this.expression = expression;
		assignIndex(index);
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		System.out.println("Value for " + name + " changed from: " + this.expression + " to " + expression);
		this.expression = expression;
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
		return type + " " + name + " = " + expression;
	}

}
