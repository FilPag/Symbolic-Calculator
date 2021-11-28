package calculator.parser;
import calculator.ast.*;
import calculator.parser.*;

import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;

/** 
 * Represents a parser for the calculator.
 * It is used to parse strings into SymbolicExpressions
 **/
public class CalculatorParser {
  private  StreamTokenizer st;
  private boolean functionMode;
  private FunctionDeclaration functionBuffer;

  public CalculatorParser() {
    functionMode = false;
  }

	/**
	 * Checks if the parser is currently adding expressions to a function
	 * @return true if the parser is in 'functionMode'
	 */
	public boolean functionMode(){
		return functionMode;
	}

	/**
	 * Updates the current string in the parser and parses it.
	 * @param str the string to be parsed
	 * @return the parsed expression
	 */
  public SymbolicExpression setInput(String str) throws IOException{
    this.st = new StreamTokenizer(new StringReader(str));
    this.st.ordinaryChar('-');
    this.st.ordinaryChar('*');
    this.st.ordinaryChar('/');
    this.st.eolIsSignificant(true);
    return parse();
  }

  /**
   * Parses the currentlut set input string
   * @return The parsed SymbolicExpression
   **/
  private SymbolicExpression parse() throws IOException{

    this.st.nextToken(); 
    if(this.st.ttype == this.st.TT_EOF){
      return null;
    }

    if(functionMode){
      this.st.pushBack();
      return parseFunction();
    }

    if (isCommand()) {
      String token = this.st.sval;
      return parseCommand(token);
    }else {
      this.st.pushBack();
      return startFunction();
    }
  }

  private SymbolicExpression parseFunction() throws IOException{
    if(this.st.nextToken() == this.st.TT_WORD && this.st.sval.toLowerCase().equals("end")){
      functionMode = false;
      return functionBuffer;
    } 

    this.st.pushBack();
    functionBuffer.getSequence().append(assignment());
    return null;
  }

  private SymbolicExpression startFunction() throws IOException{
    if(this.st.nextToken() != this.st.TT_WORD || !this.st.sval.toLowerCase().equals("function")){
      this.st.pushBack();
      return assignment();
    }

    if(this.st.nextToken() != this.st.TT_WORD){
      throw new SyntaxErrorException("Invalid function identifier");
    }

    String funcName = this.st.sval;

    if(this.st.nextToken() != '('){
      throw new SyntaxErrorException("Expected \'(\'");
    }

    ArrayList<String> arguments = new ArrayList<String>();

    if(this.st.nextToken() != this.st.TT_WORD){
      throw new SyntaxErrorException("Invalid argument name");
    }

    arguments.add(this.st.sval);

    while(this.st.nextToken() == ','){

      if(this.st.nextToken() != this.st.TT_WORD){
        throw new SyntaxErrorException("Invalid argument name");
      }
      arguments.add(this.st.sval);
    }

    functionBuffer = new FunctionDeclaration(funcName, arguments);
    functionMode = true;
    return null;
  }

  private SymbolicExpression conditional() throws IOException{	
    if (this.st.nextToken() != this.st.TT_WORD || !this.st.sval.toLowerCase().equals("if")){
      this.st.pushBack();
      return expression();
    }

    SymbolicExpression condition = condition();
    Scope case1 = new Scope(assignment());

    if(this.st.ttype != this.st.TT_WORD || !this.st.sval.toLowerCase().equals("else")){
      throw new SyntaxErrorException("Expected \'else\'");
    }

    this.st.nextToken();
    Scope case2 = new Scope(assignment());

    return new Conditional(condition, case1, case2);
  }

  private SymbolicExpression condition() throws IOException{
    SymbolicExpression result = expression();

    this.st.nextToken();

    String operator = "";
    if(this.st.ttype != this.st.TT_NUMBER){
      operator = operator + (char)this.st.ttype;
      if (this.st.nextToken() == '='){
        operator = operator + '=';
      }else{
        this.st.pushBack();
      }
    }else{
      operator = "" + (char)this.st.ttype;
    }
    return new Condition(operator, result, expression());
  }

  /* Parses all assignments in the string
   *@return the parsed assignment
   */
  private SymbolicExpression assignment() throws IOException{
    SymbolicExpression result = conditional();

    while(this.st.nextToken() == '='){
      if(this.st.nextToken() != this.st.TT_WORD){
        throw new SyntaxErrorException("Assignments should look like this: \'EXPRESSION\' = \'VARIABLE_NAME\'");
      }
      Variable var = new Variable(this.st.sval);
      result = new Assignment(result, var);
    }
    this.st.pushBack();
    return result;
  }

