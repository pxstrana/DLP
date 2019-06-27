package ast.expressions.literals;

import ast.expressions.AbstractExpression;
import visitor.Visitor;

public class IntLit extends  AbstractExpression{

	int value;
	public IntLit(int line, int column,int value) {
		super(line, column);
		this.value=value;
		
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "IntLit [" + value + "]";
	}
	@Override
	public  <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}

}
