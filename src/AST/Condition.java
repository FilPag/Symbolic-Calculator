package calculator.ast;

public class Condition extends Binary{
    private String operation;
    
    public Condition(String operation, SymbolicExpression lhs, SymbolicExpression rhs){		  
	super("condition", lhs, rhs);
	this.operation = operation;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
	return v.visit(this);
    }

    public String getOp(){
	return this.operation;
    }

    public SymbolicExpression greaterThen(SymbolicExpression lhs, SymbolicExpression rhs){
	return new Bool(lhs.getValue() > rhs.getValue());
    }

    public SymbolicExpression greaterThenOrEqual(SymbolicExpression lhs, SymbolicExpression rhs){
	return new Bool(lhs.getValue() >= rhs.getValue());
    }
}
