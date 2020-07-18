

public class BinaryOp extends Node implements Expression {
    public final Expression expression_lft;
    public final String operation;
    public final Expression expression_rght;

    public BinaryOp(Expression left, String opName, Expression right) {
        this.expression_lft = left;
        this.operation = opName;
        this.expression_rght = right;
    }
    
   

   @Override
    public String toString() {
        return "(" + expression_lft + " " + operation + " " + expression_rght + ")";
    }
   

   @Override
   public boolean equals(Object o) {
       if (o instanceof BinaryOp) {
           BinaryOp that = (BinaryOp)o;
           return
        		   this.expression_lft.equals(that.expression_lft) &&
                   this.expression_rght.equals(that.expression_rght)&& this.getClass().equals(that.getClass());
           
                   
       } else {
           return false;
       }
   }
}