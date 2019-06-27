package visitor;

import ast.Program;
import ast.definitions.Definition;
import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
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
import ast.statements.Statement;
import ast.statements.While;
import ast.statements.Write;
import ast.types.ArrayType;
import ast.types.CharType;
import ast.types.FunctionType;
import ast.types.IntType;
import ast.types.RealType;
import ast.types.RecordType;
import ast.types.StructType;
import ast.types.VoidType;
import error.ErrorType;

public abstract class AbstractVisitor<TR,TP> implements Visitor<TR, TP>{

	
	@Override
	public TR visit(FunctionDefinition a, TP params) {
		a.getType().accept(this, params);
		for (Statement s : a.getBody()) {
			s.accept(this, params);
		}
		return null;
	}

	@Override
	public TR visit(VariableDefinition a, TP params) {
		
		a.getType().accept(this, params);
		return null;
	}

	@Override
	public TR visit(ArithmeticExpression a, TP params) {
		a.getLeftExpression().accept(this, params);
		a.getRightExpression().accept(this, params);
		
		return null;
	}

	@Override
	public TR visit(ComparisonExpression a, TP params) {
		a.getLeftExpression().accept(this, params);
		a.getRightExpression().accept(this, params);
		return null;
		
	}

	@Override
	public TR visit(LogicalExpression a, TP params) {
		a.getLeftExpression().accept(this, params);
		a.getRightExpression().accept(this, params);
		return null;
	}

	@Override
	public TR visit(CharLit a, TP params) {
		
		return null;
	}

	@Override
	public TR visit(IntLit a, TP params) {
		return null;
	}

	@Override
	public TR visit(RealLit a, TP params) {
		return null;
	}

	@Override
	public TR visit(UnaryMinus a, TP params) {
		a.getExpression().accept(this, params);
		return null;
	}

	@Override
	public TR visit(UnaryNegation a, TP params) {
		a.getExpression().accept(this, params);
		return null;
	}

	

	@Override
	public TR visit(CastExpression a, TP params) {
		a.getCastType().accept(this, params);
		a.getExpression().accept(this, params);
		return null;
	}

	@Override
	public TR visit(FieldAccess a, TP params) {
		a.getExpression().accept(this, params);
		return null;
	}

	@Override
	public TR visit(Variable a, TP params) {
		
		return null;
	}

	@Override
	public TR visit(ArrayType a, TP params) {
		a.getInnerType().accept(this, params);

		return null;
	}

	@Override
	public TR visit(CharType a, TP params) {
		return null;
	}

	@Override
	public TR visit(FunctionType a, TP params) {
		a.getReturnType().accept(this, params);
		for (VariableDefinition e : a.getParameters()) {
			e.accept(this, params);
		}
		
		return null;
	}

	@Override
	public TR visit(IntType a, TP params) {
		return null;
	}

	@Override
	public TR visit(RealType a, TP params) {
		return null;
	}

	@Override
	public TR visit(RecordType a, TP params) {
		a.getType().accept(this, params);
		return null;
	}

	@Override
	public TR visit(StructType a, TP params) {
		for (RecordType e : a.getList()) {
			e.accept(this, params);
		}
		return null;
	}

	@Override
	public TR visit(VoidType a, TP params) {
		return null;
	}

	@Override
	public TR visit(ArrayAccess arrayAccess, TP params) {
		arrayAccess.getIndex().accept(this, params);
		arrayAccess.getArray().accept(this, params);
		return null;
	}

	@Override
	public TR visit(Assignment assignment, TP params) {
		assignment.getAssigned().accept(this, params);
		assignment.getValue().accept(this, params);
		
		return null;
	}

	@Override
	public TR visit(FunctionInvocation functionInvocation, TP params) {
		functionInvocation.getVariable().accept(this, params);
		for (Expression e : functionInvocation.getParameters()) {
			e.accept(this, params);
		}
		return null;
	}

	@Override
	public TR visit(If if1, TP params) {
		if1.getCondition().accept(this, params);
		for (Statement e : if1.getIfBody()) {
			e.accept(this, params);
		}
		for (Statement e : if1.getElseBody()) {
			e.accept(this, params);
		}
		return null;
	}

	@Override
	public TR visit(Read read, TP params) {
		for (Expression e : read.getExpressions()) {
			e.accept(this, params);
		}
		return null;
	}

	@Override
	public TR visit(While while1, TP params) {
		while1.getCondition().accept(this, params);
		
		for (Statement e : while1.getBody()) {
			e.accept(this, params);
		}
		return null;
	}

	@Override
	public TR visit(Write write, TP params) {
		for (Expression e : write.getExpressions()) {
			e.accept(this, params);
		}
		return null;
	}

	@Override
	public TR visit(Return return1, TP params) {
		return1.getReturnValue().accept(this, params);
		return null;
	}

	@Override
	public TR visit(Program program, TP params) {
		for (Definition e : program.getDefinitions()) {
			e.accept(this, params);
		}
		return null;
	}

	public TR visit(ErrorType errorType, TP params) {
		
		return null;
	}

	


	
	
	
}
