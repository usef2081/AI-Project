package code;

public class GenericSearch {

	private String [] Operators ;
	private String initialState;
	private String [] StateSpace;
	private String isGoal ;
	private String pathCost;
	
	
	public String[] getOperators() {
		return Operators;
	}


	public void setOperators(String[] operators) {
		Operators = operators;
	}


	public String getInitialState() {
		return initialState;
	}


	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}


	public String[] getStateSpace() {
		return StateSpace;
	}


	public void setStateSpace(String[] stateSpace) {
		StateSpace = stateSpace;
	}


	public String getIsGoal() {
		return isGoal;
	}


	public void setIsGoal(String isGoal) {
		this.isGoal = isGoal;
	}


	public String getPathCost() {
		return pathCost;
	}


	public void setPathCost(String pathCost) {
		this.pathCost = pathCost;
	}


	public GenericSearch(String [] Operators,String initialState, String isGoal, String pathCost ) {	
		
			this.Operators = Operators;
			this.initialState = initialState;
			this.isGoal = isGoal;
			this.pathCost = pathCost;
		
		
	}
	
}
