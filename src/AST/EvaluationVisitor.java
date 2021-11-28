package calculator.ast;

import java.util.ArrayList;
import java.util.HashMap;
import calculator.parser.SyntaxErrorException;

/**
 * A visitor that evaluates an expression
 */

public class EvaluationVisitor implements Visitor{
  private Environment vars = null;
  private ArrayList<Environment> stack = null;
  private HashMap<String, FunctionDeclaration> knownFunctions;

  public EvaluationVisitor(){
		vars = new Environment();
    this.stack = new ArrayList<Environment>();
    this.knownFunctions = new HashMap<String, FunctionDeclaration>();
		stack.add(0, vars);
		vars.put(new Variable("ans"), new Constant(0));

  }

	/**
	 * Returns the current Environment of variables
	 */
	public Environment getVars(){
		return stack.get(0);
	}

	/**
	 * Evaluates an expression
	 * @param topLevel The expression to be evaluated
	 * @return The evaluated expression
	 */
    public SymbolicExpression evaluate(SymbolicExpression topLevel){
    SymbolicExpression result = topLevel.accept(this);
		vars.put(new Variable("ans"), result);
		return result;
  }

  public SymbolicExpression visit(Constant n){
    return n;
  }

  public SymbolicExpression visit(NamedConstant n){
    return n.value.accept(this);
  }

  private SymbolicExpression checkStack(Variable e){

    for(Environment curr : stack){
      if(curr.containsKey(e)){
        return curr.get(e);
      } 
    }
    return null;
  }

  public SymbolicExpression visit(Variable e){

    SymbolicExpression result = checkStack(e);

    if(result != null) {
      return result;
    } else {
      return new Variable(e.getIdentifier());
    }
  }

  public SymbolicExpression visit(Addition n){
    SymbolicExpression lhs = n.lhs.accept(this);
    SymbolicExpression rhs = n.rhs.accept(this);

    if(lhs.isConstant() && rhs.isConstant()){
      return new Constant(lhs.getValue() + rhs.getValue());
    }else{
      return new Addition(lhs, rhs);
    }
  }

  public SymbolicExpression visit(Subtraction n){
    SymbolicExpression lhs = n.lhs.accept(this);
    SymbolicExpression rhs = n.rhs.accept(this);

    if(lhs.isConstant() && rhs.isConstant())
      return new Constant(lhs.getValue() - rhs.getValue());
    else
      return new Subtraction(lhs, rhs);

  }

  public SymbolicExpression visit(Multiplication n){
    SymbolicExpression lhs = n.lhs.accept(this);
    SymbolicExpression rhs = n.rhs.accept(this);

    if(lhs.isConstant() && rhs.isConstant())
      return new Constant(lhs.getValue() * rhs.getValue());
    else
      return new Multiplication(lhs, rhs);
  }

  public SymbolicExpression visit(Division n) {
    SymbolicExpression lhs = n.lhs.accept(this);
    SymbolicExpression rhs = n.rhs.accept(this);

    if(lhs.isConstant() && rhs.isConstant())
      return new Constant(lhs.getValue() / rhs.getValue());
    else
      return new Division(lhs, rhs);
  }

  public SymbolicExpression visit(Assignment n){
    SymbolicExpression lhs = n.lhs.accept(this);
    SymbolicExpression rhs = n.rhs;
    stack.get(0).put((Variable)rhs, lhs);
    if(lhs.isConstant())
      return new Constant(lhs.getValue());
    else
      return new Assignment(lhs, rhs);
  }


  public SymbolicExpression visit(Cos n){
    SymbolicExpression argument = n.getArg().accept(this);

    if(argument.isConstant()) {
      return new Constant(Math.cos(argument.getValue()));
    } else {
      return new Cos(argument);
    }
  }

  public SymbolicExpression visit(Sin n) {
    SymbolicExpression argument = n.getArg().accept(this);

    if(argument.isConstant()) {
      return new Constant(Math.sin(argument.getValue()));
    } else {
      return new Sin(argument);
    }
  }

  public SymbolicExpression visit(Exp n){
    SymbolicExpression argument = n.getArg().accept(this);

    if(argument.isConstant())
      return new Constant(Math.exp(argument.getValue()));
    else
      return new Exp(argument);
  }

  public SymbolicExpression visit(Log n){
    SymbolicExpression argument = n.getArg().accept(this);

    if(argument.isConstant())
      return new Constant(Math.log(argument.getValue()));
    else
      return new Log(argument);
  }

  public SymbolicExpression visit(Negation n){
    SymbolicExpression argument = n.getArg().accept(this);

    if(argument.isConstant())
      return new Constant(-(argument.getValue()));
    else
      return new Negation(argument);
  }

  public SymbolicExpression visit(Quit n){
    System.out.println("Bye");
    System.exit(0);
    return null;
  }

  public SymbolicExpression visit(Clear n){
    stack.get(0).clear();
    return null;
  }

  public SymbolicExpression visit(Vars n){
    return n;
  }

  public SymbolicExpression visit(Scope n){
    Environment var = new Environment();
    stack.add(0, var);
    SymbolicExpression exp = n.getExp().accept(this);
    stack.remove(0);
    return exp;
  }

  public SymbolicExpression visit(Condition n){
    SymbolicExpression lhs = n.lhs.accept(this);
    SymbolicExpression rhs = n.rhs.accept(this);

    if(!lhs.isConstant() || !rhs.isConstant()){
      throw new SyntaxErrorException (lhs + " or " + rhs + " is not a constant");
    }
    switch(n.getOp()){
      case ">":
        return n.greaterThen(lhs, rhs);
      case "<":
        return n.greaterThen(rhs, lhs);
      case ">=":
        return n.greaterThenOrEqual(lhs, rhs);
      case "<=":
        return n.greaterThenOrEqual(rhs, lhs);
      case "==":
        return new Bool(lhs.equals(rhs));
      default :
        throw new SyntaxErrorException ("Invalid condition: " + n.getOp());
    }
  }


  public SymbolicExpression visit(Conditional n){
    SymbolicExpression bool = n.getCond().accept(this);
    if(bool.getBool())
      return n.lhs.accept(this);
    else
      return n.rhs.accept(this);
  }

  public SymbolicExpression visit(Bool n){
    return n;
  }

  public SymbolicExpression visit(FunctionDeclaration n){
    knownFunctions.put(n.getName(), n);
    return n;
  }

  public SymbolicExpression visit(FunctionCall n){
		
    if(!knownFunctions.containsKey(n.getId())){
      throw new SyntaxErrorException ("Undefined function");
    }

    FunctionDeclaration function = knownFunctions.get(n.getId());

    if(n.noArgs() != function.noArgs()){
      throw new SyntaxErrorException("Called: " + n.getId() + 
          " with wrong amount of arguments!\nGot: " + n.noArgs() 
          + "\nExpected: " + function.noArgs());
    }

    Environment env = new Environment();
    SymbolicExpression result = new Constant(0);
    for(int i = 0; i < n.noArgs(); ++i){

      if(n.getArg(i).isConstant()){
        env.put(new Variable(function.getParam(i)), n.getArg(i));
      }else{

        Variable varName = (Variable)n.getArg(i);
        env.put(new Variable(function.getParam(i)), checkStack(varName));
      }
    }

    stack.add(0, env);

    for(int i = 0; i < function.noExpressions(); ++i){
      result = function.getExp(i).accept(this);
    }
    stack.remove(0);	
    return result;
  }    
}


