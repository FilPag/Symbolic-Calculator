package calculator.parser;

import java.util.Scanner;
import calculator.parser.*;
import calculator.ast.*;
import java.io.IOException;

public class Calculator {
	private static int fullyEvaluated;
	private static int evaluated;
	private static int expressions;
	private static boolean running = true;
	static final EvaluationVisitor evaluator = new EvaluationVisitor();

	private static void printVars(){
		if(evaluator.getVars().size() < 1){
			System.out.println("No Variables declared");
		}else{
			System.out.println(evaluator.getVars());
		}
	}

	private static void evaluateCommand(SymbolicExpression cmd){
		if(cmd == Quit.instance()){
			System.out.println("Fully evaluated: " + fullyEvaluated);
			System.out.println("Evaluations: " + evaluated);
			System.out.println("Expressions entered: " + expressions);
			running = false;
		}else if(cmd == Vars.instance()){
			printVars();
		}else if(cmd ==Clear.instance()){
			evaluator.getVars().clear();
		}else{
			System.out.println("Invalid Command");
		} 
	}

	public static void main(String[] args) {
		System.out.println("Welcome to the parser!");
		Scanner sc = new Scanner(System.in);
		final CalculatorParser cp = new CalculatorParser();
		final ReassignmentChecker reaCheck = new ReassignmentChecker();
		final NamedConstantChecker checker = new NamedConstantChecker();

		while(running) {

			if(!cp.functionMode()){
				System.out.print("Please enter an expression:\n");
			}

			String input = "";
			if(sc.hasNextLine()){
				input = sc.nextLine();
			}
			else {
				input = "quit";
			}
			try{
				SymbolicExpression result = cp.setInput(input);
				expressions += 1;

				if (result != null) {
					checker.check(result);
					reaCheck.check(result);

					if(result.isCommand()){
						evaluateCommand(result);
					}else{
						evaluated += 1;
						result = evaluator.evaluate(result);
						System.out.println(result);
						if(result.isConstant())
							fullyEvaluated += 1;
					}
				}
			} catch(IOException e) {
				System.out.println("ERROR : IOException");
			} catch(SyntaxErrorException e) {
				System.out.println("Syntax error: " + e.getMessage());
			} catch(IllegalExpressionException e) {
				System.out.println("Syntax error: " + e.getMessage());
			} catch(StackOverflowError e) {
				System.out.println("ERROR : StackOverFlow");
			}
		}

	}
}
