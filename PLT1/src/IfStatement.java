public class IfStatement extends Node implements Statement {
    public final Expression condition;
    public final Statement thenClause;
    public final Statement elseClause;

    public IfStatement(Expression condition, Statement thenClause, Statement elseClause) {
        this.condition = condition;
        this.thenClause = thenClause;
        this.elseClause = elseClause;
    }
    
    public IfStatement(Expression condition, Statement thenClause) {
        this.condition = condition;
        this.thenClause = thenClause;
        this.elseClause = new EmptyStatement();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IfStatement) {
            IfStatement that = (IfStatement)obj;
            return
                    this.condition.equals(that.condition) &&
                    this.thenClause.equals(that.thenClause) &&
                    this.elseClause.equals(that.elseClause);
        } else {
            return false;
        }
    }

  
    @Override
    public String toString() {
        if (elseClause instanceof EmptyStatement) {
            return "if (" + condition + ") " + thenClause;
        } else {
            return "if (" + condition + ") " + thenClause + " else " + elseClause;
        }
    }
}