package codeGeneration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import ast.types.Type;

public class CG {

	private PrintStream printer;
	private String filename;
	private int labelNumber;
	
	public CG(String filename) {//cambiar
		this.filename="outputDLP.txt";
		 try {
	            this.printer = new PrintStream(new FileOutputStream(this.filename));
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	}
	
	public void comment(String arg) {
		printer.println("	' * "+arg);
	}
	
	public void source() {
		printer.println("#source \""+filename+"\"");
	}
	public void line(int i) {
		printer.println("#line "+i);
	}
	
	
	
	public void push(String value,Type type) {
		printer.println("	push"+type.getSubfix()+" "+value);
	}
	
	public void pusha(String value) {
		printer.println("	pusha "+value);
	}
	public void pushi(String value) {
		printer.println("	pushi "+value);
	}
	
	public void pushbp() {
		printer.println("	push bp");
	}
	
	
	public void load(Type type) {
		printer.println("	load"+type.getSubfix());
	}
	
	public void store(Type type) {
		printer.println("	store"+type.getSubfix());
	}
	
	public void pop(Type type) {
		printer.println("	pop"+type.getSubfix());
	}
	public void dup(Type type) {
		printer.println("	dup"+type.getSubfix());
	}
	//ARITHMETIC
	public void add(Type type) {
		printer.println("	add"+type.getSubfix());
	}
	public void addi() {
		printer.println("	addi");
	}
	public void sub(Type type) {
		printer.println("	sub"+type.getSubfix());
	}
	public void mul(Type type) {
		printer.println("	mul"+type.getSubfix());
	}
	public void div(Type type) {
		printer.println("	div"+type.getSubfix());
	}
	public void mod(Type type) {
		printer.println("	mod"+type.getSubfix());
	}
	
	//COMPARISON
	public void gt(Type type) {	
		printer.println("	gt"+type.getSubfix());
	}
	
	public void lt(Type type) {
		printer.println("	lt"+type.getSubfix());
	}
	public void ge(Type type) {
		printer.println("	ge"+type.getSubfix());
	}
	public void le(Type type) {
		printer.println("	le"+type.getSubfix());
	}
	public void eq(Type type) {
		printer.println("	eq"+type.getSubfix());
	}
	public void ne(Type type) {
		printer.println("	ne"+type.getSubfix());
	}
	//LOGICAL
	public void and() {
		printer.println("	and");
	}
	
	public void or() {
		printer.println("	or");
	}
	public void not() {
		printer.println("	not");
	}
	
	//INPUT-OUTPUT
	public void out(Type t) {
		printer.println("	out" + t.getSubfix());
	}
	public void in(Type t) {
		printer.println("	in" + t.getSubfix());
	}
	//CONVERSIONS
	
	public void convert(Type second,Type first) {
		String subfix2=second.getSubfix();
		if (first.getSubfix().equals("b")) {
			if (subfix2.equals("i")) {
				b2i();
			} else if (subfix2.equals("f")) {
				b2i();
				i2f();
			}
		} else if (first.getSubfix().equals("i")) {
			if (subfix2.equals("b")) {
				i2b();
			} else if (subfix2.equals("f")) {
				i2f();
			}
		} else if (first.getSubfix().equals("f")) {
			if (subfix2.equals("i")) {
				f2i();
			} else if (subfix2.equals("b")) {
				f2i();
				i2b();
			}
		}
	}
	
	public void b2i() {
		printer.println("	b2i");
	}
	public void i2f() {
		printer.println("	i2f");
	}
	public void f2i() {
		printer.println("	f2i");
	}
	public void i2b() {
		printer.println("	i2b");
	}
	
	//JUMPS
	
	public void label(String id) {
		printer.println("label"+id+":");
	}
	public void functionlabel(String id) {
		printer.println(id+":");
	}
	
	public void jmp(String label) {
		printer.println("	jmp label"+label);
	}
	public void jz(String label) {
		printer.println("	jz label"+label);
	}
	public void jnz(String label) {
		printer.println("	jnz label"+label);
	}
	
	//FUNCTION
	public void call(String label) {
		printer.println("	call	"+label);
	}
	public void enter(int numOfBytes) {
		printer.println("	enter	"+numOfBytes);
	}
	public void ret(int size,int sizeLocalVariables,int sizeLocalArguments) {
		printer.println("	ret "+size+", "+sizeLocalVariables+", "+sizeLocalArguments);
	}
	public void halt() {
		printer.println("halt");
	}
	
	//DEBUGGING
	
	//OPERATOR
	public void arithmetic(String operator, Type type) {
		switch (operator) {
				case "+": 
					add(type); 
					break;
				case "-": 
					sub(type); 
					break;
				case "*": 
					mul(type); 
					break;
				case "/":
					div(type); 
					break;
				}

	}
	public void comparison(String operator, Type type) {
		switch (operator) {
				case "<": 
					lt(type); 
					break;
				case ">": 
					gt(type); 
					break;
				case "==": 
					eq(type); 
					break;
				case "<=":
					le(type); 
					break;
				case ">=":
					ge(type); 
					break;
				case "!=":
					ne(type); 
					break;
				}

	}
	
	public void logical(String operator) {
		if(operator.equals("&&")) {
			and();
		} else if (operator.equals("||")) {
			or();
		} 
	}

	public void commentRaw(String string) {
		printer.println(string);
	}
	
	public int getLabels(int num) {
		int temp = this.labelNumber;
		this.labelNumber += num;
		return temp;
	}
}
