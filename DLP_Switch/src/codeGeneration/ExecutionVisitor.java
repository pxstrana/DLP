package codeGeneration;

import java.util.List;

import ast.Program;
import ast.definitions.Definition;
import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
import ast.expressions.Expression;
import ast.statements.Assignment;
import ast.statements.FunctionInvocation;
import ast.statements.If;
import ast.statements.Read;
import ast.statements.Return;
import ast.statements.Statement;
import ast.statements.Switch;
import ast.statements.While;
import ast.statements.Write;
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
	        this.cg.comment(w.toString());
	        
	        for (Expression e : w.getExpressions()) {
	            if (e instanceof VariableDefinition) {
	                e.accept(this.addressVisitor, null);
	                this.cg.load(e.getType());
	            } else {
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
			
			a.getAssigned().accept(addressVisitor, null);
			a.getValue().accept(valueVisitor, null);
			cg.store(a.getAssigned().getType());
			
			
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
			cg.label(labelNumber);
			w.getCondition().accept(this.valueVisitor, null);
			cg.jz((labelNumber + 1));
			
			cg.comment("While body");
			w.getBody().forEach(st -> {
				if (!(st instanceof VariableDefinition)) {
					this.cg.line(st.getLine());
				}
				st.accept(this, params);
			});
			cg.jmp(labelNumber);
			cg.label((labelNumber + 1)); 
			
			
			cg.comment("End of while");
			return null;
		}
		
		@Override
		public Void visit(If ifElse, FunctionDefinition params) {
			
			cg.comment(ifElse.toString());
			int labelNumber = cg.getLabels(2);
			ifElse.getCondition().accept(this.valueVisitor, null);
			cg.jz(labelNumber);
			
			cg.comment("If Body");
			
			ifElse.getIfBody().forEach(st -> {
				if (!(st instanceof VariableDefinition)) {
					this.cg.line(st.getLine());
				}
				st.accept(this, params);
			});
			cg.jmp((labelNumber + 1));
			cg.label(labelNumber);
			
			cg.comment("Else Body");
			ifElse.getElseBody().forEach(st -> {
				if (!(st instanceof VariableDefinition)) {
					this.cg.line(st.getLine());
				}
				st.accept(this, params);
			});
			cg.label((labelNumber + 1)); 
			
			
			cg.comment("End of If");
			return null;
		}

		@Override
		public Void visit(Switch s, FunctionDefinition params) {
			
			
				cg.comment("Start Switch statement");
		        int caseCount = s.getCasesList().size();
		        int labelNumber= this.cg.getLabels(caseCount+1);
		        
		        for (int j = 0; j < s.getCasesList().size(); j++) {
		        	
					s.getCondition().accept(valueVisitor, null);
					s.getCasesList().get(j).getCondition().accept(valueVisitor, null);
					this.cg.sub(IntType.getInstance());
					this.cg.jz(j);
		        }
				for (int j = 0; j < s.getCasesList().size(); j++) {
					this.cg.label(j);								
					for (Statement st : s.getCasesList().get(j).getStatements()) {
						st.accept(this, params);
					}				
					this.cg.jmp(labelNumber+caseCount);
					
				}
		        
		        
		        this.cg.label(labelNumber+caseCount);				
		        
		        cg.comment("End Switch statement");
		        return null;
		    }
		}
		
		
		
		

