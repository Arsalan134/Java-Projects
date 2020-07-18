
public class IntegerType extends Type {
	public static IntegerType instance = new IntegerType();

	private IntegerType() {
	}

	@Override
	public String toString() {
		return "int";
	}
}