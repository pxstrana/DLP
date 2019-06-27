package ast.definitions;

import ast.AbstractASTNode;
import ast.types.Type;

public abstract class AbstractDefinition extends AbstractASTNode implements Definition{

	String name;
	Type type;
	int scope;
	
	private int offset;
	
	public AbstractDefinition(int line, int column, String name, Type type) {
		super(line, column);
		this.name = name;
		this.type = type;
	}

	
	
	public String getName() {
		return name;
	}



	public Type getType() {
		return type;
	}

	public int getScope() {
		return scope;
	}
	
	public void setScope(int i) {
		this.scope=i;
	}


	@Override
	public String toString() {
		return "AbstractDefinition [name=" + name + ", type=" + type + "]";
	}



	public int getOffset() {
		return offset;
	}



	public void setOffset(int offset) {
		this.offset = offset;
	}

	
	
	
}
