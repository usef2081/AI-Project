package code;

public class Node {

	
	
	private String state;
	private Node parent;
	private String operator;
	private int depth;
	private int path_cost;
	private int heuristic;
	
	
	public Node(String state, Node parent, String operator, int depth, int path_cost) {
		
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.path_cost = path_cost;
		
		
		
	}
	
	
	public Node(String state, Node parent, String operator, int depth, int path_cost,int heuristic) {
		
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.path_cost = path_cost;
		this.heuristic = heuristic;
		
		
		
	}


	public int getHeuristic() {
		return heuristic;
	}


	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}


	public String toString() {
		return "Node [state=" + state + ", parent=" + parent + ", operator=" + operator + ", depth=" + depth
				+ ", path_cost=" + path_cost + "]";
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Node getParent() {
		return parent;
	}


	public void setParent(Node parent) {
		this.parent = parent;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public int getDepth() {
		return depth;
	}


	public void setDepth(int depth) {
		this.depth = depth;
	}


	public int getPath_cost() {
		return path_cost;
	}


	public void setPath_cost(int path_cost) {
		this.path_cost = path_cost;
	}
	
}
