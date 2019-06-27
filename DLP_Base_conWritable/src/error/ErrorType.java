package error;

import ast.types.AbstractType;
import ast.types.Type;
import visitor.Visitor;

public class ErrorType extends AbstractType{

	private String message;

	public ErrorType(int line, int column, String message) {
		super(line, column);
		this.message = message;
		ErrorHandler.getInstance().addError(this);
	}
	@Override
	public <TR,TP> TR accept(Visitor<TR,TP> v,TP params) {
		return null;
	}
	
	public String toString() {
		return message;
	}

	@Override
	public String getName() {
		return "ErrorType";
	}
	

	@Override
	public Type arrayAccess(Type t) {
		return  this;
	}
}
