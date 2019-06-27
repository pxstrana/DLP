package ast.types;

import ast.ASTNode;
import error.ErrorType;
import visitor.Visitor;

public class RealType extends AbstractType {

	private static RealType instance = null;

	private RealType() {
		super(0, 0);
	}

	@Override
	public String toString() {
		return "Real";
	}

	@Override
	public <TR, TP> TR accept(Visitor<TR, TP> v, TP params) {
		return v.visit(this, params);
	}

	@Override
	public String getName() {
		return "RealType";
	}

	public static RealType getInstance() {
		if (instance == null) {
			instance = new RealType();
		}
		return instance;
	}

	@Override
	public Type comparison(Type t) {
		if (this == t)
			return this;
		else
			return new ErrorType(t.getLine(), t.getColumn(), "ERROR los tipos no coinciden");
	}

	@Override
	public Type arithmetic(Type t) {
		if (this == t)
			return this;
		else
			return new ErrorType(t.getLine(), t.getColumn(), "ERROR los tipos no coinciden");
	}

	@Override
	public Type cast(Type t) {
		if ( t ==RealType.getInstance()||t ==IntType.getInstance() || t ==CharType.getInstance()) {
			return t;
		}
		return new ErrorType(t.getLine(), t.getColumn(), "Cannot cast Int to" + t.getName());
	}

	@Override
	public Type unaryMinus(ASTNode node) {

		return this;
	}

	@Override
	public int numberOfBytes() {
		return 4;
	}

	@Override
	public String getSubfix() {
		return "f";
	}
	
	@Override
	public boolean isBuiltInType() {
		return true;
	}
	
	@Override
	public boolean isWritable() {
		return true;
	}
}
