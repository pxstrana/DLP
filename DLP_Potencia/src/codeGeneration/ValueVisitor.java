package codeGeneration;

import ast.definitions.Definition;
import ast.expressions.ArrayAccess;
import ast.expressions.CastExpression;
import ast.expressions.FieldAccess;
import ast.expressions.PowerExpression;
import ast.expressions.Variable;
import ast.expressions.binary.ArithmeticExpression;
import ast.expressions.binary.ComparisonExpression;
import ast.expressions.binary.LogicalExpression;
import ast.expressions.literals.CharLit;
import ast.expressions.literals.IntLit;
import ast.expressions.literals.RealLit;
import ast.expressions.unary.UnaryMinus;
import ast.expressions.unary.UnaryNegation;
import ast.statements.FunctionInvocation;
import ast.types.IntType;
import visitor.AbstractCGVisitor;

public class ValueVisitor extends AbstractCGVisitor<Void, Definition> {

	
	//SOLO EXPRESIONES
	
	private AdressVisitor addressVisitor;
	private CG cg;

	public ValueVisitor(CG cg) {
		this.cg=cg;
	}

	public void setAddressVisitor(AdressVisitor addressVisitor) {
		this.addressVisitor=addressVisitor;
		
	}
	
	
	//LITERALS
	
	
	@Override
	public Void visit(CharLit f, Definition param) {
		cg.push(String.valueOf((int)f.getValue()),f.getType());
		return null;
	}

	@Override
	public Void visit(IntLit f, Definition param) {
		cg.pushi(String.valueOf(f.getValue()));
		return null;
	}

	@Override
	public Void visit(RealLit f, Definition param) {
		cg.push(String.valueOf(f.getValue()),f.getType());
		return null;
	}
	
	
	//BINARY
	
	@Override
	public Void visit(ArithmeticExpression arith, Definition params) {		
	
			arith.getLeftExpression().accept(this, params);
			arith.getRightExpression().accept(this, params);
			cg.arithmetic(arith.getOperator(), arith.getType());
		
		return null;
	}
	
	@Override
	public Void visit(PowerExpression power, Definition params) {
		this.cg.comment("' * BEGIN: Power");

		int labelNumber = this.cg.getLabels(2);
		
		// initialization (int i = right)
		this.cg.pushbp();
		this.cg.pushi("" + -2);
		this.cg.addi();
		power.getExponent().accept(this, null);
		this.cg.pushi( "1");
		this.cg.sub(new IntType());
		this.cg.store(new IntType());

		power.getBase().accept(this, params);//the first

		this.cg.label(labelNumber);
		// condition (i > 0)
		this.cg.pushbp();
		this.cg.pushi("" + -2);
		this.cg.addi();
		this.cg.load(new IntType()); // value of i
		this.cg.pushi("" + 0); // min length (0)
		this.cg.sub(new IntType()); // substract
		this.cg.jz(labelNumber + 1); // 0, both equal, exit

		// body
		power.getBase().accept(this, params);
		this.cg.mul(power.getBase().getType());

		// decrement (i--)
		this.cg.pushbp();
		this.cg.pushi("" + -2);
		this.cg.addi(); // address of i
		this.cg.pushbp();
		this.cg.pushi("" + -2);
		this.cg.addi();
		this.cg.load(new IntType()); // value of i
		this.cg.pushi( "1"); // --
		this.cg.sub(new IntType());
		this.cg.store(new IntType()); // new i in the address mentioned

		this.cg.jmp(labelNumber);
		this.cg.label(labelNumber + 1);

		this.cg.comment("' * END: Power");
		return null;
	}

	@Override
	public Void visit(ComparisonExpression f, Definition param) {
		
		f.getLeftExpression().accept(this, param);
		f.getRightExpression().accept(this, param);
		cg.comparison(f.getOperator(), f.getType());
		return null;
	}
	@Override
	public Void visit(LogicalExpression f, Definition param) {
		
		f.getLeftExpression().accept(this, param);
		f.getRightExpression().accept(this, param);
		cg.logical(f.getOperator());
		return null;
	}
	
	
	//UNARY
	@Override
	public Void visit(UnaryNegation f, Definition param) {
		f.getExpression().accept(this, param);
		cg.not();
		return null;
	}
	

	@Override
	public Void visit(UnaryMinus f, Definition param) {
		cg.push(String.valueOf(0),f.getExpression().getType());
		f.accept(this, param);
		cg.sub(f.getType());
		return null;
	}
	
	
	//OTHERS
	
	//VAR

		@Override
		public Void visit(Variable f, Definition param) {
			f.accept(addressVisitor, null);
			cg.load(f.getType());
			return null;
		}
	
	//CAST
		@Override
		public Void visit(CastExpression f, Definition param) {
			f.getExpression().accept(this, param);
			cg.convert(f.getCastType(), f.getExpression().getType());
			return null;
		}
		
	//ARRAY ACCESS
		@Override
		public Void visit(ArrayAccess arrayAccess, Definition params) {
			arrayAccess.accept(this.addressVisitor, null);
			cg.load(arrayAccess.getType());
			return null;
		}
		
	//FIELD ACCESS
		@Override
		public Void visit(FieldAccess fa, Definition params) {
			fa.accept(this.addressVisitor, null);
			cg.load(fa.getType());
			return null;
		}
		
	//INVOCATION                
		@Override
		public Void visit(FunctionInvocation fi, Definition params) {
			fi.getParameters().forEach(arg -> {
				arg.accept(this, params);
			});
			cg.call(fi.getVariable().getName());
			return null;
		}
}
