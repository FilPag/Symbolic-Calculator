package calculator.ast;

public abstract class Unary extends SymbolicExpression {
  private SymbolicExpression argument;

  public Unary(String name, SymbolicExpression node){
    this.argument = node;
  }

  public SymbolicExpression getArg(){
    return argument;
  }

  @Override
  public String toString(){
    return this.getName() + "(" + this.argument.toString() + ")";
  }

  @Override
  public boolean isConstant(){
    return this.argument.isConstant();
  }

  @Override
  public boolean equals(Object other){
    if(other instanceof Unary)
      return this.equals((Unary) other);
    else
      return false;
  }

  public boolean equals(Unary other){
    return this.getName().equals(other.getName()) && this.argument.equals(other.argument);
  }

  @Override
  public double getValue(){
    return this.argument.getValue();
  }
}
