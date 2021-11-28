package tests;
import calculator.parser.*;
import calculator.ast.*;
import org.junit.Test;
import junit.framework.TestCase;
import java.util.ArrayList;
import org.junit.runner.notification.Failure;
import org.junit.runner.*;
import static org.junit.Assert.*;



public class TestAST extends TestCase{

    private EvaluationVisitor evaluator = new EvaluationVisitor();
    private Constant c1 = new Constant(5);
    private Constant c2 = new Constant(2);
    private Constant c3 = new Constant(2);
    private Constant c4 = new Constant(3);
    private Constant c5 = new Constant(0);
    private Constant c6 = new Constant(Math.PI/2);
    private Constant c7 = new Constant(1);
    private Variable v = new Variable("x");
    private Variable v1 = new Variable("x");
    private Addition a = new Addition(c1, v);
    private Addition a1 = new Addition(c4, c2);
    private Multiplication m = new Multiplication(a, c2);
    private Multiplication m1 = new Multiplication(c1, c2);
    private Division d = new Division(c1, c2);
    private Division d1 = new Division(a, c2);
    private Subtraction s = new Subtraction(c1, c2);
    private Subtraction s1 = new Subtraction(a, c2);
    private Exp exp = new Exp(c3);
    private Exp exp1 = new Exp(m1);
    private Exp exp2 = new Exp(s1);
    private Cos cos = new Cos(c5);
    private Sin sin = new Sin(c5);
    private Cos cos1 = new Cos(c6);
    private Sin sin1 = new Sin(c6);
    private Negation n = new Negation(new Constant (2));
    private Log l = new Log(new Constant (10));
    private Assignment x = new Assignment(new Constant(8), v);
    private Addition aboi = new Addition(new Division(new Constant(6), new Constant(2)), new Multiplication(new Constant(4), new Constant(2)));

    @Test
    public void testGetValue(){
	assertEquals(c1.getValue(), c1.getValue());
	assertEquals(c2.getValue(), c3.getValue());
	assertNotEquals(c2.getValue(), c5.getValue());
    }

    @Test
    public void testIsConstant(){
	assertTrue(c1.isConstant());
	assertTrue(a1.isConstant());
	assertFalse(a.isConstant());
	assertFalse(v.isConstant());
	assertFalse(exp2.isConstant());
    }

    @Test
    public void testGetName(){
	assertEquals("+", a.getName());
	assertEquals("-", s.getName());
	assertEquals("/", d.getName());
	assertEquals("*", m.getName());
	assertEquals("exp", exp.getName());
	assertEquals("-", n.getName());
	assertEquals("log", l.getName());
	assertEquals("sin", sin.getName());
	assertEquals("cos", cos.getName());
    }

    @Test
    public void testIsCommand(){
	assertTrue(Vars.instance().isCommand());
	assertTrue(Quit.instance().isCommand());
	assertTrue(Clear.instance().isCommand());
	assertFalse(sin.isCommand());
	assertFalse(s.isCommand());
	assertFalse(c1.isCommand());
    }

    @Test
    public void testPriority(){
	assertEquals(2, a1.getPriority());
	assertEquals(10, c1.getPriority());
	assertEquals(6, exp.getPriority());
	assertEquals(6, sin.getPriority());
	assertEquals(6, cos.getPriority());
	assertEquals(2, s.getPriority());
	assertEquals(6, n.getPriority());
	assertEquals(6, l.getPriority());
	assertEquals(6, m.getPriority());
	assertEquals(6, d.getPriority());
	assertNotEquals(10, exp.getPriority());
	assertNotEquals(7, l.getPriority());
    }

    @Test
    public void testToString(){
	assertEquals("5.0", c1.toString());
	assertEquals("2.0", c2.toString());
	assertEquals("5.0 + x", a.toString());
	assertEquals("3.0 + 2.0", a1.toString());
	assertEquals("5.0 * 2.0", m1.toString());
	assertEquals("5.0 / 2.0", d.toString());
	assertEquals("5.0 - 2.0", s.toString());
	assertEquals("exp(2.0)", exp.toString());
	assertEquals("(5.0 + x) *2.0", m.toString());
	assertEquals("(5.0 + x) /2.0", d1.toString());
	assertEquals("5.0 + x - 2.0", s1.toString());
	assertEquals("exp(5.0 * 2.0)", exp1.toString());
	assertEquals("exp(5.0 + x - 2.0)", exp2.toString());
	assertEquals("cos(" + Math.PI/2 + ")", cos1.toString());
    }

