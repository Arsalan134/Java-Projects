package Statement;

import Type.Type;
import defaul.ParameterList;
import defaul.Statement;

public class Function extends Statement {

	private Type type;
	private String name;
	private ParameterList signature;
	private boolean isMain;

	private Block body;

	public Function(Type type, String name, ParameterList signature, Block body, boolean isMain, int index) {
		super();
		this.type = type;
		this.name = name;
		this.signature = signature;
		this.body = body;
		this.isMain = isMain;
		assignIndex(index);
	}

	public boolean isMain() {
		return isMain;
	}

	@Override
	protected void assignIndex(int index) {
		this.index = index;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public ParameterList getSignature() {
		return signature;
	}

	public Block getBody() {
		return body;
	}

	@Override
	public String toString() {
		return type + " " + name + "( " + signature + " ) " + "{\n" + body + "\n}";
	}

}
