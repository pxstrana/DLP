package codeGeneration;

import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
import ast.types.FunctionType;
import ast.types.RecordType;
import ast.types.StructType;
import visitor.AbstractVisitor;

public class OffsetVisitor extends AbstractVisitor<Void, Void> {
	
	private int globalBytesToSum = 0;
	private int localBytesToSum = 0;

	@Override
	public Void visit(StructType st, Void params) {
		int fieldBytesSum = 0;
		for (RecordType rf : st.getList()) {
			rf.setOffset(fieldBytesSum);
			fieldBytesSum += rf.getType().numberOfBytes();
			rf.accept(this, params);
		}
		return null;
	}
		
	public Void visit(VariableDefinition v, Void params) {
		v.getType().accept(this,params);
		if(v.getScope() == 0) {
			v.setOffset(globalBytesToSum);
			globalBytesToSum += v.getType().numberOfBytes();
		} else if (v.getScope() == 1) {
			localBytesToSum += v.getType().numberOfBytes();
			v.setOffset(-localBytesToSum);
		}
		return null;
	}
	
	public Void visit(FunctionDefinition fd, Void param) {
		localBytesToSum = 0;
        super.visit(fd, param);
        return null;
	}
	
	public Void visit(FunctionType funtionType, Void params) {
		int paramsBytesToSum =  4;
		for(int i = funtionType.getParameters().size() - 1; i >= 0; i--) {
			VariableDefinition vd = funtionType.getParameters().get(i);
			vd.setOffset(paramsBytesToSum);
			paramsBytesToSum += vd.getType().numberOfBytes();
		}
		return null;
	}
}