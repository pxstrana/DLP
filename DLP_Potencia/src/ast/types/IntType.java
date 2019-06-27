package ast.types;

import ast.ASTNode;
import ast.expressions.unary.UnaryNegation;
import error.ErrorType;
import visitor.Visitor;

public class IntType extends AbstractType{
	
	private static IntType instance =null;

	public IntType() {
		super(0, 0);
	}

	@Override
	public String toString() {
		return "Int";
	}
	@Override
	public<TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	
	@Override
	public boolean isLogical() {
		return true;
	}
	
	@Override
	public String getName() {
		return "IntType";
	}
	
	public static IntType getInstance() {
		if(instance ==null) {
			instance =new IntType();
		}
		return instance;
	}

	@Override
	public Type comparison(Type t) {
		if(this.isEquivalent(t) ) return this;
		else 
			return new ErrorType(t.getLine(), t.getColumn(), "Los tipos no coinciden");
	}

	@Override
	public Type arithmetic(Type t) {
		if(this.isEquivalent(t) ) return this;
		else 
			return new ErrorType(t.getLine(), t.getColumn(), "Los tipos no coinciden");
	}

	@Override
	public Type logical(Type t) {
		if(this.isEquivalent(t) ) return this;
		else  
			return new ErrorType(t.getLine(), t.getColumn(), "Los tipos no coinciden");
	}

	
	

	public Type unaryNegation(UnaryNegation un) {
		return this;
	}
	@Override
	public Type unaryMinus(ASTNode node) {
		
		return this;
	}
	
	@Override
	public Type cast(Type t) {
		  if( t ==RealType.getInstance()||t ==IntType.getInstance() || t ==CharType.getInstance()) {
	            return t;
	        } 
	        return new ErrorType(t.getLine(), t.getColumn(), "Cannot cast Int to "+t.getName());
	}

	@Override
	public int numberOfBytes() {
		return 2;
	}
	@Override
	public String getSubfix() {
		return "i";
	}
	
	@Override
	public boolean isBuiltInType() {
		return true;
	}
	
	
	@Override
	public boolean isWritable() {
		return true;
	}

	@Override
	public boolean isInteger() {
		return true;
	}
	
	
}
