package calculator.ast;

public abstract class SymbolicExpression {

  public boolean isConstant(){
    return false;
  }

    /**
     * 
     */
  public String getName(){
    throw new RuntimeException("getName() called on expression with no operator");
  }

    /**
     * Get current SymbolicExpressions priority
     * @return priority value of current object
     */
  public int getPriority(){
    return 10;
  }

    /**
     * Gets value of current SymbolicExpression
     * @throw RuntimeException if current object has no value
     */
  public double getValue(){
    throw new RuntimeException("getValue() called on expression with no value");
  }

  @Override
  public boolean equals(Object other){
    return false;
  }

  /**
   * Checks if current SymbolicExpression is a Command
   * @return true if it is a command else false
   */
  public boolean isCommand(){
    return false;
  }

        
  /**
   * Checks if current SymbolicExpression is a a Bool
   * @return true if it is a Bool else false
   */
  public boolean getBool(){
    return false;
  }

  public abstract SymbolicExpression accept(Visitor v);
}
