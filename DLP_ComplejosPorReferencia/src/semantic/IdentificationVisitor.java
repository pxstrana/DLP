package semantic;

import java.util.ArrayList;
import java.util.List;

import ast.definitions.Definition;
import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
import ast.expressions.Variable;
import ast.types.RecordType;
import ast.types.StructType;
import error.ErrorType;
import symboltable.SymbolTable;
import visitor.AbstractVisitor;

public class IdentificationVisitor extends AbstractVisitor<Void, Void> {

	SymbolTable table=new SymbolTable();
	
	public Void visit(FunctionDefinition fDefinition, Void params) {
		if (!table.insert(fDefinition)) {
			new ErrorType(fDefinition.getLine(), fDefinition.getColumn(),
					"Function:" + fDefinition.getName() + " already exists. ");
		}

		table.set();
		super.visit(fDefinition, params);
		table.reset();

		return null;
    }

	@Override
	public Void visit(VariableDefinition a, Void params) {

		super.visit(a, params);
		if(!table.insert(a)) {
			new ErrorType(a.getLine(), a.getColumn(), "Variable: "+a.getName()
			+"  already defined");
		}
		
		
		return null;
	}

	

	@Override
	public Void visit(Variable a, Void params) {
		
		Definition definition = table.find(a.getName());
		if(definition==null)
			new ErrorType(a.getLine(), a.getColumn(), "Variable: "+a.getName()
			+" is not defined");
		else
			a.setDefinition(definition);
		
		return null;
	}

	public Void visit(StructType a, Void param) {
        super.visit(a, param);
        List<String> found = new ArrayList<String>();
        for(RecordType rt : a.getList()) {
            if(found.contains(rt.getVariableName())) {
                new ErrorType( rt.getLine(), rt.getColumn(),"There is already a record field with name:"+rt.getVariableName());
            }
            found.add(rt.getVariableName());
        }
        return null;
    }
	
	
}
