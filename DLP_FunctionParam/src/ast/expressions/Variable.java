package ast.expressions;

import ast.definitions.Definition;
import visitor.Visitor;

public class Variable extends AbstractExpression {

	Definition definition;
	String name;

	public Variable(int line, int column, String value) {
		super(line, column);
		this.name = value;
	}

	@Override
	public String toString() {
		return "Variable ["+name+"]";
	}
	
	

	public Definition getDefinition() {
		return definition;
	}

	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public <TR, TP> TR accept(Visitor<TR, TP> v, TP params) {
		return v.visit(this, params);
	}
	
}
