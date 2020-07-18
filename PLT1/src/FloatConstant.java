

public class FloatConstant extends Node implements Expression {
    public final float val;

    public FloatConstant(float f) {
        this.val = f;
    }
   
    
    @Override
    public boolean equals(Object o) {
    	
        if (o instanceof FloatConstant) {
            return this.val == ((FloatConstant)o).val;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return ""+val;
    }
}
