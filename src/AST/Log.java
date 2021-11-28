package calculator.ast;

/**
 * Log node with one child
 * Read the README file on how to setup and run the program
 * @author Georgies Davakos, Sebastian Fällman
 * @since 18 Nov 2019
 */
public class Log extends Unary {
    private SymbolicExpression argument;

    public Log(SymbolicExpression argument){
	super("Log", argument);
	this.argument = argument;
    }

    @Override
    public String getName(){
	return "log";
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

