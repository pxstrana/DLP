package ast.types;

import java.util.List;

import ast.ASTNode;
import error.ErrorType;
import visitor.Visitor;

public class StructType extends AbstractType{

	List<RecordType> list;

	public StructType(int line, int column, List<RecordType> list) {
		super(line, column);
		this.list = list;
	}

	
	@Override
	public String getName() {
		return "StructType";
	}
	
	public List<RecordType> getList() {
		return list;
	}

	
	public RecordType getField(String fieldName) {
		for(RecordType rf : getList()) {
			if(rf.getVariableName().equals(fieldName)) {
				return rf;
			}
		}
		return null;
	}
	
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	
	

	@Override
	public Type fieldAccess(String name,ASTNode node) {
	
		for (RecordType recordType : list) {
			if(recordType.getVariableName().equals(name))
			{
				return recordType.type;
			}
		}
		return new ErrorType(node.getLine()	,node.getColumn(),"Trying to access non existing field");
		
	}
	
	@Override
	public int numberOfBytes() {
		int total = 0;
		for(RecordType rt : this.list) {
			total += rt.numberOfBytes();
		}
		return total;
	}
	@Override
	public String toString() {
		return "StructType [listVarDef=" + list + "]";
	}


	@Override
	public boolean isEquivalent(Type t) {
		StructType aux=(StructType) t;
		
		if(!(t instanceof StructType))
			return false;
		if(list.size() != aux.getList().size()) 
			return false;
		
		for (int i = 0; i < list.size(); i++) {
			if(!(aux.getList().get(i).getType().isEquivalent(list.get(i).type)))
				return false;
		}
		
		return true;
	}
	
	
	
}