    @Test
    public void testEquals(){
	assertTrue(v.equals(v1));
	assertTrue(c2.equals(c3)); //TODO: Mer tester här
    }

    @Test
    public void testCondition(){
	Environment vars = new Environment();
	assertEquals(new Bool(true), evaluator.evaluate(new Condition("<", new Constant(1), new Constant(15))));
		     assertEquals(new Bool(true), evaluator.evaluate(new Condition("==", new Constant(15), new Constant(15))));
		     assertEquals(new Bool(true), evaluator.evaluate(new Condition(">", new Constant(19), new Constant(15))));
		     assertEquals(new Bool(true), evaluator.evaluate(new Condition("<=", new Constant(1), new Constant(15))));
		     assertEquals(new Bool(true), evaluator.evaluate(new Condition(">=", new Constant(17), new Constant(15))));
		     assertEquals(new Bool(true), evaluator.evaluate(new Condition("<=", new Constant(15), new Constant(15))));
		     assertEquals(new Bool(true), evaluator.evaluate(new Condition(">=", new Constant(1), new Constant(1))));
		     assertEquals(new Constant(8), evaluator.evaluate(new Conditional(new Condition("<", new Constant(3), new Constant(5)), new Scope(new Constant(8)), new Scope(new Constant(5)))));
	assertEquals(new Constant(5), evaluator.evaluate(new Conditional(new Condition(">", new Constant(3), new Constant(5)), new Scope(new Constant(8)), new Scope(new Constant(5)))));

    }


    @Test
    public void testEval(){
	Environment vars = new Environment();
	assertEquals(evaluator.evaluate(c3), evaluator.evaluate(new Constant(2)));
	assertEquals(evaluator.evaluate(new Addition(new Constant (5), new Variable("x"))), evaluator.evaluate(a));
	assertEquals(evaluator.evaluate(c4), evaluator.evaluate(s));
	assertEquals(evaluator.evaluate(c1), evaluator.evaluate(a1));
	assertEquals(evaluator.evaluate(c5), evaluator.evaluate(sin));
	assertEquals(evaluator.evaluate(c7), evaluator.evaluate(cos));
	assertEquals(evaluator.evaluate(c7), evaluator.evaluate(sin1));
	assertEquals(evaluator.evaluate(new Constant(Math.cos(c6.getValue()))), evaluator.evaluate(cos1));
	assertEquals(evaluator.evaluate(new Constant(2.5)), evaluator.evaluate(d));
	assertEquals(evaluator.evaluate(c7), evaluator.evaluate(new Exp(c5)));
	assertEquals(evaluator.evaluate(c7), evaluator.evaluate(new Log(new Constant(Math.E))));
	assertEquals(evaluator.evaluate(c3), evaluator.evaluate(new Exp(new Log(c3))));
	assertEquals(evaluator.evaluate(new Constant(21)), evaluator.evaluate(new Multiplication(new Addition(c1,c2), new Addition(c2,c7))));
	assertEquals(evaluator.evaluate(new Variable("x")), evaluator.evaluate(v));
	assertEquals(evaluator.evaluate(new Constant(8)), evaluator.evaluate(x));
	assertEquals(evaluator.evaluate(new Constant(26)), evaluator.evaluate(m));
	assertEquals(evaluator.evaluate(new Sin(new Constant(10))), evaluator.evaluate(new Sin(new Multiplication(new Constant(2),new Constant(5)))));
	assertEquals(evaluator.evaluate(new Constant(11)), evaluator.evaluate(aboi));
	assertNotEquals(evaluator.evaluate(new Constant(10)), evaluator.evaluate(aboi));
    }

