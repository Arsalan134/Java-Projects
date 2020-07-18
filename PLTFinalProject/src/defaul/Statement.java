package defaul;

public abstract class Statement {
	protected int index;

	public int getIndex() {
		return index;
	}

	protected abstract void assignIndex(int index);

}
