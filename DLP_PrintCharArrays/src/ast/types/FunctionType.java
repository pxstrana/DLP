package ast.types;

import java.util.List;

import ast.definitions.VariableDefinition;
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
	public Type functionInvocation( List<Type> parameterTypes) {
		
		if (this.parameters.size() == parameterTypes.size()) {
			for (int i = 0; i < parameterTypes.size(); i++) {
				if (!parameterTypes.get(i).isEquivalent(this.parameters.get(i).getType()) ) {
					return new ErrorType(parameterTypes.get(0).getLine(), parameterTypes.get(0).getColumn(),
							"FunctionInvocation the parameter types dont match");
				}
			}
			return this.returnType;
		} else
			return new ErrorType(parameterTypes.get(0).getLine(), parameterTypes.get(0).getColumn(),
					"Lists of parameters are not the same size in FunctionInvocation");

	}

	
	
	
	
}
