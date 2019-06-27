import parser.*;
import semantic.IdentificationVisitor;
import semantic.LValueVisitor;
import semantic.TypeCheckingVisitor;

import org.antlr.v4.runtime.*;

import ast.Program;
import codeGeneration.AdressVisitor;
import codeGeneration.CG;
import codeGeneration.ExecutionVisitor;
import codeGeneration.OffsetVisitor;
import codeGeneration.ValueVisitor;
import error.ErrorHandler;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;

public class Main {

	public static void main(String... args) throws Exception {
		if (args.length < 1) {
			System.err.println("Please, pass me the input file.");
			return;
		}

		// create a lexer that feeds off of input CharStream
		CharStream input = CharStreams.fromFileName(args[0]);
		CmmLexer lexer = new CmmLexer(input);

		// create a parser that feeds off the tokens buffer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CmmParser parser = new CmmParser(tokens);
		Program ast = parser.program().ast;

		LValueVisitor visitor1 = new LValueVisitor();
		IdentificationVisitor visitor2 = new IdentificationVisitor();
		TypeCheckingVisitor visitor3 = new TypeCheckingVisitor();
		visitor1.visit(ast, null);
		visitor2.visit(ast, null);
		visitor3.visit(ast, null);

		if (!ErrorHandler.getInstance().isAnyError()) {

			CG cg = new CG(args[0]);
			OffsetVisitor offsetVisitor = new OffsetVisitor();
			offsetVisitor.visit(ast, null);

			ValueVisitor valueVisitor = new ValueVisitor(cg);
			AdressVisitor adressVisitor = new AdressVisitor(cg);
			ExecutionVisitor executionVisitor = new ExecutionVisitor(cg, adressVisitor, valueVisitor);

			executionVisitor.visit(ast, null);

			ErrorHandler.getInstance().showErrors(System.out);
			if (!ErrorHandler.getInstance().isAnyError()) {
				// * The AST is shown
				IntrospectorModel model = new IntrospectorModel("Program", ast);
				new IntrospectorTree("Introspector", model);
			}else {
				ErrorHandler.getInstance().showErrors(System.out);
			}

		} else {

			ErrorHandler.getInstance().showErrors(System.out);
		}

	}
}
