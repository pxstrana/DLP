package ast.expressions.literals;

import ast.expressions.AbstractExpression;
import visitor.Visitor;

public class CharLit extends  AbstractExpression {

	
	char value;
	
	public CharLit(int line, int column, char value) {
		super(line, column);
		this.value=value;
		
	}

	public char getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "CharLit [" + value + "]";
	}
	@Override
	public  <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	

	
	
	
}
