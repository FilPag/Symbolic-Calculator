package calculator.ast;

public class Bool extends Atom{
    private boolean bool;

    public Bool(boolean bool){
	this.bool = bool;
    }

    @Override
    public String toString(){
	return "" + this.bool;
    }

    @Override
    public boolean getBool(){
	return this.bool;
    }

    @Override
    public SymbolicExpression accept(Visitor v){
	return v.visit(this);
    }

    @Override
    public boolean equals(Object other) {
	if (other instanceof Bool) {
	    return this.equals((Bool) other);
	}else{
	    return false;
	}
    }

    public boolean equals(Bool other) {
	/// access a private field of other!
	return bool == other.getBool();
    }
}
