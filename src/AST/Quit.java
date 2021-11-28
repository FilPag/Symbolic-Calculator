package calculator.ast;

public class Quit extends Command{
  private static final Quit theInstance = new Quit();

  private Quit() {
  }

  public static Quit instance(){
    return theInstance;
  }

  @Override
  public boolean isCommand(){
    return true;
  }

  @Override
  public SymbolicExpression accept(Visitor v) {
    return v.visit(this);
  }
}
