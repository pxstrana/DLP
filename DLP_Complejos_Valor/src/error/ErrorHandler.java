package error;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

	List<ErrorType> listError;
	private static ErrorHandler instance =null;
	
	private ErrorHandler() {
		listError=new ArrayList<>();
	}
	
	public static ErrorHandler getInstance() {
		if(instance ==null) {
			instance =new ErrorHandler();
		}
		return instance;
	}
	
	
	public void addError(ErrorType error) {
		this.listError.add(error);
	}
	
	public boolean isAnyError() {
		if(listError.size()>0)
			return true;
		return false;
			
		}
	
	public void showErrors(PrintStream out) {
		for (ErrorType errorType : listError) {
			out.println("ERROR ["+errorType.getLine()+", "+errorType.getColumn()+"]"+errorType.toString());
		}
	}
	
}
