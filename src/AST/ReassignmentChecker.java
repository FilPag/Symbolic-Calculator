package calculator.ast;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A visitor that checks if a variable is declared twice in a scope
 */
public class ReassignmentChecker implements Visitor{
  private Stack<ArrayList<String>> stack = new Stack<ArrayList<String>>();

  public ReassignmentChecker(){
    stack.push(new ArrayList<String>());
  }

  public SymbolicExpression check(SymbolicExpression topLevel){
    SymbolicExpression result = topLevel.accept(this);
    stack.peek().clear();
    return result;
  }

  public SymbolicExpression visit(Addition a){
    a.lhs.accept(this);
    a.rhs.accept(this);
    return a;
  }

  public SymbolicExpression visit(Subtraction a){
    a.lhs.accept(this);
    a.rhs.accept(this);
    return a;
  }

  public SymbolicExpression visit(Multiplication a){
    a.lhs.accept(this);
    a.rhs.accept(this);
    return a;
  }

  public SymbolicExpression visit(Division a){
    a.lhs.accept(this);
    a.rhs.accept(this);
    return a;
  }

  public SymbolicExpression visit(Sin a){
    a.getArg().accept(this);
    return a;
  }

  public SymbolicExpression visit(Cos a){
    a.getArg().accept(this);
    return a;
  }

  public SymbolicExpression visit(Exp a){
    a.getArg().accept(this);
    return a;
  }

  public SymbolicExpression visit(Log a){
    a.getArg().accept(this);
    return a;
  }

  public SymbolicExpression visit(Negation a){
    a.getArg().accept(this);
    return a;
  }

  public SymbolicExpression visit(Variable a){
    return a;
  }

  public SymbolicExpression visit(Clear a){
    stack.peek().clear();
    return a;
  }

  public SymbolicExpression visit(Quit a){
    return a;
  }

  public SymbolicExpression visit(Vars a){
    return a;
  }

  public SymbolicExpression visit(Constant a){
    return a;
  }

  public SymbolicExpression visit(NamedConstant a){
    return a;
  }
  public SymbolicExpression visit(Assignment a) {
    a.lhs.accept(this);

    if(stack.peek().contains(a.rhs.toString())){
      System.out.println("ArrayList contains: " + stack.peek().subList(0, stack.peek().size()));
      throw new IllegalExpressionException("Trying to assign variable more than once in the same expression: " + a.rhs);
    }
    else{
      stack.peek().add(a.rhs.toString());
    }
    return a;
  }

  public SymbolicExpression visit(Scope a){
    stack.push(new ArrayList<String>());
    a.getExp().accept(this);
    stack.pop();
    return a;
  }

  public SymbolicExpression visit(Conditional a){
    return a;
  }

  public SymbolicExpression visit(Condition a){
    return a;

  }  

  public SymbolicExpression visit(Bool a){
    return a;
  }

  public SymbolicExpression visit(FunctionCall a){
    return a;
  }

  public SymbolicExpression visit(FunctionDeclaration a){
    return a;
  }

}
