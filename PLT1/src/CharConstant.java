
public class CharConstant extends Node implements Expression {

	 public final char value;

	    public CharConstant(char value) {
	        this.value = value;
	    }
	    

	    @Override
	    public boolean equals(Object obj) {
	        if (obj instanceof BooleanConstant) {
	            return this.value == ((CharConstant)obj).value;
	        } else {
	            return false;
	        }
	    }

	   

	    @Override
	    public String toString() {
	        return ""+value;
	    }
	}