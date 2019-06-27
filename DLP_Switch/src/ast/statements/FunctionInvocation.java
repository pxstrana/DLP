

package ast.statements;

import java.util.List;

import ast.expressions.AbstractExpression;
import ast.expressions.Expression;
import ast.expressions.Variable;
import visitor.Visitor;

public class FunctionInvocation extends AbstractExpression implements Statement{

	List<Expression> parameters;
	Variable variable;
	
	
	public FunctionInvocation(int line, int column, List<Expression> parameters, Variable name) {
		super(line, column);
		this.parameters = parameters;
		this.variable = name;
	}


	@Override
	public String toString() {
		return "Invocation ["+variable.getName() +" ("+parameters+")]";
	}
	
	
	
	
	public Variable getVariable() {
		return variable;
	}


	


	public List<Expression> getParameters() {
		return this.parameters;
	}


	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}


	
	
}
