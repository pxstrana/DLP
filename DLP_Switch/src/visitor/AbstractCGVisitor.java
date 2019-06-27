package visitor;

import ast.Program;
import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
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
import ast.statements.Assignment;
import ast.statements.FunctionInvocation;
import ast.statements.If;
import ast.statements.Read;
import ast.statements.Return;
import ast.statements.Switch;
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

public abstract class AbstractCGVisitor<TR,TP> extends AbstractVisitor<TR, TP> {

	@Override
	public TR visit(FunctionDefinition funcDef, TP params) {
		throw new IllegalStateException("Function declaration is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(VariableDefinition varDef, TP params) {
		throw new IllegalStateException("Variable declaration is not supported by '" + this.getClass().getName() + "'");

	}

	@Override
	public TR visit(ArrayAccess arrayAccess, TP params) {
		throw new IllegalStateException("Array access is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(ArithmeticExpression arith, TP params) {
		throw new IllegalStateException("Arithmetic operator is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(LogicalExpression logical, TP params) {
		throw new IllegalStateException("Logical operator is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(ComparisonExpression comparison, TP params) {
		throw new IllegalStateException("Comparison operator is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(CastExpression cast, TP params) {
		throw new IllegalStateException("Cast is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(CharLit charL, TP params) {
		throw new IllegalStateException("Char literal is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(FieldAccess fieldAccess, TP params) {
		throw new IllegalStateException("Field access is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(IntLit intL, TP params) {
		throw new IllegalStateException("Int literal is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(RealLit realL, TP params) {
		throw new IllegalStateException("Real literal is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(UnaryMinus unaryMinus, TP params) {
		throw new IllegalStateException("Unary minus is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(UnaryNegation unaryNot, TP params) {
		throw new IllegalStateException("Unary not is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(Variable var, TP params) {
		throw new IllegalStateException("Variable is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(Program program, TP params) {
		throw new IllegalStateException("Program is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(Assignment assignment, TP params) {
		throw new IllegalStateException("Assignment is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(If ifElse, TP params) {
		throw new IllegalStateException("IfElse is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(FunctionInvocation invocation, TP params) {
		throw new IllegalStateException("Function Invocation is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(Read read, TP params) {
		throw new IllegalStateException("Read is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(Return return1, TP params) {
		throw new IllegalStateException("Return is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(While while1, TP params) {
		throw new IllegalStateException("While is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(Write write, TP params) {
		throw new IllegalStateException("Write is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(ArrayType arrayType, TP params) {
		throw new IllegalStateException("Array type is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(CharType charType, TP params) {
		throw new IllegalStateException("Char type is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(ErrorType errorType, TP params) {
		throw new IllegalStateException("Error type is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(FunctionType funcType, TP params) {
		throw new IllegalStateException("Function type is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(IntType intType, TP params) {
		throw new IllegalStateException("Integer type is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(RealType realType, TP params) {
		throw new IllegalStateException("Real type is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(RecordType recordField, TP params) {
		throw new IllegalStateException("Record field is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(StructType structType, TP params) {
		throw new IllegalStateException("Struct type is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(VoidType voidType, TP params) {
		throw new IllegalStateException("Void type is not supported by '" + this.getClass().getName() + "'");
	}

	@Override
	public TR visit(Switch switch1, TP params) {
		throw new IllegalStateException("Swtich type is not supported by '" + this.getClass().getName() + "'");
	}

	
	

	
}
