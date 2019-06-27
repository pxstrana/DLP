package ast.definitions;

import java.util.List;

import ast.statements.Statement;
import ast.types.Type;
import visitor.Visitor;

public class FunctionDefinition extends  AbstractDefinition{

	List<Statement> body;
	
	public FunctionDefinition(int line, int column, String name, Type type, List<Statement> body) {
		super(line, column, name, type);
		this.body = body;
	}
	@Override
	public String toString() {
		return "FunctionDefinition [ name=" + name + ", type=" + type + "]";
	}
	@Override
	public  <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	public List<Statement> getBody() {
		return body;
	}
	@Override
	public int getScope() {
		return 0;
	}
	@Override
	public void setScope(int i) {	
		
	}
	
	

	
	
	
	

	

}
