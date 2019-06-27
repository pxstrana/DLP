package semantic;

import ast.expressions.*;
import ast.expressions.binary.*;
import ast.expressions.unary.*;
import ast.statements.Assignment;
import ast.statements.FunctionInvocation;
import ast.statements.Read;
import error.ErrorType;
import ast.expressions.literals.*;
import visitor.AbstractVisitor;

public class LValueVisitor extends AbstractVisitor<Void, Void>{


	@Override
	public Void visit(ArithmeticExpression arith, Void param) {
		super.visit(arith, param);
		arith.setLValue(false);
		return null;
	}

	@Override
	public Void visit(CastExpression cast, Void param) {
		super.visit(cast, param);
		cast.setLValue(false);
		return null;
	}

	@Override
	public Void visit(CharLit cl, Void param) {
		super.visit(cl, param);
		cl.setLValue(false);
		return null;
	}

	@Override
	public Void visit(ComparisonExpression comp, Void param) {
		super.visit(comp, param);
		comp.setLValue(false);
		return null;
	}

	@Override
	public Void visit(FieldAccess fa, Void param) {	
		super.visit(fa, param);
		fa.setLValue(true);
		return null;
		
	}

	@Override
	public Void visit(ArrayAccess ind, Void param) {
		super.visit(ind, param);
		ind.setLValue(true);
		return null;
	}

	@Override
	public Void visit(IntLit il, Void param) {
		super.visit(il, param);
		il.setLValue(false);
		return null;
	}

	@Override
	public Void visit(LogicalExpression logical, Void param) {
		super.visit(logical, param);
		logical.setLValue(false);
		return null;
	}

	@Override
	public Void visit(RealLit rl, Void param) {
		super.visit(rl, param);
		rl.setLValue(false);
		return null;
	}

	@Override
	public Void visit(UnaryMinus um, Void param) {
		super.visit(um, param);
		um.setLValue(false);
		return null;
	}

	@Override
	public Void visit(UnaryNegation un, Void param) {
		super.visit(un, param);
		un.setLValue(false);
		return null;
	}

	@Override
	public Void visit(Variable variable, Void param) {
		super.visit(variable, param);
		variable.setLValue(true);
		return null;
	}

	@Override
	public Void visit(FunctionInvocation fi, Void param) {
		super.visit(fi, param);
		fi.setLValue(false);
		return null;
	}
	
	@Override
	public Void visit(Assignment assig, Void param) {
		super.visit(assig, param);
		if (!assig.getAssigned().isLValue()) {
			new ErrorType(assig.getAssigned().getLine(), assig.getAssigned().getColumn(), "Left expression isn´t an Lvalue");
		}
			
		return null;
		
	}


	@Override
	public Void visit(Read read, Void param) {
		super.visit(read, param);
		for (Expression e : read.getExpressions()) {
			if(!e.isLValue())
				new ErrorType(e.getLine(),e.getColumn(),"This value is not a LValue");
		}
		return null;
	}

	
	

}
