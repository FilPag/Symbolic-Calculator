package calculator.ast;

public class Conditional extends Binary{
    private SymbolicExpression condition;
    
    //Holy shit this is ugly
    public Conditional(SymbolicExpression condition, Scope lhs, Scope rhs){
	super("conditionals", lhs, rhs);
	this.condition = condition;
    }

    @Override
    public String getName(){
	return "";
    }

    public SymbolicExpression getCond(){
	return this.condition;
    }
    
    @Override
    public SymbolicExpression accept(Visitor v) {
	return v.visit(this);
    }
}
