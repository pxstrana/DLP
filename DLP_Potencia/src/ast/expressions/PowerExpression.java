package ast.expressions;

import visitor.Visitor;

public class PowerExpression extends AbstractExpression {

	
	Expression base;
	Expression exponent;
	
	public PowerExpression(int line, int column, Expression base, Expression exponent) {
		super(line, column);
		this.base = base;
		this.exponent = exponent;
	}

	@Override
	public <TR, TP> TR accept(Visitor<TR, TP> v, TP params) {
		return v.visit(this, params);
	}

	public Expression getBase() {
		return base;
	}

	public void setBase(Expression base) {
		this.base = base;
	}

	public Expression getExponent() {
		return exponent;
	}

	public void setExponent(Expression exponent) {
		this.exponent = exponent;
	}
	
	
	
	
}
