
public class CharType extends Type {

	public static CharType instance = new CharType();

	private CharType() {
	}

	@Override
	public String toString() {
		return "char";
	}
}