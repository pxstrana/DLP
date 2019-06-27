package ast.types;

import java.util.List;

import ast.AbstractASTNode;
import error.ErrorType;

public abstract class AbstractType extends AbstractASTNode implements Type {

	public AbstractType(int line, int column) {
		super(line, column);

	}
	
	@Override
	public int numberOfBytes() {
		return 0;
	}

	@Override
	public boolean isEquivalent(Type t) {
		return this.getName().equals(t.getName());
	}

	@Override
	public boolean isLogical() {
		return false;
	}

	
	@Override
	public Type comparison(Type t) {
		return new ErrorType(t.getLine(), t.getColumn(), "Comparison cannot be done");
	}

	@Override
	public Type arithmetic(Type t) {
		return new ErrorType(t.getLine(), t.getColumn(), "Arithmetic cannot be done");
	}

	@Override
	public Type logical(Type t) {
		return new ErrorType(t.getLine(), t.getColumn(), "Logical cannot be done");
	}

	@Override
	public Type unaryMinus(int line,int col) {
		return new ErrorType(line, col, "UnaryMinus cannot be done");
	}

	@Override
	public Type unaryNegation(int line,int col) {
		return new ErrorType(line, col, "UnaryNot cannot be done");
	}

	@Override
	public Type arrayAccess(Type t) {
		return new ErrorType(t.getLine(), t.getColumn(), "Array access cannot be done");
	}

	@Override
	public Type fieldAccess(String name ,int line,int col) {
		return new ErrorType(line, col, "FieldAccess ["+name+"] cannot be done to this type");
	}

	@Override
	public Type functionInvocation( List<Type> parameterTypes) {
		return new ErrorType(parameterTypes.get(0).getLine(), parameterTypes.get(0).getColumn(), "FunctionInvocation cannot be done");
	}

	@Override
	public Type cast(Type t) {
		return  new ErrorType(t.getLine(), t.getColumn(), "Cast cannot be done");
		
	}

	

	@Override
	public String getSubfix() {
		return "";
	}

	@Override
	public boolean isBuiltInType() {
		
		return false;
	}

	@Override
	public boolean isWritable() {
		return false;
	}

	@Override
	public boolean isAssignable(Type type) {
		return false;
	}
	
	

	
	
	
	
	
}
