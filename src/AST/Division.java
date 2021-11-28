package calculator.ast;

/**
 * Division node with two children
 * Read the README file on how to setup and run the program
 * @author Georgios Davakos, Sebastian Fällman
 * @since 18 Nov 2019
 */
public class Division extends Binary{

	/**
	 * default constructor for Division
	 * @param lhs child on the left hand side 
	 * @param rhs child on the right hand side 
	 */
	public Division(SymbolicExpression lhs, SymbolicExpression rhs){
		super("/", lhs, rhs);
	}
	/**
	 * gets the result for the division of the lhs and rhs
	 * @return the value after the division
	 */
	@Override
	public double getValue(){
		return this.lhs.getValue() / this.rhs.getValue();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Division) {
			return this.equals((Division) other);
		} else {
			return false;
		}
	}

	public boolean equals(Division other) {
		/// access a private field of other!
		return this.lhs.equals(other.lhs) && this.rhs.equals(other.rhs);
	}


	/**
	 * checks if the lhs and rhs are constants
	 * @return true of both sides are constants otherwise false
	 */
	@Override
	public boolean isConstant(){
		return this.lhs.isConstant() && this.rhs.isConstant();
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
