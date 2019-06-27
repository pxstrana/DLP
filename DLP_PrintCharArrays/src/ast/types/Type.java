package ast.types;

import java.util.List;

import ast.ASTNode;



public interface Type extends ASTNode {

	boolean isLogical();
	
	String getName();
	String getSubfix();
	
	boolean isEquivalent(Type t);
	boolean isBuiltInType();
	
	boolean isWritable();
	
	boolean isAssignable(Type type);
	
	
	public int numberOfBytes();
	
	public Type arithmetic(Type t);
	public Type logical(Type t);
	public Type comparison(Type t);
	public Type unaryMinus(int line,int col);
	public Type unaryNegation(int line,int col);
	public Type arrayAccess(Type t);
	public Type fieldAccess(String name,int line,int col);
	public Type functionInvocation( List<Type> parameterTypes);
	public Type cast(Type t);
	

	
	


	
	
}
