package calculator.ast;

/**
 * Addition node with two children
 * Read the README file on how to setup and run the program
 * @author Georgios Davakos, Sebastian Fällman
 * @since 18 Nov 2019
 */
public class Addition extends Binary {

  /**
   * default constructor for Addition
   * @param lhs child on the left hand side 
   * @param rhs child on the right hand side 
   */
  public Addition(SymbolicExpression lhs, SymbolicExpression rhs){
    super("+", lhs, rhs);
  }

  /**
   * gets the result for the addition of the lhs and rhs
   * @return the value after the addition
   */
  @Override
  public double getValue(){
    return this.lhs.getValue() + this.rhs.getValue();
  }

  @Override
  public String toString(){
    return super.toString();
  }

  @Override
  public int getPriority(){
    return 2;
  }

  /**
   * checks if the lhs and rhs are constants
   * @return true of both sides are constants otherwise false
   */
  @Override
  public boolean isConstant(){
    return this.rhs.isConstant() && this.lhs.isConstant();
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Addition) {
      return this.equals((Addition) other);
    } else {
      return false;
    }
  }

  public boolean equals(Addition other) {
    /// access a private field of other!
    return this.lhs.equals(other.lhs) && this.rhs.equals(other.rhs);
  }

  @Override
  public SymbolicExpression accept(Visitor v) {
    return v.visit(this);
  }
}
