package ast.statements;

import java.util.ArrayList;
import java.util.List;

import ast.expressions.Expression;
import visitor.Visitor;

public class Switch extends AbstractStatement{

	Expression condition;
	
	List<Case> casesList=new ArrayList<>();

	public Switch(int line, int column, Expression condition) {
		super(line, column);
		this.condition = condition;
		
	}

	public void addCase(Expression caseCondition, List<Statement> statements) {
		this.casesList.add(new Case( caseCondition,  statements));
		
	}
	
	
	
	public Expression getCondition() {
		return condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public List<Case> getCasesList() {
		return casesList;
	}

	public void setCasesList(List<Case> casesList) {
		this.casesList = casesList;
	}

	@Override
	public <TR, TP> TR accept(Visitor<TR, TP> v, TP params) {
		v.visit(this, params);
		return null;
	}

}
