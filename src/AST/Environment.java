package calculator.ast;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * A hash table that contains all variables and the expressions/values they map to
 * Read the README file on how to setup and run the program
 * @author Georgios Davakos, Sebastian Fällman
 * @since 18 Nov 2019
 */
public class Environment extends HashMap<Variable, SymbolicExpression> {

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Variables:");
		TreeSet<Variable> vars = new TreeSet<>(this.keySet());

		for (Variable v : vars) {
			sb.append("\n");
			sb.append(v.getIdentifier());
			sb.append(" = ");
			sb.append(this.get(v));
		}
		return sb.toString();
	}
}
