package ast.statements;

import java.util.List;

import ast.expressions.Expression;

public class Case {

	
	Expression caseCondition;
	List<Statement> statements;
	
	public Case(Expression caseCondition, List<Statement> statements) {
		super();
		this.caseCondition = caseCondition;
		this.statements = statements;
	}

	public Expression getCondition() {
		return caseCondition;
	}

	
	public List<Statement> getStatements() {
		return statements;
	}

}
