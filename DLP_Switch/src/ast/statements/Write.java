package ast.statements;

import java.util.List;

import ast.expressions.Expression;
import visitor.Visitor;

public class Write extends AbstractStatement{

	
	List<Expression> value;

	public Write(int line, int column, List<Expression> value) {
		super(line, column);
		this.value = value;
	}

	public List<Expression> getExpressions(){
		return value;
		
	}
	@Override
	public String toString() {
		return "Start of Write";
	}
	
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
}
