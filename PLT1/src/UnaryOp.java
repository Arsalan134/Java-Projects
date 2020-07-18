

public class UnaryOp extends Node implements Expression {
    public final String opName;
    public final Expression operand;

    public UnaryOp(String opName, Expression operand) {
        this.opName = opName;
        this.operand = operand;
    }
   
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnaryOp) {
            UnaryOp that = (UnaryOp)obj;
            return
                    this.getClass().equals(that.getClass()) &&
                    this.operand.equals(that.operand);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "(" + opName + operand + ")";
    }
}
