package semantic;

import java.util.stream.Collectors;

import ast.definitions.FunctionDefinition;
import ast.expressions.ArrayAccess;
import ast.expressions.CastExpression;
import ast.expressions.Expression;
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
import ast.statements.Assignment;
import ast.statements.FunctionInvocation;
import ast.statements.If;
import ast.statements.Read;
import ast.statements.Return;
import ast.statements.While;
import ast.statements.Write;
import ast.types.CharType;
import ast.types.FunctionType;
import ast.types.IntType;
import ast.types.RealType;
import ast.types.Type;
import error.ErrorType;
import visitor.AbstractVisitor;

public class TypeCheckingVisitor extends AbstractVisitor<Void, Type> {

	// DEFINITIONS

	public Void visit(FunctionDefinition a, Type t) {
		super.visit(a, ((FunctionType) a.getType()).getReturnType());
		return null;
	}

	// STATEMENTS

	public Void visit(If if1, Type t) {
		super.visit(if1, t);
		if (!if1.getCondition().getType().isLogical()) {
			new ErrorType(if1.getLine(), if1.getColumn(),
					"If condition must be an int");
		}
		return null;
	}

	public Void visit(While while1, Type t) {
		super.visit(while1, t);
		if (!while1.getCondition().getType().isLogical()) {
			new ErrorType(while1.getLine(), while1.getColumn(),
					" While condition must be an int");
		}
		return null;
	}
	//cambiar a isAssigned
	public Void visit(Assignment assignment, Type t) {
		super.visit(assignment, t);
		
		if(!(assignment.getAssigned().getType() instanceof ErrorType ||
				assignment.getValue().getType() instanceof ErrorType)) {
		if (!assignment.getAssigned().getType().isEquivalent(assignment.getValue().getType())) {
			new ErrorType(assignment.getLine(), assignment.getColumn(),
					"Assigment cannot be of different types");
		}
		}
		return null;
	}

	public Void visit(Return a, Type t) {
		super.visit(a, t);
		if (!a.getReturnValue().getType().isEquivalent(t)) {
			new ErrorType(a.getLine(), a.getColumn(),
					"El return type no coincide");
		}
		return null;

	}
	
	@Override
	public Void visit(Read f, Type param) {
		super.visit(f,param);
		for (Expression e : f.getExpressions()) {
			if(!e.getType().isBuiltInType()) {
				new ErrorType(e.getLine(), e.getColumn(), "Read expression is not a builtInType");
			}
		}
		return null;
	}
	@Override
	public Void visit(Write f, Type param) {
		super.visit(f,param);
		for (Expression e : f.getExpressions()) {
			if(!e.getType().isWritable()) {
				new ErrorType(e.getLine(), e.getColumn(), "Write expression is not writable");
			}
		}
		return null;
	}
	@Override
	public Void visit(FunctionInvocation i, Type t) {
		super.visit(i, t);
		i.setType(i.getVariable().getType().functionInvocation( 
				i.getParameters().stream().map(e -> e.getType()).collect(Collectors.toList())));
		return null;
	}

	// EXPRESSIONS

	
	// binary
	public Void visit(ArithmeticExpression a, Type t) {
		super.visit(a, t);
		a.setType(a.getLeftExpression().getType().arithmetic(a.getRightExpression().getType()));
		return null;

	}

	

	public Void visit(ComparisonExpression a, Type t) {
		super.visit(a, t);
		a.setType(a.getLeftExpression().getType().comparison(a.getRightExpression().getType()));
		return null;
	}

	public Void visit(LogicalExpression a, Type t) {
		super.visit(a, t);
		a.setType(a.getLeftExpression().getType().logical(a.getRightExpression().getType()));
		return null;
	}

	// unary

	public Void visit(UnaryMinus unaryMinus, Type param) {
		super.visit(unaryMinus, param);
		unaryMinus.setType(unaryMinus.getExpression().getType().unaryMinus(unaryMinus.getLine(),unaryMinus.getColumn()));
		return null;
	}

	public Void visit(UnaryNegation unaryNegation, Type param) {
		super.visit(unaryNegation, param);
		unaryNegation.setType(unaryNegation.getExpression().getType().unaryNegation(unaryNegation.getLine(),unaryNegation.getColumn()));	
		return null;
	}

	// specials

	public Void visit(ArrayAccess array, Type t) {
		super.visit(array, t);
		Type type = array.getArray().getType().arrayAccess(array.getIndex().getType());
		array.setType(type);
		return null;
	}

	public Void visit(CastExpression c, Type t) {
		super.visit(c, t);
		c.setType(c.getExpression().getType().cast(c.getCastType()));
		return null;
	}

	public Void visit(FieldAccess fa, Type t) {
		super.visit(fa, t);
		fa.setType(fa.getExpression().getType().fieldAccess(fa.getName(), fa.getLine(), fa.getColumn()));
		return null;
	}

	// var
	public Void visit(Variable c, Type t) {
		super.visit(c, t);		
		 if (c.getDefinition() == null) {
	            c.setType(new ErrorType(c.getLine(),c.getColumn(),  "Variable has no definiton"));
	        } else {
	            c.setType(c.getDefinition().getType());
	        }
	        return null;
	
	}

	// literals
	public Void visit(CharLit c, Type t) {
		c.setType(CharType.getInstance());
		return null;
	}

	public Void visit(IntLit c, Type t) {
		c.setType(IntType.getInstance());
		return null;
	}

	public Void visit(RealLit c, Type t) {
		c.setType(RealType.getInstance());
		return null;
	}

}
