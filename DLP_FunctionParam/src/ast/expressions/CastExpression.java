
package ast.expressions;

import ast.types.Type;
import visitor.Visitor;

public class CastExpression  extends AbstractExpression{

	Type castType;
	Expression casteable;
	
	
	public CastExpression(int line, int column, Type type, Expression casteable) {
		super(line, column);
		this.castType = type;
		this.casteable = casteable;
	}


	public Type getCastType() {
		return castType;
	}


	public Expression getExpression() {
		return casteable;
	}


	@Override
	public String toString() {
		return "Cast [(" + castType + ") " + casteable + "]";
	}
	
	
	@Override
	public  <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
}
