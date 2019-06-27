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
	
	
	public int numberOfBytes();
	
	public Type arithmetic(Type t);
	public Type logical(Type t);
	public Type comparison(Type t);
	public Type unaryMinus(ASTNode node);
	public Type unaryNegation(ASTNode node);
	public Type arrayAccess(Type t);
	public Type fieldAccess(String name,ASTNode node);
	public Type functionInvocation( List<Type> parameterTypes, ASTNode node);
	public Type cast(Type t);


	

	
	


	
	
}
