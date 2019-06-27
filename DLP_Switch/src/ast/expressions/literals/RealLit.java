package ast.expressions.literals;

import ast.expressions.AbstractExpression;
import visitor.Visitor;

public class RealLit extends AbstractExpression{

	double value;
	
	public RealLit(int line, int column, double value) {
		super(line, column);
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "RealLit [" + value + "]";
	}
	
	@Override
	public  <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
}
