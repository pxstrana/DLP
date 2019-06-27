package ast.expressions;

import visitor.Visitor;

public class FieldAccess extends AbstractExpression{


	private String name;
	private Expression expression;
	
	
	public FieldAccess(int line, int column, String name, Expression expression) {
		super(line, column);
		this.name = name;
		this.expression = expression;
	}


	public String getName() {
		return name;
	}


	public Expression getExpression() {
		return expression;
	}


	@Override
	public String toString() {
		return "FieldAccess ["+expression+"."+name+"]";
	}
	
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params){
		return v.visit(this, params);
	}
	
	
	
}
