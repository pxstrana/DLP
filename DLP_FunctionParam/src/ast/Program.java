package ast;

import java.util.List;

import ast.definitions.Definition;
import visitor.Visitor;

public class Program extends AbstractASTNode{
	List<Definition> definitions;
	
	
	public Program(int line, int column, List<Definition> definitions) {
		super(line, column);
		this.definitions = definitions;
	}
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	public List<Definition> getDefinitions() {
		return definitions;
	}

	

}
