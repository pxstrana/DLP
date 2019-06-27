package ast.statements;

import ast.expressions.Expression;
import visitor.Visitor;

public class Assignment extends AbstractStatement{

	
	Expression assigned;
	Expression value;
	
	public Assignment(int line, int column, Expression assigned, Expression value) {
		super(line, column);
		this.assigned = assigned;
		this.value = value;
	}

	
	public Expression getAssigned() {
		return assigned;
	}


	public Expression getValue() {
		return value;
	}


	@Override
	public String toString() {
		return "Assignment ["+ assigned + " = " + value + "]";
	}
	
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	
}
