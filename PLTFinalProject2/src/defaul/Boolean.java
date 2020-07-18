package defaul;

public class Boolean extends Condition {
	String value;

	public Boolean(String string, int index) {
		this.value = string;
		assignIndex(index);
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	protected void assignIndex(int index) {
	}
}
