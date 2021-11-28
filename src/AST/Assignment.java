package calculator.ast;

/**
 * An assignment node with two children
 * Read the README file on how to setup and run the program
 * @author Georgios Davakos, Sebastian Fällman
 * @since 18 Nov 2019
 * @see Binary
 */
public class Assignment extends Binary{

	/**
	 * default constructor for Assignment class
	 * @param lhs child on the left hand side
	 * @param rhs child on the right hand side
	 */
	public Assignment(SymbolicExpression lhs, SymbolicExpression rhs){
		super("=", lhs, rhs);
	}

	@Override
	public int getPriority(){
		return 4;
	}

	/**
	 * gets the value of the expression from the left child
	 * @return the value of the left child
	 */
	@Override
	public double getValue(){
		return this.lhs.getValue();
	}

	@Override
	public SymbolicExpression accept(Visitor v) {
		return v.visit(this);
	}
}
