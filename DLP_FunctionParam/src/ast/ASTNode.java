package ast;

import visitor.Visitor;

public interface ASTNode {
	int getLine();
	int getColumn();
	
	 <TR,TP> TR accept(Visitor<TR,TP> v,TP params);

}
