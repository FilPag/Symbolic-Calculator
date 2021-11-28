package calculator.ast;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Represents all named constants or predefined expressions in the calculator.
 * Such as pi and euler's number.
 * @param namedConstants All named constants
 * @param keyWords All pre-defined keywords in the calculator 
 **/
public class NamedConstant extends Atom{
	public static final HashMap<String, SymbolicExpression> namedConstants = new HashMap<String, SymbolicExpression>();
	SymbolicExpression value;

	public static final ArrayList<String>	keyWords  = new ArrayList<String>();

	static{
		namedConstants.put("ans", new Constant(0));
		namedConstants.put("answer", new Constant(42));
		namedConstants.put("e", new Constant(Math.E));
		namedConstants.put("pi", new Constant(Math.PI));

		keyWords.add("sin");
		keyWords.add("cos");
		keyWords.add("log");
		keyWords.add("exp");
		keyWords.add("if");
		keyWords.add("else");
		keyWords.add("function");
		keyWords.add("quit");
		keyWords.add("vars");
		keyWords.add("clear");
		keyWords.add("end");
	}

	public NamedConstant(String str){
		this.value= namedConstants.get(str);
	}

	/** Updates the ans constant
	 * @param The new value of ans
	 **/
	public static void updateAns(SymbolicExpression lastResult){
		namedConstants.put("ans", lastResult);
	}

	public SymbolicExpression getConstant(){
		return value;
	}

	@Override
	public SymbolicExpression accept(Visitor v){
		return v.visit(this);
	}

	@Override
	public String toString(){
		return value.toString();	
	}
}
