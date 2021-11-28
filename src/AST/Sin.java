package calculator.ast;

/**
 * Sinus node with one child
 * Read the README file on how to setup and run the program
 * @author Georgios Davakos, Sebastian Fällman
 * @since 18 Nov 2019
 */
public class Sin extends Unary {
    private SymbolicExpression argument;

    /**
     * default constructor for Sin
     * @param argument the value to operate upon
     */
    public Sin(SymbolicExpression argument){
	super("Sin", argument);
	this.argument = argument;
    }

    @Override
    public String getName(){
	return "sin";
    }
    
    @Override
    public double getValue(){
	return this.argument.getValue();
    }
    
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
