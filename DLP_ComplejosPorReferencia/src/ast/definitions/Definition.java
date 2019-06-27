package ast.definitions;

import ast.ASTNode;
import ast.types.Type;

public interface Definition extends ASTNode{

	public int getScope();
	
	public void setScope(int i) ;
	
	public String getName();
	
	public Type getType();
}
