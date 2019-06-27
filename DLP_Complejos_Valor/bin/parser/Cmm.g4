grammar Cmm;	

@header{
	import ast.*;
	import ast.definitions.*;
	import ast.expressions.*;
	import ast.expressions.binary.*;
	import ast.expressions.literals.*;
	import ast.expressions.unary.*;
	
	import ast.statements.*;
	
	import ast.types.*;
	
	
	
}
program returns [Program ast]:  definitionList  
				{$ast = new Program(0,0,$definitionList.ast);}
       ;
definitionList returns [List<Definition> ast= new ArrayList<Definition>();]: 
		(funcDefinition {$ast.add($funcDefinition.ast);} | multipleVarDef{$ast.addAll($multipleVarDef.ast);} )*			
		;

//DEFINITIONS

multipleVarDef returns [List<VariableDefinition> ast=new ArrayList<VariableDefinition>();] :
				t=type i1=ID {$ast.add(new VariableDefinition($i1.getLine(),$i1.getCharPositionInLine()+1,$i1.text,$t.ast));}
				(n=',' i2=ID  {$ast.add(new VariableDefinition($n.getLine(),$n.getCharPositionInLine()+1,$i2.text,$t.ast));})* ';' 

			;

//type ID (n=','ID)* ';'  
//varDefinition returns [VariableDefinition ast]: type n=ID ';'  
//			{$ast=new VariableDefinition($n.getLine(),$n.getCharPositionInLine()+1,$n.text,$type.ast);}
//		; 
		
funcDefinition returns [FunctionDefinition ast]: type  ID n='('arguments')' functionBody   
			{$ast=new FunctionDefinition($n.getLine(),$n.getCharPositionInLine()+1
										,$ID.text
										,new FunctionType($n.getLine(),$n.getCharPositionInLine()+1,$type.ast,$arguments.ast)
										,$functionBody.ast);}
		;
arguments returns [List<VariableDefinition> ast=new ArrayList<VariableDefinition>();]: 
	(      b=type n=ID  {$ast.add(new VariableDefinition($n.getLine(),$n.getCharPositionInLine()+1,$n.text,$b.ast));} 
	(',' b=type i=ID  {$ast.add(new VariableDefinition($i.getLine(),$i.getCharPositionInLine()+1,$i.text,$b.ast));})*)?
	;
functionBody returns [List<Statement> ast=new ArrayList<Statement>();]:
		 '{' (multipleVarDef {$multipleVarDef.ast.forEach(e-> $ast.add((Statement)e));})* 
			(statement {$ast.add($statement.ast);})* '}' 
			;   
			
			
//STATEMENTS--------------------------------------

statement returns [Statement ast]: 
		   n='while' '(' expression ')' block	
		                                 {$ast=new While($n.getLine(),$n.getCharPositionInLine()+1,$expression.ast,$block.ast);}
		|  n='if' '(' expression ')' b1=block 
			                             {$ast=new If($n.getLine(),$n.getCharPositionInLine()+1,$expression.ast,$b1.ast);} 
			('else' b2=block             {((If)$ast).setElse($b2.ast);})?
		|  n='read'  l=expressionList    {$ast=new Read($n.getLine(),$n.getCharPositionInLine()+1,$l.ast);}			
		| n='write' l=expressionList     {$ast=new Write($n.getLine(),$n.getCharPositionInLine()+1,$l.ast);}
		| n='return' e=expression ';'	 {$ast=new Return($n.getLine(),$n.getCharPositionInLine()+1,$e.ast);}	 
		| funcInvocation ';'             {$ast=$funcInvocation.ast;}
		| <assoc=right> e1=expression n='=' e2=expression ';'  {$ast=new Assignment($n.getLine(),$n.getCharPositionInLine()+1,$e1.ast,$e2.ast);}
		;
expressionList returns [List<Expression> ast = new ArrayList<Expression>();]:
		   e1=expression {$ast.add($e1.ast);}(',' e2=expression {$ast.add($e2.ast);})* ';' 
		;
block returns[List<Statement> ast=new ArrayList<Statement>();]:     statement {$ast.add($statement.ast);}
  		| '{' (statement {$ast.add($statement.ast);} )* '}' 
  		;
  
  		
//TYPES---------------------------------------

type returns [Type ast]: builtInType           {$ast= $builtInType.ast;} 
	| n='void'                                 {$ast=VoidType.getInstance();}
	| n='struct' structBody                    {$ast=new StructType($n.getLine(),$n.getCharPositionInLine()+1,$structBody.ast);}				
	|  t=type '[' e=INT_CONSTANT']'				   
	{
		if($t.ast instanceof ArrayType){
			ArrayType newArrayType= new ArrayType($t.start.getLine(),$t.start.getCharPositionInLine()+1);
			newArrayType.setSize(Integer.parseInt($e.text));

			((ArrayType)$t.ast).changeInnerType(newArrayType);
			$ast=$t.ast;
		}else{
			ArrayType aux=new ArrayType($t.start.getLine(),$t.start.getCharPositionInLine()+1);
			((ArrayType)aux).setInnerType($t.ast);
			((ArrayType)aux).setSize(Integer.parseInt($e.text));
			$ast=aux;
		}
		

	}
	;					


