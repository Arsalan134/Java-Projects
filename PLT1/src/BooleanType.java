
public class BooleanType extends Type {
	public static BooleanType instance = new BooleanType();

	private BooleanType() {
	}

	@Override
	public String toString() {
		return "bool";
	}
}