  private SymbolicExpression expression() throws IOException {

    SymbolicExpression result = term();

    this.st.nextToken();

    while(st.ttype == '+' || st.ttype == '-'){
      int operation = st.ttype;

      if(operation == '+'){
        result = new Addition(result, term());

      }else{
        result = new Subtraction(result, term());
      }
      this.st.nextToken();
    }
    this.st.pushBack();
    return result;
  }

  private SymbolicExpression term() throws IOException {
    SymbolicExpression result = parentheses();

    this.st.nextToken();

    while(st.ttype == '*' || st.ttype == '/'){
      int operation = st.ttype;

      if(operation == '*'){
        result = new Multiplication (result, parentheses());
      }else{
        result = new Division(result, parentheses());
      }
      this.st.nextToken();
    }

    this.st.pushBack();
    return result;
  }

  private SymbolicExpression parentheses() throws IOException {
    SymbolicExpression result = null;

    this.st.nextToken();
    if (this.st.ttype == '(') {
      result = assignment();

      if (this.st.ttype != ')') {
        throw new SyntaxErrorException("Expected ')'");
      }

      this.st.nextToken();
    }else if(this.st.ttype == '{'){
      result = new Scope(assignment());

      if(this.st.ttype != '}'){
        throw new SyntaxErrorException("Expected '}' ");
      }
      this.st.nextToken();
    }else {
      this.st.pushBack();
      result = numberOrUnary();
    }     
    return result;
  }


  private SymbolicExpression numberOrUnary() throws IOException {

    if (this.st.ttype != this.st.TT_NUMBER) {
      this.st.pushBack();
      return unary();
    }
    this.st.pushBack();
    return number();
  }

  private SymbolicExpression unary() throws IOException{

    this.st.nextToken();
    if(this.st.ttype != this.st.TT_WORD){
      if(this.st.ttype == '-'){
        return new Negation (parentheses());
      }else{
	  throw new SyntaxErrorException("Unexpected: " +  (char)this.st.ttype);
      }
    }

    String token = this.st.sval.toLowerCase();

    switch(token){
      case "exp":
        return new  Exp(parentheses());

      case("sin"):
        return new Sin (parentheses());

      case("cos"):
        return new Cos (parentheses());

      case("log"): 
        return new Log (parentheses());

      default:
        return variable(token);	
    }
  }

  private SymbolicExpression callFunction(String id) throws IOException{
    ArrayList <SymbolicExpression> args = new ArrayList<SymbolicExpression>();
    if(this.st.nextToken() == ')'){
      return new FunctionCall(id, args);
    }

    this.st.pushBack();
    args.add(conditional());

    while(this.st.nextToken() == ','){
      args.add(conditional());
    }

    if(this.st.ttype != ')'){
      throw new SyntaxErrorException("Expected \')\'");
    }

    return new FunctionCall(id, args);
  }

  private SymbolicExpression variable(String str) throws IOException{
    switch(str){
      case "pi":
        return new NamedConstant("pi");

      case "e":
        return new NamedConstant("e");

      case "answer":
        return new NamedConstant("answer");

      default:
        String identifier = str;
        if(this.st.nextToken() == '('){
          return callFunction(identifier); 
        }
        this.st.pushBack();
        return new Variable(identifier);
    } 
  }

  private SymbolicExpression number() throws IOException {
    if (this.st.nextToken() == StreamTokenizer.TT_NUMBER) {
      SymbolicExpression result = new Constant(this.st.nval);
      return result;   
    }else{
      throw new SyntaxErrorException("Unexpected: " + quote((char) this.st.ttype));
    }
  }

  private boolean isCommand() {
    if(this.st.ttype != StreamTokenizer.TT_WORD){
      return false;
    }
    return (this.st.sval.toLowerCase().equals("quit")
        || this.st.sval.toLowerCase().equals("vars") 
        || this.st.sval.toLowerCase().equals("clear"));
  }

  private Command parseCommand(String command) throws SyntaxErrorException{
    String cmd = command.toLowerCase();
    switch(cmd){
      case "quit":
        return Quit.instance();

      case "vars":
        return Vars.instance();

      case "clear":
        return Clear.instance();

      default:
        throw new SyntaxErrorException("Invalid Command: " + quote(command));
    }
  }

  private static String quote(char s){
    return "\'" + s + "\'";
  }

  private static String quote(String s){
    return '\'' + s + '\'';
  }

  private static String mergeArgs(String[] args){
    String result = "";	
    for(String s : args){
      result = result + s;
    }
    return result;
  }
}