    @Test
    public void testScope(){
	Environment vars = new Environment();
	assertEquals(evaluator.evaluate(new Constant(10)), evaluator.evaluate(new Addition(new Scope(new Assignment(new Constant(1), new Variable("x"))), new Scope(new Assignment(new Constant(9), new Variable("x"))))));
	assertEquals(0, vars.size());
    }


    @Test
    public void testConditional(){
	Environment vars = new Environment();
	Condition less = new Condition("<", new Constant(1), new Constant(2));
	Condition lessOnes = new Condition("<", new Constant(1), new Constant(1));
	Condition lessOrEqual = new Condition("<=", new Constant(1), new Constant(1));
	Condition greater = new Condition(">", new Constant(-1), new Constant(0));
	Condition eq = new Condition("==", new Constant(5), new Constant(5));
	Condition eq1 = new Condition("==", new Constant(3), new Constant(5));
	Scope scope1 = new Scope(new Constant(1));
	Scope scope2 = new Scope(new Constant(2));
	Conditional lessThen = new Conditional(less, scope1, scope2);
	Conditional less1 = new Conditional(lessOnes, scope1, scope2);
	Conditional lessThenOrEqual = new Conditional(lessOrEqual, scope1, scope2);
	Conditional lessThenOrEqual1 = new Conditional(lessOrEqual, scope1, scope2);
	Conditional great = new Conditional(greater, scope1, scope2);
	Conditional equals = new Conditional(eq, scope1, scope2);
	Conditional equals1 = new Conditional(eq1, scope1, scope2);
	assertEquals(evaluator.evaluate(new Constant(1)), evaluator.evaluate(lessThen));
	assertEquals(evaluator.evaluate(new Constant(1)), evaluator.evaluate(lessThenOrEqual));
	assertEquals(evaluator.evaluate(new Constant(2)), evaluator.evaluate(less1));
	assertEquals(evaluator.evaluate(new Constant(2)), evaluator.evaluate(great));
	assertEquals(evaluator.evaluate(new Constant(1)), evaluator.evaluate(equals));
	assertEquals(evaluator.evaluate(new Constant(2)), evaluator.evaluate(equals1));
    }

    @Test
    public void testFunctions(){
	ArrayList<String> param1 = new ArrayList<String>();
	param1.add("n");
	FunctionDeclaration func = new FunctionDeclaration("fun", param1);
	
    }

    @Test
    public void testParser(){
	CalculatorParser cp = new CalculatorParser();
	try{
	    SymbolicExpression result = cp.setInput(a.toString());
	    assertTrue(result.equals(a));
	    result = cp.setInput(c1.toString());
	    assertTrue(result.equals(c1));
	    result = cp.setInput(m1.toString());
	    assertTrue(result.equals(m1));
	    result = cp.setInput(d.toString());
	    assertTrue(result.equals(d));
	    result = cp.setInput(exp.toString());
	    assertTrue(result.equals(exp));
	    result = cp.setInput(s1.toString());
	    assertTrue(result.equals(s1));
	    result = cp.setInput(d1.toString());
	    assertTrue(result.equals(d1));
	    result = cp.setInput(aboi.toString());
	    assertTrue(result.equals(aboi));
	    result = cp.setInput(cos1.toString());
	    assertTrue(result.equals(cos1));
	    assertFalse(result.equals(cos));
	    assertFalse(result.equals(sin));
	    result = cp.setInput(n.toString());
	    assertTrue(result.equals(n));
	    result = cp.setInput("(1 = x) + {(2 + x = x) + {3 + x = x}}");
	    assertEquals(new Constant(10), evaluator.evaluate(result));
	    result = cp.setInput("function fun(n)");
	    result = cp.setInput("n - 1 = m");
	    result = cp.setInput("if n > 1 {fun(m)*n} else {1}");
	    result = cp.setInput("end");
	    evaluator.evaluate(result);
	    result = cp.setInput("fun(5)");
	    assertEquals(new Constant(120), evaluator.evaluate(result));

	} catch(Exception e) {
	    System.err.println("Exception: " + e);
	}
    }



    public static void main(String[] args) {
	Result result = JUnitCore.runClasses(TestAST.class);

	for(Failure failure : result.getFailures()){
	    System.out.println(failure.toString());
	}

	System.out.println(result.wasSuccessful());	
    }
}
