package ast.statements;



import java.util.ArrayList;
import java.util.List;

import ast.expressions.Expression;
import visitor.Visitor;

public class If extends AbstractStatement{

	Expression condition;
	List<Statement> ifBody;
	List<Statement> elseBody;
	
	
	
	public If(int line, int column, Expression condition, List<Statement> ifBody) {
		super(line, column);
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody=new ArrayList<>();
	}



	public void setElse(List<Statement> elseBody){
		this.elseBody=elseBody;
	}
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}

	@Override
	public String toString() {
		return "If [(" + condition + ")]";
	}



	public Expression getCondition() {
		return condition;
	}



	public List<Statement> getIfBody() {
		return ifBody;
	}



	public List<Statement> getElseBody() {
		return elseBody;
	}

	
	
}
