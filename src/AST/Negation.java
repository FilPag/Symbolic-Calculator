package calculator.ast;

public class Negation extends Unary {
    private SymbolicExpression argument;

    /**
     * default constructor for Negation
     * @param argument the value to operate upon
     */
    public Negation(SymbolicExpression argument){
	super("Negation", argument);
	this.argument = argument;
    }

    @Override
    public String getName(){
	return "-";
    }

    @Override
    public String toString(){
	return super.toString();
    }

    @Override
    public int getPriority(){
	return 6;
    } 

    @Override
    public SymbolicExpression accept(Visitor v) {
	return v.visit(this);
    }
}
