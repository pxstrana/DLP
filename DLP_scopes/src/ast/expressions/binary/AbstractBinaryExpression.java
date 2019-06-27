package ast.expressions.binary;

import ast.expressions.AbstractExpression;
import ast.expressions.Expression;

public abstract class AbstractBinaryExpression extends AbstractExpression{

	
	Expression leftExpression;
	Expression rightExpression;
	String operator;
	
	
	
	public AbstractBinaryExpression(int line, int column, Expression leftExpression, String operator,Expression rightExpression) {
		super(line, column);
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		this.operator=operator;
	}

	
	
	
	
	public Expression getLeftExpression() {
		return leftExpression;
	}





	public Expression getRightExpression() {
		return rightExpression;
	}





	public String getOperator() {
		return operator;
	}





	@Override
	public String toString() {
		return "BinaryExp [" + leftExpression +" "+ operator + " " +rightExpression + "]";
	}
	
	
}
