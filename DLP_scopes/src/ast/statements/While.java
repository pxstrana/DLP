package ast.statements;

import java.util.List;

import ast.expressions.Expression;
import visitor.Visitor;

public class While extends AbstractStatement{
	
	Expression condition;
	List<Statement> body;
	
	public While(int line, int column, Expression condition, List<Statement> body) {
		super(line, column);
		this.condition = condition;
		this.body = body;
	}

	public Expression getCondition() {
		return condition;
	}

	public List<Statement> getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "While [(" + condition + ")]";
	}
	
	
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}

}
