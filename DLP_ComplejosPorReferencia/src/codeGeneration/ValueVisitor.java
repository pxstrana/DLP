package codeGeneration;

import ast.expressions.ArrayAccess;
import ast.expressions.CastExpression;
import ast.expressions.FieldAccess;
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
import ast.types.ArrayType;
import ast.types.StructType;
import visitor.AbstractCGVisitor;

public class ValueVisitor extends AbstractCGVisitor<Void, Void> {

	// SOLO EXPRESIONES

	private AdressVisitor addressVisitor;
	private CG cg;

	public ValueVisitor(CG cg) {
		this.cg = cg;
	}

	public void setAddressVisitor(AdressVisitor addressVisitor) {
		this.addressVisitor = addressVisitor;

	}

	// LITERALS

	@Override
	public Void visit(CharLit f, Void param) {
		cg.push(String.valueOf((int) f.getValue()), f.getType());
		return null;
	}

	@Override
	public Void visit(IntLit f, Void param) {
		cg.pushi(f.getValue());
		return null;
	}

	@Override
	public Void visit(RealLit f, Void param) {
		cg.push(String.valueOf(f.getValue()), f.getType());
		return null;
	}

	// BINARY

	@Override
	public Void visit(ArithmeticExpression f, Void param) {
		f.getLeftExpression().accept(this, param);
		f.getRightExpression().accept(this, param);
		cg.arithmetic(f.getOperator(), f.getType());
		return null;
	}

	@Override
	public Void visit(ComparisonExpression f, Void param) {

		f.getLeftExpression().accept(this, param);
		f.getRightExpression().accept(this, param);
		cg.comparison(f.getOperator(), f.getType());
		return null;
	}

	@Override
	public Void visit(LogicalExpression f, Void param) {

		f.getLeftExpression().accept(this, param);
		f.getRightExpression().accept(this, param);
		cg.logical(f.getOperator());
		return null;
	}

	// UNARY
	@Override
	public Void visit(UnaryNegation f, Void param) {
		f.getExpression().accept(this, param);
		cg.not();
		return null;
	}

	@Override
	public Void visit(UnaryMinus f, Void param) {
		cg.push(String.valueOf(0), f.getExpression().getType());
		f.accept(this, param);
		cg.sub(f.getType());
		return null;
	}

	// OTHERS

	// VAR

	@Override
	public Void visit(Variable var, Void params) {
		var.accept(this.addressVisitor, params);
		this.cg.load(var.getType());
		return null;
	}

	// CAST
	@Override
	public Void visit(CastExpression f, Void param) {
		f.getExpression().accept(this, param);
		cg.convert(f.getCastType(), f.getExpression().getType());
		return null;
	}

	// ARRAY ACCESS
	@Override
	public Void visit(ArrayAccess arrayAccess, Void params) {
		arrayAccess.accept(this.addressVisitor, params);
		cg.load(arrayAccess.getType());
		return null;
	}

	// FIELD ACCESS
	@Override
	public Void visit(FieldAccess fa, Void params) {
		fa.accept(this.addressVisitor, params);
		cg.load(fa.getType());
		return null;
	}

	// INVOCATION
	@Override
	public Void visit(FunctionInvocation fi, Void params) {
		fi.getParameters().forEach(arg -> {
			if (arg.getType() instanceof StructType | arg.getType() instanceof ArrayType) {
				arg.accept(this.addressVisitor, params);
			} else {
				arg.accept(this, params);
			}
		});
		cg.call(fi.getVariable().getName());
		return null;
	}
}
