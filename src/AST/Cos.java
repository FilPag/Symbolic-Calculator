package calculator.ast;

/**
 * Cosin node with one child
 * Read the README file on how to setup and run the program
 * @author Georgios Davakos, Sebastian Fällman
 * @since 18 Nov 2019
 */
public class Cos extends Unary {
    private SymbolicExpression argument;
    
    /**
     * default constructor for Cos
     * @param argument the value to operate upon
     */
    public Cos(SymbolicExpression argument){
	super("Cos", argument);
	this.argument = argument;
    }

    @Override
    public String getName(){
	return "cos";
    }
    
    /**
     * checks if argument is a constant
     * @return true if the argument inside cosin is a constant otherwise false
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
