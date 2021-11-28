package calculator.ast;

import java.util.ArrayList;

/**
 * Creates a new FunctionDecleration object that represents the definition of a function
 * @param name name of the function declared
 * @param sequence a sequence of operation(s)
 * @param param list of functions parameters
 */
public class FunctionDeclaration extends SymbolicExpression{
	private String name;
	private Sequence sequence;
	private ArrayList<String> param;

	public FunctionDeclaration(String name, ArrayList<String> param){
		this.name = name;
		this.param = param;
		this.sequence = new Sequence();
	}

	@Override
	public String toString(){
		return "Function: " + this.name + " with " + this.param.size() + " parameter(s)";
	}

	@Override
	public String getName(){
		return name;
	}

	/**
	 * Method to get sequence
	 * @return this sequence
	 */
	public Sequence getSequence(){
		return this.sequence;
	}

	/**
	 * Get number operations to the function
	 * @return number of parameters
	 */
	public int noExpressions(){
		return sequence.size();
	}

	/**
	 * Get a number of a given index, valid indexes [0-(n-1)]
	 * @param index the index of the parameter in list of parameters
	 * @return the parameter at the given index
	 */
	public String getParam(int index){
		return this.param.get(index);
	}

	/**
	 * Get a sequence of a given index, valid indexes [0-(n-1)]
	 * @param index the index of the sequence to get
	 * @return the sequence of the given index
	 */
	public SymbolicExpression getExp(int i){
		return sequence.get(i);	
	}

	/**
	 * Get number of parameters
	 * @return quantity of parameters
	 */
	public int noArgs(){
		return this.param.size();
	}

        @Override
	public SymbolicExpression accept(Visitor v){
		return v.visit(this);
	}
}
