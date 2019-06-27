package ast.statements;

import java.util.List;

import ast.expressions.Expression;
import visitor.Visitor;

public class Read extends AbstractStatement{

	
	List<Expression> value;

	public Read(int line, int column, List<Expression> value) {
		super(line, column);
		this.value = value;
	}

	
	@Override
	public String toString() {
		String strin="Read [";
		for (Expression e : value) {
			strin+= e.toString()+",";
		}
		return strin+ "]";
	}
	
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}

	public List<Expression> getExpressions() {
		return value;
	}
}

