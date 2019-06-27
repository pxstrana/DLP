package ast.statements;

import ast.expressions.Expression;
import visitor.Visitor;

public class Return extends AbstractStatement{
	
	Expression returnValue;

	public Return(int line, int column, Expression returnValue) {
		super(line, column);
		this.returnValue = returnValue;
	}

	public Expression getReturnValue() {
		return returnValue;
	}

	@Override
	public String toString() {
		return "Return [returnValue=" + returnValue + "]";
	}
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}

	public void setReturnValue(Expression returnValue) {
		this.returnValue = returnValue;
	}
	
}
