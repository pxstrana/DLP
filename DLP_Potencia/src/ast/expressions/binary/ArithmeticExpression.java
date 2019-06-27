package ast.expressions.binary;

import ast.expressions.Expression;
import visitor.Visitor;

public class ArithmeticExpression extends AbstractBinaryExpression{

	public ArithmeticExpression(int line, int column, Expression leftExpression,String operator ,Expression rightExpression) {
		super(line, column, leftExpression, operator, rightExpression);
	
	}

	
	@Override
	public  <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	
	
	
}
