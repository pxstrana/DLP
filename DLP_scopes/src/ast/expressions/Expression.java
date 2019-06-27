package ast.expressions;

import ast.ASTNode;
import ast.types.Type;

public interface Expression extends ASTNode{

	
	void setLValue(boolean LValue);
	
	boolean isLValue();
	
	
	void setType(Type t);
	
	Type getType();
}
