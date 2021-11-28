import calculator.parser.*;
import calculator.ast.*;
import java.util.Scanner;
import java.io.StreamTokenizer;
import java.io.IOException;
import java.io.StringReader;

public class TestParser {

    private static boolean equivalent(double expected, double result) {
	return (expected == result);
    }

    
  public static void runTest() throws IOException {
	Environment vars = new Environment();
	CalculatorParser cp = new CalculatorParser("", vars);
	String input_1 = new String("1.0+2.0");
	String input_2 = new String("1.0-2.0");
	String input_3 = new String("1.0*2.0");
	String input_4 = new String("1.0/2.0");
	String input_5 = new String("-1.0");
	String input_6 = new String("exp 1.0");
	String input_7 = new String("log 10.0");
	String input_8 = new String("sin 3.14");
	String input_9 = new String("cos 3.14");
	String input_10 = new String("2.0+3.0*4.0/5.0");
	String input_11 = new String("2.0+x");

	CalculatorParser result_1 = new CalculatorParser(input_1, vars);
	CalculatorParser result_2 = new CalculatorParser(input_2,vars);
	CalculatorParser result_3 = new CalculatorParser(input_3,vars);
	CalculatorParser result_4 = new CalculatorParser(input_4,vars);
	CalculatorParser result_5 = new CalculatorParser(input_5,vars);
	CalculatorParser result_6 = new CalculatorParser(input_6,vars);
	CalculatorParser result_7 = new CalculatorParser(input_7,vars);
	CalculatorParser result_8 = new CalculatorParser(input_8,vars);
	CalculatorParser result_9 = new CalculatorParser(input_9,vars);
	CalculatorParser result_10 = new CalculatorParser(input_10,vars);
	CalculatorParser result_11 = new CalculatorParser(input_11,vars);
	
	String[] inputs = new String[]{input_1, input_2, input_3, input_4, input_5, input_6, input_7, input_8, input_9, input_10, input_11};
	//SymbolicExpression[] results = {result_1.statement(), result_2.statement(), result_3.statement(), result_4.statement(), result_5.statement(), result_6.statement(), result_7.statement(), result_8.statement(), result_9.statement(), result_10.statement(), result_11.statement()};
        int test_count = 11;
	for (int i = 0; i < test_count; ++i) {
	    System.out.println("Test " + i + "\ninput: " + inputs[i] + "\nparser returns: " + cp.statement(inputs[i]));
	    System.out.println("----------------");
	}
    }

    public static void main(String[] args) {
	    
	try {
	    runTest();
	} catch(IOException e) {
	    System.err.println("IO Exception!");
	}	
    }

    
}

