
public class Declaration extends Node implements Statement {
	public final String varName;
	public final Type type;
	public final Expression expr;

	public Declaration(String varName, Type type, Expression expr) {
		this.varName = varName;
		this.type = type;
		this.expr = expr;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Declaration) {
			Declaration that = (Declaration) obj;
			return this.varName.equals(that.varName) && this.type.equals(that.type) && this.expr.equals(that.expr);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return varName + " : " + type + " := " + expr;
	}
}
