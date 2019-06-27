package codeGeneration;

import ast.definitions.VariableDefinition;
import ast.expressions.ArrayAccess;
import ast.expressions.FieldAccess;
import ast.expressions.Variable;
import ast.types.IntType;
import ast.types.StructType;
import visitor.AbstractCGVisitor;

public class AdressVisitor extends AbstractCGVisitor<Void,Void> {
	
	//SOLO PARA NODOS CON LVALUE
	
	private ValueVisitor valueVisitor;
	private CG cg;

	public AdressVisitor(CG cg) {
		this.cg=cg;
	}
 
	public void setValueVisitor(ValueVisitor valueVisitor2) {
		this.valueVisitor=valueVisitor2;
		
	}
	
	@Override
	public Void visit (Variable f, Void param) {
		if(f.getDefinition().getScope() == 0) {
			cg.pusha(String.valueOf(((VariableDefinition)f.getDefinition()).getOffset())); //push la direccion global
		} else {
			cg.pushbp(); //base pointer
			cg.pushi(String.valueOf(((VariableDefinition)f.getDefinition()).getOffset())); //offset respecto bp
			cg.add(IntType.getInstance());//push la direccion local
		}
		return null;
	} 

	@Override
	public Void visit(FieldAccess fieldA, Void params) {
		fieldA.getExpression().accept(this, params); //sacamos la direccion de la expresion(Struct n este caso)
		
		cg.pushi("" + ((StructType) fieldA.getExpression().getType()).getField(fieldA.getName()).getOffset()); //sacamos el offset del campo
		cg.addi(); //sumamos ambos
		return null;
	}

	@Override
	public Void visit(ArrayAccess array, Void params) {
		array.getArray().accept(this, params); //sacamos direccion de la array
		array.getIndex().accept(valueVisitor, params); //sacamos el valor del indice
		cg.pushi("" + array.getType().numberOfBytes()); //bytes del tipo de array (int 2,double 4...)
		cg.mul(array.getIndex().getType()); //multiplicamos por el indice
		cg.addi(); //sumamos todo
		return null;
	}
}
