package ast.types;

import error.ErrorType;
import visitor.Visitor;

public class CharType extends AbstractType{

	private static CharType instance =null;
	
	private CharType() {
		super(0, 0);
	}

	@Override
	public String toString() {
		return "Char";
	}
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}

	@Override
	public String getName() {
		return "CharType";
	}
	
	
	
	public static CharType getInstance() {
		if(instance ==null) {
			instance =new CharType();
		}
		return instance;
	}

	@Override
	public Type comparison(Type t) {
		if(this == t) return this;
		else 
			return new ErrorType(t.getLine(), t.getColumn(), "Los tipos no coinciden");
	}

	@Override
	public Type arithmetic(Type t) {
		if(this == t) return this;
		else 
			return new ErrorType(t.getLine(), t.getColumn(), "Los tipos no coinciden");
	}


	@Override
	public Type cast(Type t) {
		  if( t ==RealType.getInstance()||t ==IntType.getInstance() || t ==CharType.getInstance()){
	            return t;
	        } 
	        return new ErrorType(t.getLine(), t.getColumn(), "Cannot cast Char to"+t.getName());
	}
	
	@Override
	public Type unaryMinus(int line,int col) {
		
		return this;
	}
	
	
	@Override
	public int numberOfBytes() {
		return 1;
	}

	@Override
	public String getSubfix() {
		return "b";
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
