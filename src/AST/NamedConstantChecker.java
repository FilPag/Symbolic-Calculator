package calculator.ast;

/**
 * Visitor that check if a variable name in an assignment is valid
 */
public class NamedConstantChecker implements Visitor{

	public SymbolicExpression check(SymbolicExpression topLevel){
		return topLevel.accept(this);
	}

	public SymbolicExpression visit(Addition a){
		a.lhs.accept(this);
		a.rhs.accept(this);
		return a;
	}

	public SymbolicExpression visit(Subtraction a){
		a.lhs.accept(this);
		a.rhs.accept(this);
		return a;
	}

	public SymbolicExpression visit(Multiplication a){
		a.lhs.accept(this);
		a.rhs.accept(this);
		return a;
	}

	public SymbolicExpression visit(Division a){
		a.lhs.accept(this);
		a.rhs.accept(this);
		return a;
	}

	public SymbolicExpression visit(Sin a){
		a.getArg().accept(this);
		return a;
	}

	public SymbolicExpression visit(Cos a){
		a.getArg().accept(this);
		return a;
	}

	public SymbolicExpression visit(Exp a){
		a.getArg().accept(this);
		return a;
	}

	public SymbolicExpression visit(Log a){
		a.getArg().accept(this);
		return a;
	}

	public SymbolicExpression visit(Negation a){
		a.getArg().accept(this);
		return a;
	}

	public SymbolicExpression visit(Variable a){
		return a;
	}

	public SymbolicExpression visit(Clear a){
		return a;
	}

	public SymbolicExpression visit(Quit a){
		return a;
	}

	public SymbolicExpression visit(Vars a){
		return a;
	}

	public SymbolicExpression visit(Constant a){
		return a;
	}

	public SymbolicExpression visit(NamedConstant a){
		return a;
	}

	public SymbolicExpression visit(Scope a){
		return a;
	}

	public SymbolicExpression visit(Assignment a) {
		a.rhs.accept(this);
		String varName = a.rhs.toString().toLowerCase();
		if(NamedConstant.namedConstants.containsKey(varName)) {
			throw new IllegalExpressionException("Not allowed to reassign '" + varName + "' try another variable");
		}
		if(NamedConstant.keyWords.contains(a.rhs.toString().toLowerCase())) {
			throw new IllegalExpressionException("Not allowed to reassign '" + varName + "' try another variable");
		}
		return a;
	}

	public SymbolicExpression visit(Conditional a){
		return a;
	}

	public SymbolicExpression visit(Condition a){
		return a;
	}

	public SymbolicExpression visit(Bool a){
		return a;
	}

	public SymbolicExpression visit(FunctionCall a){
		return a;
	}

	public SymbolicExpression visit(FunctionDeclaration a){
		return a;
	}

}
