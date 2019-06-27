package ast.types;

import visitor.Visitor;

public class VoidType extends AbstractType{

	private static VoidType instance =null;
	
	public VoidType() {
		super(0, 0);
		
	}

	@Override
	public String toString() {
		return "Void";
	}
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	
	@Override
	public String getName() {
		return "VoidType";
	}
	
	public static VoidType getInstance() {
		if(instance ==null) {
			instance =new VoidType();
		}
		return instance;
	}

	@Override
	public int numberOfBytes() {
		// TODO Auto-generated method stub
		return 0;
	}
}
