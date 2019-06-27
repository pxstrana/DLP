package ast.expressions;

import visitor.Visitor;

public class ArrayAccess extends AbstractExpression{

	Expression index;
	Expression array;
	
	public ArrayAccess(int line, int column,  Expression array,Expression index) {
		super(line, column);
		this.index = index;
		this.array = array;
	}

	public Expression getIndex() {
		return index;
	}

	public Expression getArray() {
		return array;
	}

	@Override
	public String toString() {
		return "ArrayAccess ["+array.toString()+"["+index.toString()+"]]";
	}
	@Override
	public  <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	
	
}
