package ast.expressions.unary;

import ast.expressions.Expression;
import visitor.Visitor;

public class UnaryNegation extends AbstractUnary {

	public UnaryNegation(int line, int column, Expression expression) {
		super(line, column, expression);
		
	}

	@Override
	public  <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
}

