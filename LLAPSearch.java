package code;

import java.util.*;




public class LLAPSearch extends GenericSearch{
	
	
	public static int initialHeuristic = 0;
	public static int initialProsperity;
	public static int initialFood;
	public static int initialMaterials;
	public static int initialEnergy;
	public static int unitPriceFood; 
	public static int unitPriceMaterials;
	public static int unitPriceEnergy;
	public static int amountRequestFood; 
	public static int delayRequestFood;
	public static int amountRequestMaterials; 
	public static int delayRequestMaterials;
	public static int amountRequestEnergy;
	public static int delayRequestEnergy;
	public static int priceBUILD1;
	public static int foodUseBUILD1;
	public static int materialsUseBUILD1; 
	public static int energyUseBUILD1;
	public static int prosperityBUILD1;
	public static int priceBUILD2;
	public static int foodUseBUILD2;
	public static int materialsUseBUILD2; 
	public static int energyUseBUILD2;
	public static int prosperityBUILD2;
	public static int budget = 100000;
	public static int money_spent = 0;
	public static String waiting_for;
	public static int remaining_delivery_time;
	public static int expandedNodes;
	public static Node initial_node;
	
	
	

	public LLAPSearch(String[] Operators, String initialState, String isGoal, String pathCost) {
		super(Operators, initialState, isGoal, pathCost);
		String [] actions = {"RequestFood" , "RequestMaterials", "RequestEnergy", "WAIT", "BUILD1", "BUILD2"};
		this.setOperators(actions);
		
		this.setIsGoal("Check if prosperity >= 100");
		this.setPathCost("Money spent for each action is the path cost");
		
		
		
	}
	
	
	public static String solve(String initial_State,String strategy,boolean visualize) {
		
		
		
		expandedNodes = 0;
		parseInitials(initial_State);
		
		
		if(strategy.equals("BF") || strategy.equals("DF") || strategy.equals("UC") || strategy.equals("ID")) {
		String initial_node_state = initialFood + ";" + initialMaterials + ";" + initialEnergy + ";" +  initialProsperity + ";" + money_spent + ";" + waiting_for + ";" + remaining_delivery_time;
		//state represented as Food;Materials;Energy;Prosperity;Money_spent;waiting_for;remaining_delivery_time
		
		initial_node = new Node(initial_node_state,null,null,0,0);
		//System.out.println(initial_node);
		
		}
		
		else {
			String initial_node_state = initialFood + ";" + initialMaterials + ";" + initialEnergy + ";" +  initialProsperity + ";" + money_spent + ";" + waiting_for + ";" + remaining_delivery_time +";" +initialHeuristic;
			//state represented as Food;Materials;Energy;Prosperity;Money_spent;waiting_for;remaining_delivery_time;heuristicvalue
			
			initial_node = new Node(initial_node_state,null,null,0,0,initialHeuristic);
			//System.out.println(initial_node);
			   
		}
		
		if(strategy.equals("BF")) {
			
			return solveBF(initial_node,visualize);
			
		}
		
		if(strategy.equals("DF")) {
			
			return solveDF(initial_node,visualize);
			
		}
		
		if(strategy.equals("UC")) {
			
			
			return solveUC(initial_node,visualize);
		}
		
		
		if(strategy.equals("ID")) {
			
			
			return solveID(initial_node,visualize);
		}
		
		
		if(strategy.substring(0, 2).equals("GR")) {
			
			int i = Integer.parseInt(strategy.charAt(2)+"");
			return solveGR(initial_node,visualize,i);
			
		}
		
		if(strategy.substring(0, 2).equals("AS")) {
			
			int i = Integer.parseInt(strategy.charAt(2)+"");
			return solveAS(initial_node,visualize,i);
			
		}
		
		
		
		
		return "";
	}
	
	
	
	public static boolean canRequestFood (String [] splitted_node) {
		
		
		int food = Integer.parseInt(splitted_node[0]);
		int materials = Integer.parseInt(splitted_node[1]);
		int energy = Integer.parseInt(splitted_node[2]);
		String waiting = splitted_node[5];
		
		if((food<50) && (food >0) && (materials>0) && (energy>0) && (waiting.equals("null") || waiting == null)) {
			
			return true;
			}
		
		return false;
		
	}
	
	
	
	public static boolean canRequestMaterials (String [] splitted_node) {
		
		int food = Integer.parseInt(splitted_node[0]);
		int materials = Integer.parseInt(splitted_node[1]);
		int energy = Integer.parseInt(splitted_node[2]);
		String waiting = splitted_node[5];
		if((materials<50) && (food >0) && (materials>0) && (energy>0) && (waiting.equals("null") || waiting == null))
			return true;
		return false;
		
		
		
	}
	
	
	public static boolean canRequestEnergy (String [] splitted_node) {
		
		
		int food = Integer.parseInt(splitted_node[0]);
		int materials = Integer.parseInt(splitted_node[1]);
		int energy = Integer.parseInt(splitted_node[2]);
		String waiting = splitted_node[5];
		if((energy<50) && (food >0) && (materials>0) && (energy>0) && (waiting.equals("null") || waiting == null))
			return true;
		return false;
		
		
		
	}
	
	
	public static boolean canWait (String [] splitted_node) {
		
		int food = Integer.parseInt(splitted_node[0]);
		int materials = Integer.parseInt(splitted_node[1]);
		int energy = Integer.parseInt(splitted_node[2]);
		String waiting_for = splitted_node[5];
		int remaining_time = Integer.parseInt(splitted_node[6]);
		
		
		if((food >0) && (materials>0) && (energy>0) && (waiting_for != null) && !(waiting_for.equals("null")) && (remaining_time>0)) {
			
			return true;
		}
		return false;
		
		
		
	}