structBody returns [List<RecordType> ast=new ArrayList<RecordType>();]: 
			'{' (recordType {$ast.add($recordType.ast);})* '}'
		;
recordType returns [RecordType ast]: t=type ID ';' {$ast=new RecordType($t.start.getLine(),$t.start.getCharPositionInLine()+1,$ID.text,$t.ast);} 
		;

builtInType returns [Type ast]:  n='double' {$ast=RealType.getInstance();}
			| n='int'                       {$ast=IntType.getInstance();}
	        | n='char'                      {$ast=CharType.getInstance();}
			;
								


//EXPRESIONS-------------------------------------

expression returns[Expression ast]:  
			'(' expression ')' {$ast=$expression.ast;}					
			| e1=expression n='['e2=expression']'                     {$ast = new ArrayAccess           ($n.getLine(),$n.getCharPositionInLine()+1,$e1.ast,$e2.ast);}
			| n='-' e=expression                                      {$ast = new UnaryMinus            ($n.getLine(),$n.getCharPositionInLine()+1,$e.ast);} 
			| e=expression n='.' i=ID                                 {$ast = new FieldAccess           ($n.getLine(),$n.getCharPositionInLine()+1,$i.text,$e.ast);}
			| n='(' type ')' e=expression                             {$ast = new CastExpression        ($n.getLine(),$n.getCharPositionInLine()+1,$type.ast,$e.ast);}
			| e1=expression n=('*'|'/'|'%') e2=expression             {$ast = new ArithmeticExpression  ($n.getLine(),$n.getCharPositionInLine()+1,$e1.ast,$n.text,$e2.ast);}
			| e1=expression n=('+'|'-') e2=expression	              {$ast = new ArithmeticExpression  ($n.getLine(),$n.getCharPositionInLine()+1,$e1.ast,$n.text,$e2.ast);}
			| e1=expression n=('>' |'>=' |'<' |'<='|'!=' |'==') e2=expression {$ast=new ComparisonExpression($n.getLine(),$n.getCharPositionInLine()+1,$e1.ast,$n.text,$e2.ast);}
			| e1=expression n=('&&'|'||') e2=expression               {$ast = new LogicalExpression     ($n.getLine(),$n.getCharPositionInLine()+1,$e1.ast,$n.text,$e2.ast);}
			| n='!' e=expression                                      {$ast = new UnaryNegation         ($n.getLine(),$n.getCharPositionInLine()+1,$e.ast);}
		
			| funcInvocation {$ast=$funcInvocation.ast;}


			| n=ID             {$ast=new Variable($n.getLine(),$n.getCharPositionInLine()+1,$n.text);}
			| n=INT_CONSTANT   {$ast=new IntLit($n.getLine(),$n.getCharPositionInLine()+1,Integer.parseInt($n.text));}
			| n=REAL_CONSTANT  {$ast=new RealLit($n.getLine(),$n.getCharPositionInLine()+1,Double.parseDouble(($n.text)));}
			| n=CHAR_CONSTANT  {$ast=new CharLit($n.getLine(),$n.getCharPositionInLine()+1,LexerHelper.lexemeToChar($n.text));}
			;
		
		
//STMNT - EXPRESSION - STMNT - EXPRESSION ------

funcInvocation returns [FunctionInvocation ast] : 
			n=ID '('p=funcParams ')'  {$ast = new FunctionInvocation($n.getLine(),$n.getCharPositionInLine()+1,$p.ast,new Variable($n.getLine(),$n.getCharPositionInLine()+1,$n.text));}
		;
	
funcParams returns [List<Expression> ast=new ArrayList<Expression>();]: 
			(e1=expression {$ast.add($e1.ast);} (','e2=expression { $ast.add($e2.ast);})*)? 
	;








//LEXER-------------------------------------------------------------------



INT_CONSTANT: '0'|[1-9][0-9]* 
            ;
            
CHAR_CONSTANT: '\'' .*? '\''
			|  '\' INT_CONSTANT \''
            ;
REAL_CONSTANT:  REAL_BASIC
			 |  REAL_BASIC[eE]'-'?INT_CONSTANT
			 |  INT_CONSTANT[eE]INT_CONSTANT
            ;
ID : (LETTER|'_')(LETTER|[0-9]|'_')*
		;
		
fragment
ONE_LINE_COMMENT:'//'.*?'\r'?('\n'|EOF)
				;
				
fragment
MULTI_LINE_COMMENT:'/*' .*? '*/'
				;
COMMENT: (WHITE_SPACES|ONE_LINE_COMMENT|MULTI_LINE_COMMENT)->skip
		;
fragment
WHITE_SPACES:('\t'
		|'\n'|'\r'|' ')
			;

fragment
LETTER:[a-zA-Z]
	  ;
fragment
REAL_BASIC: INT_CONSTANT?  '.' [0-9]*
		  ;