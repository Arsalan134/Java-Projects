

public class BooleanConstant extends Node implements Expression {
    public final boolean value;

    public BooleanConstant(boolean value) {
        this.value = value;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object instanceof BooleanConstant) {
            return this.value == ((BooleanConstant)object).value;
        } else {
            return false;
        }
    }

   

    @Override
    public String toString() {
        return ""+value;
    }
}