	public static boolean canBuild1 (String [] splitted_node) {
		
		int food = Integer.parseInt(splitted_node[0]);
		int materials = Integer.parseInt(splitted_node[1]);
		int energy = Integer.parseInt(splitted_node[2]);
		
		
		if((food>= foodUseBUILD1) && (materials >= materialsUseBUILD1) && (energy>= energyUseBUILD1)) {
			
			return true;
		}
		return false;
		
		
	}
	
	
	public static boolean canBuild2 (String [] splitted_node) {
		
		int food = Integer.parseInt(splitted_node[0]);
		int materials = Integer.parseInt(splitted_node[1]);
		int energy = Integer.parseInt(splitted_node[2]);
		
		
		if((food>= foodUseBUILD2) && (materials >= materialsUseBUILD2) && (energy>= energyUseBUILD2)) {
			
			return true;
		}
		return false;
		
		
	}
	
	
	public static String solveAS (Node initial, boolean v, int i) {
		
		int BUILD1_fullprice = (foodUseBUILD1 * unitPriceFood) + (materialsUseBUILD1 * unitPriceMaterials) + (energyUseBUILD1 * unitPriceEnergy) + priceBUILD1;
		int BUILD2_fullprice = (foodUseBUILD2 * unitPriceFood) + (materialsUseBUILD2 * unitPriceMaterials) + (energyUseBUILD2 * unitPriceEnergy) + priceBUILD2;
		
		int min_build_price = Integer.min(BUILD1_fullprice,BUILD2_fullprice);
		int min_build_price_prosperity = 0;
		int initial_total_resources = initialFood + initialEnergy + initialMaterials;
		
		if(min_build_price == BUILD1_fullprice) {
			
			min_build_price_prosperity = prosperityBUILD1;
			
		}
		
		
		if(min_build_price == BUILD2_fullprice) {
			
			min_build_price_prosperity = prosperityBUILD2;
			
			
		}
		

		
		if(i == 1) {
			
			
		
		int h_initial = (((100 - initialProsperity) / min_build_price_prosperity) * min_build_price) ;
		//checking which heuristic
		initial.setHeuristic(h_initial);
		initial.setState(initialFood + ";" + initialMaterials + ";" + initialEnergy + ";" +  initialProsperity + ";" + money_spent + ";" + waiting_for + ";" + remaining_delivery_time +";" +h_initial);

	}
		
		
		if(i==2) {
			
			int h_initial = (((100 - initialProsperity) / (initial_total_resources+1)) * min_build_price) ;
			initial.setHeuristic(h_initial);
			initial.setState(initialFood + ";" + initialMaterials + ";" + initialEnergy + ";" +  initialProsperity + ";" + money_spent + ";" + waiting_for + ";" + remaining_delivery_time +";" +h_initial);
			
		}

		
		
		
		PriorityQueue<Node> remaining_nodes = new PriorityQueue<>((node1, node2) -> {
            if ((node1.getHeuristic() + node1.getPath_cost()) < (node2.getHeuristic() + node2.getPath_cost())) {
                return -1;
            }
            if ((node1.getHeuristic() + node1.getPath_cost())  > (node2.getHeuristic() + node2.getPath_cost())) {
                return 1;
            }
            return 0; });
		
		remaining_nodes.add(initial);
		
		ArrayList<String> unique_states = new ArrayList();
		unique_states.add(initial.getState());
		
		
		Node current ;
		Node goal_state = null;
		int currentFood;
		int currentMaterials;
		int currentEnergy;
		int currentProsperity = 0;
		int current_money_spent;
		
		int newFood;
		int newMaterials;
		int newEnergy;
		int newProsperity;
		int new_money_spent;
		String new_waiting_for = "null";
		int newDelay = 0;
		String newState;
		
		
		
		boolean canRequestFood;
		boolean canRequestMaterials;
		boolean canRequestEnergy;
		boolean canWait;
		boolean canBUILD1;
		boolean canBUILD2;
		String current_waiting_for;
		int current_remaining_delay;
		
		
		
		while(remaining_nodes.isEmpty() == false) {
			
			current = remaining_nodes.poll();
			if(v) {
				
				System.out.println(current.getState());
			}
			expandedNodes++;
			String [] current_state_splitted = current.getState().split(";");
			
			
			if(current.getParent() == null) {   //checking if we are at an initial state
				
				
				currentFood = initialFood;
				currentMaterials = initialMaterials;
				currentEnergy = initialEnergy;
				currentProsperity = initialProsperity;
				current_money_spent = 0;
				current_waiting_for = "null";
				current_remaining_delay =0;
				
				
			}
			
			else {     //if we are not at initial state
				
				 currentFood = Integer.parseInt(current_state_splitted[0]);
				 currentMaterials = Integer.parseInt(current_state_splitted[1]);
				 currentEnergy = Integer.parseInt(current_state_splitted[2]);
				 currentProsperity = Integer.parseInt(current_state_splitted[3]);
				 current_money_spent = Integer.parseInt(current_state_splitted[4]);
				 current_waiting_for = current_state_splitted[5];
				 current_remaining_delay = Integer.parseInt(current_state_splitted[6]);
				
				
			}
			
			if(currentProsperity>= 100) {  //checking if we reach our goal of prosperity in our current state
				
				
				 goal_state = current;
				 break;

			}
			
			
			if(!(current_waiting_for.equals("null")) && (current_remaining_delay >0)) {
				
				newDelay = current_remaining_delay-1;
				
			}
			else {
				
				newDelay = 0;
			}
			
			
			
			//Checking possible actions we can do 
			
			canRequestFood = canRequestFood(current_state_splitted);
			//System.out.println(canRequestFood);
			canRequestMaterials = canRequestMaterials(current_state_splitted);
			canRequestEnergy = canRequestEnergy(current_state_splitted);
			canWait = canWait(current_state_splitted);
			canBUILD1 = canBuild1(current_state_splitted);
			canBUILD2 = canBuild2(current_state_splitted);
			
			
			
			
			
			
			if(canBUILD1) { //if action build1 is possible
				
				newFood = currentFood - foodUseBUILD1;
				newMaterials = currentMaterials - materialsUseBUILD1;
				newEnergy = currentEnergy - energyUseBUILD1;
				newProsperity = currentProsperity + prosperityBUILD1;
				new_money_spent = current_money_spent + priceBUILD1 + (foodUseBUILD1 * unitPriceFood) + (materialsUseBUILD1 * unitPriceMaterials) + (energyUseBUILD1 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null")) ) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) + new_money_spent;
				}
				
				if(i ==2) {
					
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials +1)) * min_build_price) + new_money_spent;
					
				}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				Node n = new Node(newState,current,"BUILD1",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if((new_money_spent<= 100000) && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);
					}
					}
				}
			
			
			
			if(canBUILD2) { //if action build2 is possible
				
				newFood = currentFood - foodUseBUILD2;
				newMaterials = currentMaterials - materialsUseBUILD2;
				newEnergy = currentEnergy - energyUseBUILD2;
				newProsperity = currentProsperity + prosperityBUILD2;
				new_money_spent = current_money_spent + priceBUILD2 + (foodUseBUILD2 * unitPriceFood) + (materialsUseBUILD2 * unitPriceMaterials) + (energyUseBUILD2 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null"))) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price)+ new_money_spent ;
				}
				
				if(i ==2) {
					
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials +1)) * min_build_price) + new_money_spent;
					
				}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				Node n = new Node(newState,current,"BUILD2",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);
					}
					}
				}
			
			
			
			if(canWait) { //check if action WAIT is possible
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = current_waiting_for;
				
				if((newDelay == 0)) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) + new_money_spent;
				}
				if(i ==2) {
					
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials +1)) * min_build_price) + new_money_spent;
					
				}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				

				Node n = new Node(newState,current,"WAIT",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
			
			
			}
			
			
			
			if(canRequestFood) { //check if RequestFood action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Food";
				newDelay = delayRequestFood;
				
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) + new_money_spent ;
				}
				if(i ==2) {
					
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials +1)) * min_build_price) + new_money_spent;
					
				}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				Node n = new Node(newState,current,"RequestFood",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
				
			}
			
			
			
			if(canRequestMaterials) { //check if RequestMaterials action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Materials";
				newDelay = delayRequestMaterials;
				
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) + new_money_spent ;
				}
				if(i ==2) {
					
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials +1)) * min_build_price) + new_money_spent;
					
				}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				Node n = new Node(newState,current,"RequestMaterials",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
				
				
			}
			if(canRequestEnergy) { //check if RequestEnergy action is available
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Energy";
				newDelay = delayRequestEnergy;
				
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) + new_money_spent;
				}
				if(i ==2) {
					
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials +1)) * min_build_price) + new_money_spent;
					
				}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				Node n = new Node(newState,current,"RequestEnergy",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
			}
			
			
			
			
			

		
			
		
			
			
		}
		
		if(goal_state == null) {
			
			return "NOSOLUTION";
		}
		
		else {
			
			Node tmp = goal_state;
			String plan = "";
			
		while(tmp.getOperator() != null) {
			
			plan = tmp.getOperator() + plan  ;
			tmp = tmp.getParent();
			
			if(tmp.getOperator() != null) {
				plan = ","+ plan ;
			}
			
		}
		
		String output = plan + ";" + goal_state.getPath_cost() + ";" + expandedNodes;
			
			
			
			//System.out.println("Solution Reached with " + expandedNodes + " Nodes Expanded and Prosperity of " + currentProsperity + " With total money spent of " + goal_state.getPath_cost());
			return output;}
	
		
	}
	
	public static String solveGR(Node initial,boolean v, int i) {
		
		
		int BUILD1_fullprice = (foodUseBUILD1 * unitPriceFood) + (materialsUseBUILD1 * unitPriceMaterials) + (energyUseBUILD1 * unitPriceEnergy) + priceBUILD1;
		int BUILD2_fullprice = (foodUseBUILD2 * unitPriceFood) + (materialsUseBUILD2 * unitPriceMaterials) + (energyUseBUILD2 * unitPriceEnergy) + priceBUILD2;
		
		int min_build_price = Integer.min(BUILD1_fullprice,BUILD2_fullprice);
		int min_build_price_prosperity = 0;
		int initial_total_resources = initialFood + initialEnergy + initialMaterials;
		
		if(min_build_price == BUILD1_fullprice) {
			
			min_build_price_prosperity = prosperityBUILD1;
			
		}
		
		
		if(min_build_price == BUILD2_fullprice) {
			
			min_build_price_prosperity = prosperityBUILD2;
			
			
		}
		
		
		if(i == 1) {
			
			
		
		int h_initial = (((100 - initialProsperity) / min_build_price_prosperity) * min_build_price) ;
		//checking which heuristic
		initial.setHeuristic(h_initial);
		initial.setState(initialFood + ";" + initialMaterials + ";" + initialEnergy + ";" +  initialProsperity + ";" + money_spent + ";" + waiting_for + ";" + remaining_delivery_time +";" +h_initial);
	
	}
		
		if(i==2) {
			
			int h_initial = (((100 - initialProsperity) / initial_total_resources+1) * min_build_price) ;
			initial.setHeuristic(h_initial);
			initial.setState(initialFood + ";" + initialMaterials + ";" + initialEnergy + ";" +  initialProsperity + ";" + money_spent + ";" + waiting_for + ";" + remaining_delivery_time +";" +h_initial);
			
		}

		
		
		
		PriorityQueue<Node> remaining_nodes = new PriorityQueue<>((node1, node2) -> {
            if (node1.getHeuristic() < node2.getHeuristic()) {
                return -1;
            }
            if (node1.getHeuristic() > node2.getHeuristic()) {
                return 1;
            }
            return 0; });
		
		remaining_nodes.add(initial);
		
		ArrayList<String> unique_states = new ArrayList();
		unique_states.add(initial.getState());
		
		
		Node current ;
		Node goal_state = null;
		int currentFood;
		int currentMaterials;
		int currentEnergy;
		int currentProsperity = 0;
		int current_money_spent;
		
		int newFood;
		int newMaterials;
		int newEnergy;
		int newProsperity;
		int new_money_spent;
		String new_waiting_for = "null";
		int newDelay = 0;
		String newState;
		
		
		
		boolean canRequestFood;
		boolean canRequestMaterials;
		boolean canRequestEnergy;
		boolean canWait;
		boolean canBUILD1;
		boolean canBUILD2;
		String current_waiting_for;
		int current_remaining_delay;
		
		
		
		while(remaining_nodes.isEmpty() == false) {
			
			current = remaining_nodes.poll();
			if(v) {
				
				System.out.println(current.getState());
			}
			expandedNodes++;
			String [] current_state_splitted = current.getState().split(";");
			
			
			if(current.getParent() == null) {   //checking if we are at an initial state
				
				
				currentFood = initialFood;
				currentMaterials = initialMaterials;
				currentEnergy = initialEnergy;
				currentProsperity = initialProsperity;
				current_money_spent = 0;
				current_waiting_for = "null";
				current_remaining_delay =0;
				
				
			}
			
			else {     //if we are not at initial state
				
				 currentFood = Integer.parseInt(current_state_splitted[0]);
				 currentMaterials = Integer.parseInt(current_state_splitted[1]);
				 currentEnergy = Integer.parseInt(current_state_splitted[2]);
				 currentProsperity = Integer.parseInt(current_state_splitted[3]);
				 current_money_spent = Integer.parseInt(current_state_splitted[4]);
				 current_waiting_for = current_state_splitted[5];
				 current_remaining_delay = Integer.parseInt(current_state_splitted[6]);
				
				
			}
			
			if(currentProsperity>= 100) {  //checking if we reach our goal of prosperity in our current state
				
				
				 goal_state = current;
				 break;

			}
			
			
			if(!(current_waiting_for.equals("null")) && (current_remaining_delay >0)) {
				
				newDelay = current_remaining_delay-1;
				
			}
			else {
				
				newDelay = 0;
			}
			
			
			
			//Checking possible actions we can do 
			
			canRequestFood = canRequestFood(current_state_splitted);
			//System.out.println(canRequestFood);
			canRequestMaterials = canRequestMaterials(current_state_splitted);
			canRequestEnergy = canRequestEnergy(current_state_splitted);
			canWait = canWait(current_state_splitted);
			canBUILD1 = canBuild1(current_state_splitted);
			canBUILD2 = canBuild2(current_state_splitted);
			
			
			
			
			
			
			if(canBUILD1) { //if action build1 is possible
				
				newFood = currentFood - foodUseBUILD1;
				newMaterials = currentMaterials - materialsUseBUILD1;
				newEnergy = currentEnergy - energyUseBUILD1;
				newProsperity = currentProsperity + prosperityBUILD1;
				new_money_spent = current_money_spent + priceBUILD1 + (foodUseBUILD1 * unitPriceFood) + (materialsUseBUILD1 * unitPriceMaterials) + (energyUseBUILD1 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null")) ) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) ;
				}
				
				if(i == 2) {
				new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials+1)) * min_build_price) ;
				}
				
				
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				Node n = new Node(newState,current,"BUILD1",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if((new_money_spent<= 100000) && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);
					}
					}
				}
			
			
			
			if(canBUILD2) { //if action build2 is possible
				
				newFood = currentFood - foodUseBUILD2;
				newMaterials = currentMaterials - materialsUseBUILD2;
				newEnergy = currentEnergy - energyUseBUILD2;
				newProsperity = currentProsperity + prosperityBUILD2;
				new_money_spent = current_money_spent + priceBUILD2 + (foodUseBUILD2 * unitPriceFood) + (materialsUseBUILD2 * unitPriceMaterials) + (energyUseBUILD2 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null"))) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) ;
				}
				
				if(i == 2) {
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials+1)) * min_build_price) ;
					}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				Node n = new Node(newState,current,"BUILD2",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);
					}
					}
				}
			
			
			
			if(canWait) { //check if action WAIT is possible
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = current_waiting_for;
				
				if((newDelay == 0)) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) ;
				}
				
				if(i == 2) {
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials+1)) * min_build_price) ;
					}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				

				Node n = new Node(newState,current,"WAIT",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
			
			
			}
			
			
			
			if(canRequestFood) { //check if RequestFood action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Food";
				newDelay = delayRequestFood;
				
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) ;
				}
				
				if(i == 2) {
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials+1)) * min_build_price) ;
					}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				Node n = new Node(newState,current,"RequestFood",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
				
			}
			
			
			
			if(canRequestMaterials) { //check if RequestMaterials action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Materials";
				newDelay = delayRequestMaterials;
				
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) ;
				}
				
				if(i == 2) {
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials+1)) * min_build_price) ;
					}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				Node n = new Node(newState,current,"RequestMaterials",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
				
				
			}
			if(canRequestEnergy) { //check if RequestEnergy action is available
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Energy";
				newDelay = delayRequestEnergy;
				
				int new_heuristic = 0;
				if(i == 1) {
				new_heuristic = (((100 - newProsperity) / min_build_price_prosperity) * min_build_price) ;
				}
				
				if(i == 2) {
					new_heuristic = (((100 - newProsperity) / (newFood+newEnergy+newMaterials+1)) * min_build_price) ;
					}
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay +";" + new_heuristic;
				Node n = new Node(newState,current,"RequestEnergy",current.getDepth()+1,new_money_spent,new_heuristic);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
			}
			
			
			
			
			

		
			
		
			
			
		}
		
		if(goal_state == null) {
			
			return "NOSOLUTION";
		}
		
		else {
			
			Node tmp = goal_state;
			String plan = "";
			
		while(tmp.getOperator() != null) {
			
			plan = tmp.getOperator() + plan  ;
			tmp = tmp.getParent();
			
			if(tmp.getOperator() != null) {
				plan = ","+ plan ;
			}
			
		}
		
		String output = plan + ";" + goal_state.getPath_cost() + ";" + expandedNodes;
			
			
			
			//System.out.println("Solution Reached with " + expandedNodes + " Nodes Expanded and Prosperity of " + currentProsperity + " With total money spent of " + goal_state.getPath_cost());
			return output;}
		
	}
	
	public static String solveID(Node initial, boolean v) {
		
		boolean nextLevelExists = true;
		boolean goalFound = false;
		Node goal_state = null;
		int max_level = 0;
		
		
while(nextLevelExists) {
	
	

		nextLevelExists= false;
        Stack <Node> remaining_nodes = new Stack<> ();
		
		remaining_nodes.push(initial);
		
		ArrayList<String> unique_states = new ArrayList();
		unique_states.add(initial.getState());
		
		
		Node current ;
		
		int currentFood;
		int currentMaterials;
		int currentEnergy;
		int currentProsperity = 0;
		int current_money_spent;
		
		int newFood;
		int newMaterials;
		int newEnergy;
		int newProsperity;
		int new_money_spent;
		String new_waiting_for = "null";
		int newDelay = 0;
		String newState;
		
		
		
		boolean canRequestFood;
		boolean canRequestMaterials;
		boolean canRequestEnergy;
		boolean canWait;
		boolean canBUILD1;
		boolean canBUILD2;
		String current_waiting_for;
		int current_remaining_delay;
		
		
		
		while(remaining_nodes.isEmpty() == false) {
			
			current = remaining_nodes.pop();
			if(v) {
				
				System.out.println(current.getState());
			}
			expandedNodes++;
			String [] current_state_splitted = current.getState().split(";");
			
			
			if(current.getParent() == null) {   //checking if we are at an initial state
				
				
				currentFood = initialFood;
				currentMaterials = initialMaterials;
				currentEnergy = initialEnergy;
				currentProsperity = initialProsperity;
				current_money_spent = 0;
				current_waiting_for = "null";
				current_remaining_delay =0;
				
				
			}
			
			else {     //if we are not at initial state
				
				 currentFood = Integer.parseInt(current_state_splitted[0]);
				 currentMaterials = Integer.parseInt(current_state_splitted[1]);
				 currentEnergy = Integer.parseInt(current_state_splitted[2]);
				 currentProsperity = Integer.parseInt(current_state_splitted[3]);
				 current_money_spent = Integer.parseInt(current_state_splitted[4]);
				 current_waiting_for = current_state_splitted[5];
				 current_remaining_delay = Integer.parseInt(current_state_splitted[6]);
				
				
			}
			
			if(currentProsperity>= 100) {  //checking if we reach our goal of prosperity in our current state
				
				
				 goal_state = current;
				 goalFound = true;
				 break;

			}
			
			
			if(!(current_waiting_for.equals("null")) && (current_remaining_delay >0)) {
				
				newDelay = current_remaining_delay-1;
				
			}
			else {
				
				newDelay = 0;
			}
			
			
			
			//Checking possible actions we can do 
			
			canRequestFood = canRequestFood(current_state_splitted);
			//System.out.println(canRequestFood);
			canRequestMaterials = canRequestMaterials(current_state_splitted);
			canRequestEnergy = canRequestEnergy(current_state_splitted);
			canWait = canWait(current_state_splitted);
			canBUILD1 = canBuild1(current_state_splitted);
			canBUILD2 = canBuild2(current_state_splitted);
			
			
			
			if(canRequestEnergy) { //check if RequestEnergy action is available
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Energy";
				newDelay = delayRequestEnergy;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestEnergy",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						if(n.getDepth()>max_level) {
							
							nextLevelExists = true;
						}
						
						else {
						unique_states.add(newState);
						remaining_nodes.push(n);}}
				}
				
			}
			
			
			if(canRequestMaterials) { //check if RequestMaterials action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Materials";
				newDelay = delayRequestMaterials;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestMaterials",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						if(n.getDepth()>max_level) {
							
							nextLevelExists = true;
						}
						else {
						
						unique_states.add(newState);
						remaining_nodes.push(n);}}
				}
				
				
				
				
			}
			
			
			if(canRequestFood) { //check if RequestFood action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Food";
				newDelay = delayRequestFood;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestFood",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						if(n.getDepth()>max_level) {
							
							nextLevelExists = true;
						}
						else {
						
						unique_states.add(newState);
						remaining_nodes.push(n);}}
				}
				
				
				
			}
			
			
			if(canWait) { //check if action WAIT is possible
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = current_waiting_for;
				
				if((newDelay == 0)) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"WAIT",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						if(n.getDepth()>max_level) {
							
							nextLevelExists = true;
						}
						else {
						unique_states.add(newState);
						remaining_nodes.push(n);}}
				}
				
			
			
			}
			
			
			
			if(canBUILD2) { //if action build2 is possible
				
				newFood = currentFood - foodUseBUILD2;
				newMaterials = currentMaterials - materialsUseBUILD2;
				newEnergy = currentEnergy - energyUseBUILD2;
				newProsperity = currentProsperity + prosperityBUILD2;
				new_money_spent = current_money_spent + priceBUILD2 + (foodUseBUILD2 * unitPriceFood) + (materialsUseBUILD2 * unitPriceMaterials) + (energyUseBUILD2 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null"))) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"BUILD2",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						if(n.getDepth()>max_level) {
							
							nextLevelExists = true;
						}
						
						else {
						
						unique_states.add(newState);
						remaining_nodes.push(n);
						}
					}
					}
				}
			
			
			
			
			
			
			if(canBUILD1) { //if action build1 is possible
				
				newFood = currentFood - foodUseBUILD1;
				newMaterials = currentMaterials - materialsUseBUILD1;
				newEnergy = currentEnergy - energyUseBUILD1;
				newProsperity = currentProsperity + prosperityBUILD1;
				new_money_spent = current_money_spent + priceBUILD1 + (foodUseBUILD1 * unitPriceFood) + (materialsUseBUILD1 * unitPriceMaterials) + (energyUseBUILD1 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null")) ) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"BUILD1",current.getDepth()+1,new_money_spent);
				
				if((new_money_spent<= 100000) && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						if(n.getDepth()>max_level) {
							
							nextLevelExists = true;
						}
						else {
						
						unique_states.add(newState);
						remaining_nodes.push(n);
						
						}
					}
					}}
			
			
			

			
			
		}
		
		if(goalFound) {
			
			break;
		}
		
		max_level++;
		
		
}
		
		if(goal_state == null) {
			
			return "NOSOLUTION";
		}
		
		else {
			
			Node tmp = goal_state;
			String plan = "";
			
		while(tmp.getOperator() != null) {
			
			plan = tmp.getOperator() + plan  ;
			tmp = tmp.getParent();
			
			if(tmp.getOperator() != null) {
				plan = ","+ plan ;
			}
			
		}
		
		String output = plan + ";" + goal_state.getPath_cost() + ";" + expandedNodes;
			
			
			
			//System.out.println("Solution Reached with " + expandedNodes + " Nodes Expanded and Prosperity of " + currentProsperity + " With total money spent of " + goal_state.getPath_cost());
			return output;}
		
		
		
	}
	
	public static String solveUC(Node initial, boolean v) {
		
		
		PriorityQueue<Node> remaining_nodes = new PriorityQueue<>((node1, node2) -> {
            if (node1.getPath_cost() < node2.getPath_cost()) {
                return -1;
            }
            if (node1.getPath_cost() > node2.getPath_cost()) {
                return 1;
            }
            return 0; });
		
		remaining_nodes.add(initial);
		
		ArrayList<String> unique_states = new ArrayList();
		unique_states.add(initial.getState());
		
		
		Node current ;
		Node goal_state = null;
		int currentFood;
		int currentMaterials;
		int currentEnergy;
		int currentProsperity = 0;
		int current_money_spent;
		
		int newFood;
		int newMaterials;
		int newEnergy;
		int newProsperity;
		int new_money_spent;
		String new_waiting_for = "null";
		int newDelay = 0;
		String newState;
		
		
		
		boolean canRequestFood;
		boolean canRequestMaterials;
		boolean canRequestEnergy;
		boolean canWait;
		boolean canBUILD1;
		boolean canBUILD2;
		String current_waiting_for;
		int current_remaining_delay;
		
		
		
		while(remaining_nodes.isEmpty() == false) {
			
			current = remaining_nodes.poll();
			if(v) {
				
				System.out.println(current.getState());
			}
			expandedNodes++;
			String [] current_state_splitted = current.getState().split(";");
			
			
			if(current.getParent() == null) {   //checking if we are at an initial state
				
				
				currentFood = initialFood;
				currentMaterials = initialMaterials;
				currentEnergy = initialEnergy;
				currentProsperity = initialProsperity;
				current_money_spent = 0;
				current_waiting_for = "null";
				current_remaining_delay =0;
				
				
			}
			
			else {     //if we are not at initial state
				
				 currentFood = Integer.parseInt(current_state_splitted[0]);
				 currentMaterials = Integer.parseInt(current_state_splitted[1]);
				 currentEnergy = Integer.parseInt(current_state_splitted[2]);
				 currentProsperity = Integer.parseInt(current_state_splitted[3]);
				 current_money_spent = Integer.parseInt(current_state_splitted[4]);
				 current_waiting_for = current_state_splitted[5];
				 current_remaining_delay = Integer.parseInt(current_state_splitted[6]);
				
				
			}
			
			if(currentProsperity>= 100) {  //checking if we reach our goal of prosperity in our current state
				
				
				 goal_state = current;
				 break;

			}
			
			
			if(!(current_waiting_for.equals("null")) && (current_remaining_delay >0)) {
				
				newDelay = current_remaining_delay-1;
				
			}
			else {
				
				newDelay = 0;
			}
			
			
			
			//Checking possible actions we can do 
			
			canRequestFood = canRequestFood(current_state_splitted);
			//System.out.println(canRequestFood);
			canRequestMaterials = canRequestMaterials(current_state_splitted);
			canRequestEnergy = canRequestEnergy(current_state_splitted);
			canWait = canWait(current_state_splitted);
			canBUILD1 = canBuild1(current_state_splitted);
			canBUILD2 = canBuild2(current_state_splitted);
			
			
			
			
			
			
			if(canBUILD1) { //if action build1 is possible
				
				newFood = currentFood - foodUseBUILD1;
				newMaterials = currentMaterials - materialsUseBUILD1;
				newEnergy = currentEnergy - energyUseBUILD1;
				newProsperity = currentProsperity + prosperityBUILD1;
				new_money_spent = current_money_spent + priceBUILD1 + (foodUseBUILD1 * unitPriceFood) + (materialsUseBUILD1 * unitPriceMaterials) + (energyUseBUILD1 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null")) ) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"BUILD1",current.getDepth()+1,new_money_spent);
				
				if((new_money_spent<= 100000) && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);
					}
					}
				}
			
			
			
			if(canBUILD2) { //if action build2 is possible
				
				newFood = currentFood - foodUseBUILD2;
				newMaterials = currentMaterials - materialsUseBUILD2;
				newEnergy = currentEnergy - energyUseBUILD2;
				newProsperity = currentProsperity + prosperityBUILD2;
				new_money_spent = current_money_spent + priceBUILD2 + (foodUseBUILD2 * unitPriceFood) + (materialsUseBUILD2 * unitPriceMaterials) + (energyUseBUILD2 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null"))) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"BUILD2",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);
					}
					}
				}
			
			
			
			if(canWait) { //check if action WAIT is possible
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = current_waiting_for;
				
				if((newDelay == 0)) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"WAIT",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
			
			
			}
			
			
			
			if(canRequestFood) { //check if RequestFood action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Food";
				newDelay = delayRequestFood;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestFood",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
				
			}
			
			
			
			if(canRequestMaterials) { //check if RequestMaterials action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Materials";
				newDelay = delayRequestMaterials;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestMaterials",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
				
				
			}
			if(canRequestEnergy) { //check if RequestEnergy action is available
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Energy";
				newDelay = delayRequestEnergy;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestEnergy",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
			}
			
			
			
			
			

		
			
		
			
			
		}
		
		if(goal_state == null) {
			
			return "NOSOLUTION";
		}
		
		else {
			
			Node tmp = goal_state;
			String plan = "";
			
		while(tmp.getOperator() != null) {
			
			plan = tmp.getOperator() + plan  ;
			tmp = tmp.getParent();
			
			if(tmp.getOperator() != null) {
				plan = ","+ plan ;
			}
			
		}
		
		String output = plan + ";" + goal_state.getPath_cost() + ";" + expandedNodes;
			
			
			
			//System.out.println("Solution Reached with " + expandedNodes + " Nodes Expanded and Prosperity of " + currentProsperity + " With total money spent of " + goal_state.getPath_cost());
			return output;}
		
		
		
		
		
		
	}
	
	public static String solveDF(Node initial, boolean v) {
		
		
		Stack <Node> remaining_nodes = new Stack<> ();
		
		remaining_nodes.push(initial);
		
		ArrayList<String> unique_states = new ArrayList();
		unique_states.add(initial.getState());
		
		
		Node current ;
		Node goal_state = null;
		int currentFood;
		int currentMaterials;
		int currentEnergy;
		int currentProsperity = 0;
		int current_money_spent;
		
		int newFood;
		int newMaterials;
		int newEnergy;
		int newProsperity;
		int new_money_spent;
		String new_waiting_for = "null";
		int newDelay = 0;
		String newState;
		
		
		
		boolean canRequestFood;
		boolean canRequestMaterials;
		boolean canRequestEnergy;
		boolean canWait;
		boolean canBUILD1;
		boolean canBUILD2;
		String current_waiting_for;
		int current_remaining_delay;
		
		
		
		while(remaining_nodes.isEmpty() == false) {
			
			current = remaining_nodes.pop();
			if(v) {
				
				System.out.println(current.getState());
			}
			expandedNodes++;
			String [] current_state_splitted = current.getState().split(";");
			
			
			if(current.getParent() == null) {   //checking if we are at an initial state
				
				
				currentFood = initialFood;
				currentMaterials = initialMaterials;
				currentEnergy = initialEnergy;
				currentProsperity = initialProsperity;
				current_money_spent = 0;
				current_waiting_for = "null";
				current_remaining_delay =0;
				
				
			}
			
			else {     //if we are not at initial state
				
				 currentFood = Integer.parseInt(current_state_splitted[0]);
				 currentMaterials = Integer.parseInt(current_state_splitted[1]);
				 currentEnergy = Integer.parseInt(current_state_splitted[2]);
				 currentProsperity = Integer.parseInt(current_state_splitted[3]);
				 current_money_spent = Integer.parseInt(current_state_splitted[4]);
				 current_waiting_for = current_state_splitted[5];
				 current_remaining_delay = Integer.parseInt(current_state_splitted[6]);
				
				
			}
			
			if(currentProsperity>= 100) {  //checking if we reach our goal of prosperity in our current state
				
				
				 goal_state = current;
				 break;

			}
			
			
			if(!(current_waiting_for.equals("null")) && (current_remaining_delay >0)) {
				
				newDelay = current_remaining_delay-1;
				
			}
			else {
				
				newDelay = 0;
			}
			
			
			
			//Checking possible actions we can do 
			
			canRequestFood = canRequestFood(current_state_splitted);
			//System.out.println(canRequestFood);
			canRequestMaterials = canRequestMaterials(current_state_splitted);
			canRequestEnergy = canRequestEnergy(current_state_splitted);
			canWait = canWait(current_state_splitted);
			canBUILD1 = canBuild1(current_state_splitted);
			canBUILD2 = canBuild2(current_state_splitted);
			
			
			
			if(canRequestEnergy) { //check if RequestEnergy action is available
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Energy";
				newDelay = delayRequestEnergy;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestEnergy",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.push(n);}}
				
				
			}
			
			
			if(canRequestMaterials) { //check if RequestMaterials action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Materials";
				newDelay = delayRequestMaterials;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestMaterials",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.push(n);}}
				
				
				
				
			}
			
			
			if(canRequestFood) { //check if RequestFood action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Food";
				newDelay = delayRequestFood;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestFood",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.push(n);}}
				
				
				
			}
			
			
			if(canWait) { //check if action WAIT is possible
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = current_waiting_for;
				
				if((newDelay == 0)) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"WAIT",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.push(n);}}
				
			
			
			}
			
			
			
			if(canBUILD2) { //if action build2 is possible
				
				newFood = currentFood - foodUseBUILD2;
				newMaterials = currentMaterials - materialsUseBUILD2;
				newEnergy = currentEnergy - energyUseBUILD2;
				newProsperity = currentProsperity + prosperityBUILD2;
				new_money_spent = current_money_spent + priceBUILD2 + (foodUseBUILD2 * unitPriceFood) + (materialsUseBUILD2 * unitPriceMaterials) + (energyUseBUILD2 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null"))) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"BUILD2",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.push(n);
					}
					}
				}
			
			
			
			
			
			
			if(canBUILD1) { //if action build1 is possible
				
				newFood = currentFood - foodUseBUILD1;
				newMaterials = currentMaterials - materialsUseBUILD1;
				newEnergy = currentEnergy - energyUseBUILD1;
				newProsperity = currentProsperity + prosperityBUILD1;
				new_money_spent = current_money_spent + priceBUILD1 + (foodUseBUILD1 * unitPriceFood) + (materialsUseBUILD1 * unitPriceMaterials) + (energyUseBUILD1 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null")) ) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"BUILD1",current.getDepth()+1,new_money_spent);
				
				if((new_money_spent<= 100000) && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.push(n);
					}
					}
				}
			
			
			

			
			
		}
		
		if(goal_state == null) {
			
			return "NOSOLUTION";
		}
		
		else {
			
			Node tmp = goal_state;
			String plan = "";
			
		while(tmp.getOperator() != null) {
			
			plan = tmp.getOperator() + plan  ;
			tmp = tmp.getParent();
			
			if(tmp.getOperator() != null) {
				plan = ","+ plan ;
			}
			
		}
		
		String output = plan + ";" + goal_state.getPath_cost() + ";" + expandedNodes;
			
			
			
			//System.out.println("Solution Reached with " + expandedNodes + " Nodes Expanded and Prosperity of " + currentProsperity + " With total money spent of " + goal_state.getPath_cost());
			return output;}
		
	}
	
	public static String solveBF(Node initial, boolean v) {
		
		
		
		Queue <Node> remaining_nodes = new LinkedList <> ();
		remaining_nodes.add(initial);
		
		ArrayList<String> unique_states = new ArrayList();
		unique_states.add(initial.getState());
		
		
		Node current ;
		Node goal_state = null;
		int currentFood;
		int currentMaterials;
		int currentEnergy;
		int currentProsperity = 0;
		int current_money_spent;
		
		int newFood;
		int newMaterials;
		int newEnergy;
		int newProsperity;
		int new_money_spent;
		String new_waiting_for = "null";
		int newDelay = 0;
		String newState;
		
		
		
		boolean canRequestFood;
		boolean canRequestMaterials;
		boolean canRequestEnergy;
		boolean canWait;
		boolean canBUILD1;
		boolean canBUILD2;
		String current_waiting_for;
		int current_remaining_delay;
		
		
		
		while(remaining_nodes.isEmpty() == false) {
			
			current = remaining_nodes.poll();
			if(v) {
				
				System.out.println(current.getState());
			}
			expandedNodes++;
			String [] current_state_splitted = current.getState().split(";");
			
			
			if(current.getParent() == null) {   //checking if we are at an initial state
				
				
				currentFood = initialFood;
				currentMaterials = initialMaterials;
				currentEnergy = initialEnergy;
				currentProsperity = initialProsperity;
				current_money_spent = 0;
				current_waiting_for = "null";
				current_remaining_delay =0;
				
				
			}
			
			else {     //if we are not at initial state
				
				 currentFood = Integer.parseInt(current_state_splitted[0]);
				 currentMaterials = Integer.parseInt(current_state_splitted[1]);
				 currentEnergy = Integer.parseInt(current_state_splitted[2]);
				 currentProsperity = Integer.parseInt(current_state_splitted[3]);
				 current_money_spent = Integer.parseInt(current_state_splitted[4]);
				 current_waiting_for = current_state_splitted[5];
				 current_remaining_delay = Integer.parseInt(current_state_splitted[6]);
				
				
			}
			
			if(currentProsperity>= 100) {  //checking if we reach our goal of prosperity in our current state
				
				
				 goal_state = current;
				 break;

			}
			
			
			if(!(current_waiting_for.equals("null")) && (current_remaining_delay >0)) {
				
				newDelay = current_remaining_delay-1;
				
			}
			else {
				
				newDelay = 0;
			}
			
			
			
			//Checking possible actions we can do 
			
			canRequestFood = canRequestFood(current_state_splitted);
			//System.out.println(canRequestFood);
			canRequestMaterials = canRequestMaterials(current_state_splitted);
			canRequestEnergy = canRequestEnergy(current_state_splitted);
			canWait = canWait(current_state_splitted);
			canBUILD1 = canBuild1(current_state_splitted);
			canBUILD2 = canBuild2(current_state_splitted);
			
			
			
			
			
			
			if(canBUILD1) { //if action build1 is possible
				
				newFood = currentFood - foodUseBUILD1;
				newMaterials = currentMaterials - materialsUseBUILD1;
				newEnergy = currentEnergy - energyUseBUILD1;
				newProsperity = currentProsperity + prosperityBUILD1;
				new_money_spent = current_money_spent + priceBUILD1 + (foodUseBUILD1 * unitPriceFood) + (materialsUseBUILD1 * unitPriceMaterials) + (energyUseBUILD1 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null")) ) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"BUILD1",current.getDepth()+1,new_money_spent);
				
				if((new_money_spent<= 100000) && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);
					}
					}
				}
			
			
			
			if(canBUILD2) { //if action build2 is possible
				
				newFood = currentFood - foodUseBUILD2;
				newMaterials = currentMaterials - materialsUseBUILD2;
				newEnergy = currentEnergy - energyUseBUILD2;
				newProsperity = currentProsperity + prosperityBUILD2;
				new_money_spent = current_money_spent + priceBUILD2 + (foodUseBUILD2 * unitPriceFood) + (materialsUseBUILD2 * unitPriceMaterials) + (energyUseBUILD2 * unitPriceEnergy);
				new_waiting_for = current_waiting_for;
				if(new_waiting_for.equals("null")) {
					
					newDelay = 0;
				}
				
				if((newDelay == 0) && !(new_waiting_for.equals("null"))) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"BUILD2",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);
					}
					}
				}
			
			
			
			if(canWait) { //check if action WAIT is possible
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = current_waiting_for;
				
				if((newDelay == 0)) {
					 
					if(new_waiting_for.equals("Food")) {
						
						newFood += amountRequestFood;
						new_waiting_for = "null";
						
					}
					
					if(new_waiting_for.equals("Materials")) {
						
						newMaterials += amountRequestMaterials;
						new_waiting_for = "null";
						
						
					}
					
					if(new_waiting_for.equals("Energy")) {
						
						newEnergy += amountRequestEnergy;
						new_waiting_for = "null";
						
						
					}
					
					
					
				}
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"WAIT",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000 && (newFood <=50) && (newEnergy <=50) && (newMaterials <=50)) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
			
			
			}
			
			
			
			if(canRequestFood) { //check if RequestFood action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Food";
				newDelay = delayRequestFood;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestFood",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
				
			}
			
			
			
			if(canRequestMaterials) { //check if RequestMaterials action is available
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Materials";
				newDelay = delayRequestMaterials;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestMaterials",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
				
				
			}
			if(canRequestEnergy) { //check if RequestEnergy action is available
				
				
				newFood = currentFood - 1;
				newMaterials = currentMaterials -1;
				newEnergy = currentEnergy - 1;
				newProsperity = currentProsperity;
				new_money_spent = current_money_spent + unitPriceFood + unitPriceMaterials + unitPriceEnergy ;
				new_waiting_for = "Energy";
				newDelay = delayRequestEnergy;
				
				newState = newFood + ";" + newMaterials + ";" + newEnergy + ";" + newProsperity + ";" + new_money_spent +";" + new_waiting_for + ";" + newDelay;
				Node n = new Node(newState,current,"RequestEnergy",current.getDepth()+1,new_money_spent);
				
				if(new_money_spent<= 100000) {
					
					if(unique_states.contains(newState) == false) {
						
						unique_states.add(newState);
						remaining_nodes.add(n);}}
				
				
			}
			
			
			
			
			

		
			
		
			
			
		}
		
		if(goal_state == null) {
			
			return "NOSOLUTION";
		}
		
		else {
			
			Node tmp = goal_state;
			String plan = "";
			
		while(tmp.getOperator() != null) {
			
			plan = tmp.getOperator() + plan  ;
			tmp = tmp.getParent();
			
			if(tmp.getOperator() != null) {
				plan = ","+ plan ;
			}
			
		}
		
		String output = plan + ";" + goal_state.getPath_cost() + ";" + expandedNodes;
			
			
			
			//System.out.println("Solution Reached with " + expandedNodes + " Nodes Expanded and Prosperity of " + currentProsperity + " With total money spent of " + goal_state.getPath_cost());
			return output;
		}
		
		
		
		
		
		
	}
	
	
	public static void parseInitials (String i) {
		
		
		
		String [] splitted = i.split(";");
		
		initialProsperity = Integer.parseInt(splitted[0]);
		
		String [] splitted_1 = splitted[1].split(",");
		
		
		initialFood = Integer.parseInt(splitted_1[0]);
		initialMaterials = Integer.parseInt(splitted_1[1]);
		initialEnergy = Integer.parseInt(splitted_1[2]);
		
		
		String [] splitted_2 = splitted[2].split(",");
		
		unitPriceFood = Integer.parseInt(splitted_2[0]);
		unitPriceMaterials = Integer.parseInt(splitted_2[1]);
		unitPriceEnergy = Integer.parseInt(splitted_2[2]);
		
		String [] splitted_3 = splitted[3].split(",");
		
		amountRequestFood = Integer.parseInt(splitted_3[0]);
		delayRequestFood = Integer.parseInt(splitted_3[1]);
		
		String [] splitted_4 = splitted[4].split(",");
		
		amountRequestMaterials = Integer.parseInt(splitted_4[0]);
		delayRequestMaterials = Integer.parseInt(splitted_4[1]);
		
		
		String [] splitted_5 = splitted[5].split(",");
		
		amountRequestEnergy = Integer.parseInt(splitted_5[0]);
		delayRequestEnergy = Integer.parseInt(splitted_5[1]);
		
		String [] splitted_6 = splitted[6].split(",");
		
		priceBUILD1 = Integer.parseInt(splitted_6[0]);
		foodUseBUILD1 = Integer.parseInt(splitted_6[1]);
		materialsUseBUILD1 = Integer.parseInt(splitted_6[2]);
		energyUseBUILD1 = Integer.parseInt(splitted_6[3]);
		prosperityBUILD1 = Integer.parseInt(splitted_6[4]);
		
		
		String [] splitted_7 = splitted[7].split(",");
		
		priceBUILD2 = Integer.parseInt(splitted_7[0]);
		foodUseBUILD2 = Integer.parseInt(splitted_7[1]);
		materialsUseBUILD2 = Integer.parseInt(splitted_7[2]);
		energyUseBUILD2 = Integer.parseInt(splitted_7[3]);
		prosperityBUILD2 = Integer.parseInt(splitted_7[4]);
		
		
		
	}
	
	
	
	
	public static void main(String [] args) {
		
		
		
		
		
		
//		PriorityQueue<Node> queue = new PriorityQueue<>((node1, node2) -> {
//            if (node1.getPath_cost() < node2.getPath_cost()) {
//                return -1;
//            }
//            if (node1.getPath_cost() > node2.getPath_cost()) {
//                return 1;
//            }
//            return 0;
//});
//		
//		Node x1 = new Node(null,null,null,0,55);
//		Node x2 = new Node(null,null,null,0,27);
//		Node x3 = new Node(null,null,null,0,34);
//		Node x4 = new Node(null,null,null,0,75);
//		Node x5 = new Node(null,null,null,0,25);
//		
//		queue.add(x1);
//		queue.add(x2);
//		queue.add(x3);
//		queue.add(x4);
//		queue.add(x5);
//		
//		System.out.println(queue);
//		while (!queue.isEmpty()) {
//		    Node removedNode = queue.poll();
//		    System.out.println("Removed Node with path_cost: " + removedNode.getPath_cost());
//		}
//		
		
		
		
		
		
		
		LLAPSearch s = new LLAPSearch (null,null,null,null);
		String init = "32;" +
				"20,16,11;" +
				"76,14,14;" +
				"9,1;9,2;9,1;" +
				"358,14,25,23,39;" +
				"5024,20,17,17,38;";
		
		
		System.out.println(s.solve(init, "AS2", true));
		
		
		
		
		
	
		
		
		


        
		
	}
	

}
