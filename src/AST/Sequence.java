package calculator.ast;

import java.util.ArrayList;

public class Sequence{
  private ArrayList<SymbolicExpression> sequence;
    
  public Sequence(){
    this.sequence = new ArrayList<SymbolicExpression>();
  }

  public ArrayList<SymbolicExpression> getExpressions(){
    return sequence;
  }

	public int size(){
		return sequence.size();
	}

	public SymbolicExpression get(int i){
		return sequence.get(i);
	}

  public void append(SymbolicExpression n){
    sequence.add(n);
  }
}
