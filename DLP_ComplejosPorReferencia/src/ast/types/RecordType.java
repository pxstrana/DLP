package ast.types;

import visitor.Visitor;

public class RecordType extends AbstractType {

	String name;
	Type type;
	private int offset;

	
	public RecordType(int line, int column, String name, Type type) {
		super(line, column);
		this.name = name;
		this.type = type;
	}
	
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params){
		return v.visit(this, params);
	}

	public Type getType() {
		return type;
		
	}

	
	public String getVariableName() {
		return name;
	}
	
	@Override
	public String getName() {
		return "RecordType";
	}
	
	@Override
	public int numberOfBytes() {
		return this.type.numberOfBytes();
	}

	public void setOffset(int offset) {
		this.offset=offset;
	}
	
	public int getOffset() {
		return offset;
	}

	
	
	
}
