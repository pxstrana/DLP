package ast.expressions;

import ast.AbstractASTNode;
import ast.types.Type;

public abstract class AbstractExpression extends AbstractASTNode implements Expression {

	boolean LValue;
	Type type;
	
	public AbstractExpression(int line, int column) {
		super(line, column);
		
	}
	
	@Override
	public void setLValue(boolean LValue) {
		this.LValue = LValue;
		
	}

	@Override
	public boolean isLValue() {
		return LValue;
	}

	
	@Override
	public void setType(Type t) {
		this.type=t;
		
	}

	@Override
	public Type getType() {
		return type;
	}

	
}
