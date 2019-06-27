package ast.definitions;

import ast.statements.Statement;
import ast.types.Type;
import visitor.Visitor;

public class VariableDefinition extends AbstractDefinition implements Statement{



	public VariableDefinition(int line, int column, String name, Type type) {
		super(line, column, name, type);
	}

	@Override
	public String toString() {
		return "VariableDefinition [name=" + name + ", type=" + type + "]";
	}

   
	@Override
	public  <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}

	
	
	
	


}
