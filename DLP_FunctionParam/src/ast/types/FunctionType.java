package ast.types;

import java.util.List;

import ast.ASTNode;
import ast.definitions.VariableDefinition;
import ast.expressions.Expression;
import ast.expressions.Variable;
import error.ErrorType;
import visitor.Visitor;

public class FunctionType extends AbstractType{

	Type returnType;
	List<VariableDefinition> parameters;
	
	public FunctionType(int line, int column, Type returnType, List<VariableDefinition> parameters) {
		super(line, column);
		this.returnType = returnType;
		this.parameters = parameters;
	}
	

	public Type getReturnType() {
		return returnType;
	}


	public List<VariableDefinition> getParameters() {
		return parameters;
	}


	@Override
	public String toString() {
		return "FunctionType [returnType=" + returnType + ", parameters=" + parameters + "]";
	}
	
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return v.visit(this, params);
	}
	@Override
	public String getName() {
		return "FunctionType";
	}

	@Override
	public Type functionInvocation( List<Expression> parameters, ASTNode node) {
		
		if (this.parameters.size() == parameters.size()) {
			for (int i = 0; i < parameters.size(); i++) {
				if (!parameters.get(i).getType().isEquivalent(this.parameters.get(i).getType()) ) {
					return new ErrorType(parameters.get(0).getLine(), parameters.get(0).getColumn(),
							"FunctionInvocation the parameter types dont match");
				}
			}
			
			for (int i = 0; i < parameters.size(); i++) {
	            if(parameters.get(i).getType() instanceof FunctionType) {
	            	this.parameters.get(i).setName(((Variable)parameters.get(i)).getName());
	            }
	        }
			
			return this.returnType;
		} else
			return new ErrorType(parameters.get(0).getLine(), parameters.get(0).getColumn(),
					"Lists of parameters are not the same size in FunctionInvocation");

		
		
	}

	
	
	
	
}
