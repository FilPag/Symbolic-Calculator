package calculator.ast;

/**
 * Turns a parsed number into a Constant node in the abstract syntax tree
 * Read the README file on how to setup and run the program
 * @author Gerogios Davakos, Sebastian Fällman
 * @since 18 Nov 2019
 */
public class Constant extends Atom{
  private double value;

  /**
   * default constructor for a Constant
   * @param value the value to be converted to a Constant
   */
  public Constant(double value){
    this.value = value;
  }

  @Override
  public boolean isConstant(){
    return true;
  }

  @Override
  public double getValue(){
    return this.value;
  }

  @Override
  public String toString() {
    return String.valueOf(this.getValue());
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Constant) {
      return this.equals((Constant) other);
    } else {
      return false;
    }
  }

  public boolean equals(Constant other) {
    /// access a private field of other!
    return this.value == other.value;
  }


  @Override
  public SymbolicExpression accept(Visitor v) {
    return v.visit(this);
  }
}
