public class WhileLoop extends Node implements Statement {
    public final Expression head;
    public final Statement body;

    public WhileLoop(Expression head, Statement body) {
        this.head = head;
        this.body = body;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WhileLoop) {
            WhileLoop that = (WhileLoop)obj;
            return
                    this.head.equals(that.head) &&
                    this.body.equals(that.body);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "while (" + head + ") { " + body + " }";
    }
}