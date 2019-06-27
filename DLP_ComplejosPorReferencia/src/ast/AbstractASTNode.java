package ast;

public abstract class AbstractASTNode implements ASTNode {

	int line;
	int column;
	
	
	
	public AbstractASTNode(int line, int column) {
		super();
		this.line = line;
		this.column = column;
	}

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return column;
	}
	
	
}
