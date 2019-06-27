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


public interface Visitor<TR,TP> {

	public TR visit(FunctionDefinition a,TP params);
	
	public TR visit(VariableDefinition a,TP params);
	
	public TR visit(ArithmeticExpression a,TP params);
	public TR visit(ComparisonExpression a,TP params);
	public TR visit(LogicalExpression a,TP params);
	
	public TR visit(CharLit a,TP params);
	public TR visit(IntLit a,TP params);
	public TR visit(RealLit a,TP params);
	public TR visit(UnaryMinus a,TP params);
	public TR visit(UnaryNegation a,TP params);
	
	
	public TR visit(CastExpression a,TP params);
	public TR visit(FieldAccess a,TP params);
	public TR visit(Variable a,TP params);
	
	
	public TR visit(ArrayType a,TP params);
	public TR visit(CharType a,TP params);
	public TR visit(FunctionType a,TP params);
	public TR visit(IntType a,TP params);
	public TR visit(RealType a,TP params);
	public TR visit(RecordType a,TP params);
	public TR visit(StructType a,TP params);
	public TR visit(VoidType a,TP params);

	public TR visit(ArrayAccess arrayAccess, TP params);

	public TR visit(Assignment assignment, TP params);

	public TR visit(FunctionInvocation functionInvocation, TP params);

	public TR visit(If if1, TP params);

	public TR visit(Read read, TP params);

	public TR visit(While while1, TP params);

	public TR visit(Write write, TP params);

	public TR visit(Return return1, TP params);

	public TR visit(Program program, TP params);

	

	
}
