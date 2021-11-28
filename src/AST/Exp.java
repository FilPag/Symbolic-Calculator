package calculator.ast;


/**
 * Turns the parsed Exp into a node in the abstract syntax tree
 * Read the README file on how to setup and run the program
 * @author Georgios Davakos, Sebastian Fällman
 * @since 18 Nov 2019
 */
public class Exp extends Unary {
    private SymbolicExpression argument;

    /**
     * default constructor for Exp
     * @param argument the value to operate upon
     */
    public Exp(SymbolicExpression argument){
	super("Exp", argument);
	this.argument = argument;
    }

    @Override
    public String getName(){
	return "exp";
    }

    /**
     * checks if argument is a constant
     * @return true if argument is a constant otherwise false
     */
    @Override
    public boolean isConstant(){
	return this.argument.isConstant();
    }
    
    @Override
    public int getPriority(){
	return 6;
    }

    @Override
    public String toString(){
	return super.toString();
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
	return v.visit(this);
    }
}
