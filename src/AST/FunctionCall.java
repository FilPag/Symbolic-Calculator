package calculator.ast;

import java.util.ArrayList;
/**
 * Creates a new FunctionCall object that represents the action of calling a function
 * @param identifier name of the function to call
 * @param args arguments to the function
 */
public class FunctionCall extends SymbolicExpression{
  private String identifier;
  private ArrayList<SymbolicExpression> args;

  public FunctionCall(String identifier, ArrayList<SymbolicExpression> args){
    this.identifier = identifier;
    this.args = args;
  }

    /**
     * Returns the element at the specified position in this list
     * @param index - index of the element to return
     * @return the element at the specified position in list
     * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >= size())
     */
  public SymbolicExpression getArg(int index){
    return this.args.get(index);
  }

    public String toString(){
	return "Function: " + identifier;
    }
    /**
     * Returns the number of elements in this list
     * @return the number of elements in this list
     */
  public int noArgs(){
      return this.args.size();
  }

    /**
     * Returns this identifier
     * @return this identifier
     */
  public String getId(){
    return this.identifier;
  }

  @Override
  public SymbolicExpression accept(Visitor v){
    return v.visit(this);
  }
}
