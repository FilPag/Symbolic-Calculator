package calculator.ast;

/**
 * Subtraction node with two children
 * Read the README file on how to setup and run the program
 * @author Georgios Davakos, Sebastian Fällman
 * @since 18 Nov 2019
 */
public class Subtraction extends Binary {

	/**
	 * default constructor for Subtraction
	 * @param lhs child on the left hand side 
	 * @param rhs child on the right hand side 
	 */
	public Subtraction(SymbolicExpression lhs, SymbolicExpression rhs){
		super("-", lhs, rhs);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Subtraction) {
			return this.equals((Subtraction) other);
		} else {
			return false;
		}
	}

	public boolean equals(Subtraction other) {
		/// access a private field of other!
		return this.lhs.equals(other.lhs) && this.rhs.equals(other.rhs);
	}

	@Override
	public String toString(){
		return super.toString();
	}

	/**
	 * gets the result for the subtraction of the lhs and rhs
	 * @return the value after the subtraction
	 */    
	@Override
	public double getValue(){
		return this.lhs.getValue() - this.rhs.getValue();
	}


	/**
	 * checks if the lhs and rhs are constants
	 * @return true of both sides are constants otherwise false
	 */
	@Override
	public boolean isConstant(){
		return this.rhs.isConstant() && this.lhs.isConstant();
	}

	@Override
	public int getPriority(){
		return 2;
	}


	@Override
	public SymbolicExpression accept(Visitor v) {
		return v.visit(this);
	}

}
