package Parser;

import Statement.Block;
import Type.Type;
import defaul.ArgumentList;
import defaul.Statement;

public class Function extends Statement {

	private Type type;
	private String name;
	private ArgumentList signature;
	private Block body;

	public Function(Type type, String name, ArgumentList signature, Block body, int index) {
		super();
		this.type = type;
		this.name = name;
		this.signature = signature;
		this.body = body;
		assignIndex(index);
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

	public ArgumentList getSignature() {
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
