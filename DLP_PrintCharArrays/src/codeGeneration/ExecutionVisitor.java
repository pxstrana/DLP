package codeGeneration;

import java.util.List;

import ast.Program;
import ast.definitions.Definition;
import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
import ast.expressions.ArrayAccess;
import ast.expressions.Expression;
import ast.expressions.literals.IntLit;
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
import ast.types.VoidType;
import visitor.AbstractCGVisitor;




	public class ExecutionVisitor extends AbstractCGVisitor<Void, FunctionDefinition> {

		private AdressVisitor addressVisitor;
		private CG cg;
		private ValueVisitor valueVisitor;
		
		
		public ExecutionVisitor(CG cg,AdressVisitor a,ValueVisitor v) {
			this.cg = cg;
			valueVisitor = new ValueVisitor(cg);
			addressVisitor = new AdressVisitor(cg);
			valueVisitor.setAddressVisitor(addressVisitor);
			addressVisitor.setValueVisitor(valueVisitor);
			
		}

		private int getBytesLocals(List<Statement> sentencias) {
			int size = 0;
			for(Statement st : sentencias) {
				if(st instanceof Definition) {
					size += ((Definition) st).getType().numberOfBytes();
				}
			}	
			return size;
		}
		
		private int getBytesParams(List<VariableDefinition> list) {
			int size = 0;
			for(Definition st : list) {
					size +=  st.getType().numberOfBytes();

			}	
			return size;
		}
		
		@Override
		public Void visit(Program f, FunctionDefinition param) {			
			cg.source();
			cg.commentRaw("");
			
			for(Definition def : f.getDefinitions()) {
				if(def instanceof VariableDefinition)
					def.accept(this, param);
			}
			
			cg.call("main");
			cg.halt();
			
			
			for(Definition def : f.getDefinitions()) {
				if(def instanceof FunctionDefinition)
					def.accept(this, param);
			}
			cg.commentRaw("' * END of program");
			return null;
		}
		
		@Override
		public Void visit(Read r, FunctionDefinition param) {	
			cg.comment(r.toString());
			for (Expression e : r.getExpressions()) {
				e.accept(addressVisitor, null); //direccion donde guardar
				cg.in(e.getType());				//valor por input
				cg.store(e.getType());			//guardar
			}
			cg.comment("End of Read");
			return null;
		}
		@Override
	    public Void visit(Write w, FunctionDefinition params) {
	        this.cg.comment("Start of write");
	        
	        for (Expression e : w.getExpressions()) {
	            if (e instanceof VariableDefinition) {  	
	            	e.accept(this.addressVisitor, null);
	                this.cg.load(e.getType());	     
	                
	            }if(e.getType() instanceof ArrayType) {
	            	
	            	
	            	for (int i = 0; i < ((ArrayType)e.getType()).getSize(); i++) {
	            		IntLit index=new IntLit(e.getLine(), e.getColumn(), i);
	            		index.setType(IntType.getInstance());
						ArrayAccess aa=new ArrayAccess(e.getLine(), e.getColumn(),
								e,index );
						aa.setType(CharType.getInstance());
						aa.accept(addressVisitor, null);
						cg.load(CharType.getInstance());
						cg.out(CharType.getInstance());
						
						
					}
	            	
	            return null;}
	            else {
	                e.accept(this.valueVisitor, null);
	            }
	            this.cg.out(e.getType());
	        }
	        
	        cg.comment("End of Write");
	        return null;
	    }
		
		
		
		@Override
		public Void visit(Assignment a, FunctionDefinition param) {
			cg.comment(a.toString());
			
			if(a.getAssigned().getType() instanceof ArrayType && a.getValue().getType() instanceof ArrayType) {
				ArrayType al =(ArrayType) a.getAssigned().getType();
				ArrayType ar =(ArrayType) a.getValue().getType();
				
				for (int j = 0; j < al.getSize(); j++) {
					
				
				ArrayAccess left=new ArrayAccess(a.getLine(), a.getColumn(),
						a.getAssigned(), new IntLit(a.getLine(),a.getColumn(), j));
				left.setType(CharType.getInstance());
				left.getIndex().setType(IntType.getInstance());
				
				
				left.accept(addressVisitor, null);
				
				
				ArrayAccess right=new ArrayAccess(a.getLine(), a.getColumn(),
						a.getAssigned(), new IntLit(a.getLine(),a.getColumn(), j));
				right.setType(CharType.getInstance());
				right.getIndex().setType(IntType.getInstance());
				right.accept(valueVisitor, null);
			
				cg.store(al.getInnerType());
				
				}
	
				
				
				
				
				
			}
			else {
			
			a.getAssigned().accept(addressVisitor, null);
			a.getValue().accept(valueVisitor, null);
			cg.store(a.getAssigned().getType());
			}
			
			cg.comment("End of Assignment");
			
			return null;
		}

		
		
		@Override
		public Void visit(FunctionDefinition f, FunctionDefinition param) {
			
			cg.line(f.getLine());
			cg.commentRaw("");
			cg.functionlabel(f.getName());
			
			cg.enter(getBytesLocals(f.getBody()));
			FunctionType funcType = (FunctionType) f.getType(); 
			
			cg.commentRaw("	' * Parameters");
			for (VariableDefinition e : funcType.getParameters()) {
				cg.comment(e.getType().getName()+" "+e.getName()+" ( offset "+e.getOffset()+")");
			}
			cg.commentRaw("	' * Local variables");		
			for (Statement st : f.getBody()) {
				if (!(st instanceof VariableDefinition)) {
					this.cg.line(st.getLine());
				}
				st.accept(this, f);
			}
			
						
			
			if(funcType.getReturnType().numberOfBytes() == 0) {
				cg.ret(0,getBytesLocals(f.getBody()),
						getBytesParams(funcType.getParameters()));
			}
			
			cg.comment("End of FunctionDefinition: "+f.getName());
			return null;
		}
		
		@Override
		public Void visit(FunctionInvocation fi, FunctionDefinition params) {
			cg.comment(fi.toString() );
			
			fi.accept(this.valueVisitor, null);
			if(!(fi.getType() instanceof VoidType)) {
				cg.pop(fi.getType());
			}
			
			
			cg.comment("End of invocation: "+fi.getVariable().getName());
			return null;
		}
		
		
		@Override
		public Void visit(VariableDefinition varDef, FunctionDefinition params) {
			cg.comment(varDef.getType().getName()+" "+varDef.getName()+" (offset "+varDef.getOffset()+")");
			return null;

		}

		@Override
		public Void visit(Return return1, FunctionDefinition params) {
			cg.comment("Return");
			return1.getReturnValue().accept(valueVisitor, null);
			if (!(((FunctionType)params.getType()).getReturnType() instanceof VoidType)) {
			cg.ret(return1.getReturnValue().getType().numberOfBytes(),
					getBytesLocals(params.getBody()),
					getBytesParams(((FunctionType)params.getType()).getParameters()));		
			}
			return null;

		}
		//CONDITIONALS
		@Override
		public Void visit(While w, FunctionDefinition params) {
			
			cg.comment(w.toString());
			
			int labelNumber = cg.getLabels(2);
			cg.label(labelNumber+"");
			w.getCondition().accept(this.valueVisitor, null);
			cg.jz((labelNumber + 1)+"");
			
			cg.comment("While body");
			w.getBody().forEach(st -> {
				if (!(st instanceof VariableDefinition)) {
					this.cg.line(st.getLine());
				}
				st.accept(this, params);
			});
			cg.jmp(labelNumber+"");
			cg.label((labelNumber + 1)+""); 
			
			
			cg.comment("End of while");
			return null;
		}
		
		@Override
		public Void visit(If ifElse, FunctionDefinition params) {
			
			cg.comment(ifElse.toString());
			int labelNumber = cg.getLabels(2);
			ifElse.getCondition().accept(this.valueVisitor, null);
			cg.jz(labelNumber+"");
			
			cg.comment("If Body");
			
			ifElse.getIfBody().forEach(st -> {
				if (!(st instanceof VariableDefinition)) {
					this.cg.line(st.getLine());
				}
				st.accept(this, params);
			});
			cg.jmp((labelNumber + 1)+"");
			cg.label(labelNumber+"");
			
			cg.comment("Else Body");
			ifElse.getElseBody().forEach(st -> {
				if (!(st instanceof VariableDefinition)) {
					this.cg.line(st.getLine());
				}
				st.accept(this, params);
			});
			cg.label((labelNumber + 1)+""); 
			
			
			cg.comment("End of If");
			return null;
		}
		
}
