package ast.types;

import error.ErrorType;
import visitor.Visitor;

public class ArrayType extends AbstractType {

	int size;
	Type innerType;

	
	
	public ArrayType(int line, int column) {
		super(line, column);
		
	}
	
	public void setSize(int size) {
		this.size=size;
	}
	
	public void setInnerType(Type innerType) {
		this.innerType=innerType;
	}
	
	public void changeInnerType(ArrayType arrayType) {
		if(innerType instanceof ArrayType) {
			((ArrayType) innerType).changeInnerType(arrayType);
		}else {
			Type previousType=innerType;
			innerType=arrayType;
			arrayType.setInnerType(previousType);
		}
		
	}

	public int getSize() {
		return size;
	}

	public Type getInnerType() {
		return innerType;
	}

	@Override
	public String toString() {
		return "ArrayType [size=" + size + ", innerType=" + innerType + "]";
	}
	
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	@Override
	public String getName() {
		return "ArrayType";
	}
	@Override
	public Type arrayAccess(Type t) {
		if(t instanceof IntType) {
			return this.innerType;
		} else if (t instanceof ErrorType) {
			return t;
		}
		return new ErrorType(t.getLine(),t.getColumn(), "Arrays can only be accessed with int");
	}

	
	@Override
	public int numberOfBytes() {
		return this.size * this.innerType.numberOfBytes();
	}
	
	@Override
    public boolean isEquivalent(Type type) {
        if(type instanceof ArrayType) {
            this.innerType.isEquivalent(((ArrayType) type).getInnerType());
            return true;
        }
        return false;
    }


	
//	@Override
//	public boolean isWritable() {
//		
//		if(innerType instanceof CharType)
//			return true;
//		else return false;
//	}

	
	
}
