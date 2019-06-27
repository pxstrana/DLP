package ast.definitions;

import ast.statements.Statement;
import ast.types.Type;
import visitor.Visitor;

public class VariableDefinition extends AbstractDefinition implements Statement{

	boolean isParam=false;

	public VariableDefinition(int line, int column, String name, Type type) {
		super(line, column, name, type);
	}
	public VariableDefinition(int line, int column, String name, Type type,boolean isParam) {
		super(line, column, name, type);
		this.isParam=isParam;
	}

	@Override
	public String toString() {
		return "VariableDefinition [name=" + name + ", type=" + type + "]";
	}

   
	@Override
	public  <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}

	
	public boolean isParam() {
		return isParam;
	}
	
	
	
	


}
