package calculator.ast;

public class Variable extends Atom implements Comparable<Variable>{
	private String identifier;

	public Variable(String identifier){
		this.identifier = identifier;
	}

	public int compareTo(Variable other){
		return identifier.compareTo(other.getIdentifier());
	}

	public String getIdentifier(){
		return this.identifier;
	}

	@Override
	public String toString(){
		return this.identifier;
	}

	@Override
	public boolean equals(Object other){
		if(other instanceof Variable)
			return this.equals((Variable) other);
		else
			return false;
	}

	public boolean equals(Variable other){
		return this.identifier.equals(other.identifier);
	}

	@Override
	public int hashCode(){
		return identifier.hashCode();
	}

	@Override
	public SymbolicExpression accept(Visitor v) {
		return v.visit(this);
	}
}
