package ast.expressions.unary;

import ast.expressions.AbstractExpression;
import ast.expressions.Expression;

public abstract class AbstractUnary extends AbstractExpression{

	Expression expression;

	public AbstractUnary(int line, int column, Expression expression) {
		super(line, column);
		this.expression = expression;
	}

	
	
	
	public Expression getExpression() {
		return expression;
	}




	@Override
	public String toString() {
		return "AbstractUnary [expression=" + expression + "]";
	}
	
	
	
}
