package parser;

public class LexerHelper {
	
	public static int lexemeToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}
	
	public static char lexemeToChar(String str) {
		
		try {
			str=str.replace("'", "");
			if(str.length()==1) {
			return str.charAt(0);
			}
			if(str.charAt(0)=='\\' && Character.isDigit(str.charAt(1))) {
				return (char)Integer.parseInt(str.substring(1));
			}
			else if(str.charAt(0)=='\\' && str.charAt(1)=='n'){
				return '\n';
			}else if(str.charAt(0)=='\\' && str.charAt(1)=='t'){
				return '\t';
			}
			
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return 'f';
		
//		if(str.length()>=2 && Character.isDigit(str.charAt(1)))
//			str.replace("\\", "");
	}

	
	public static double lexemeToReal(String str) {
		try {
			return Double.parseDouble(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}


	public static void main(String[] args) {
		String str="\\127";
		System.out.println(str);
		str=str.substring(1);
		int num=Integer.parseInt(str);
		String ch=Character.toString((char)Integer.parseInt(str));
		System.out.println(num+" "+ch+" "+str);
		
		System.out.println((int)'\n');
	}
	
}
