

public class Assign extends Node implements Statement {
    public final String variableName;
    public final Expression expr;

    public Assign(String varName, Expression expr) {
        this.variableName = varName;
        this.expr = expr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Assign) {
            Assign that = (Assign)obj;
            return
                    this.variableName.equals(that.variableName) &&
                    this.expr.equals(that.expr);
        } else {
            return false;
        }
    }
    @Override
    public String toString() {
        return variableName + " := " + expr;
    }
}