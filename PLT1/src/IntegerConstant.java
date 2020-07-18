

public class IntegerConstant extends Node implements Expression {
    public final int value;

    public IntegerConstant(int value) {
        this.value = value;
    }
   
    
    @Override
    public boolean equals(Object obj) {
    	
    	System.out.print("checker");
        if (obj instanceof IntegerConstant) {
            return this.value == ((IntegerConstant)obj).value;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
