import java.util.Stack;
/**
 * The Search class stores three different search methods. BFS, DFS and UCS. 
 * The UCS will reuse the BFS code, because they both use Queue as data structure.
 * @author joshluo
 *
 */
public class Search {
	public static int BFSTYPE = 0;
	public static int DFSTYPE = 1;
	public static int UCSTYPE = 2;
	
	public static void breadthFirstSearch(Node start, Node end){
		searchMethod(start, end, BFSTYPE);
	}
	
	public static void depthFirstSearch(Node start, Node end){
		searchMethod(start, end, DFSTYPE);
	}
	
	public static void uniformCostSearch(Node start, Node end){
		searchMethod(start, end, UCSTYPE);
	}
	
	/**
	 * Invoke the searchMethod according to the different type. The BFS will use 
	 * the standard queue structure. But UCS will choose to use the priority queue
	 * which was sorted by merge sort.
	 * 
	 * @param start
	 * @param end
	 * @param type
	 * @return
	 */
	private static boolean searchMethod(Node start, Node end, int type){
		Queue<Path> queue = new Queue<Path>();
		if(type == DFSTYPE) {
			return dfs(start, end);
		}
		
		//initiate the program for the start node
		Path last_path = new Path();
		last_path.path.add(start);
		last_path.pathCost += start.cost;
		queue.enqueue(last_path);
		
		//Dequeue the node in the queue is the traversal order.
		System.out.println("Traversal Order: ");
	    while (!queue.isEmpty()){
	    	//for UCS use priority Queue
	    	if(type == UCSTYPE) queue = Sort.mergeSort(queue);
	    	
	    	//get the last node from the queue
	    	last_path = queue.dequeue();
	    	Node last_node = last_path.path.getLast();
	    	
	    	//for BFS, detect if this node is visited, if not mark it as visited
	    	if(type == BFSTYPE && last_node.isVisited == true){
	    		continue;
	    	} else if(type == BFSTYPE){
	    		last_node.isVisited = true;
	    	}
	    	
	    	/*
	    	 * for UCS, detect if the current path cost greater than the minimum
	    	 * if yes, we should not consider this path.
	    	 */
	    	if(type == UCSTYPE && last_node.minCost < last_path.pathCost) 
	    		continue;
	    	
	    	//print the next node on traversal order.
	    	System.out.print("(" + (last_node.state[0] + 1) + ","
	    			+ (last_node.state[1] + 1) + ")");
	    	
	    	/*
	    	 * detect the if the current node is the final goal
	    	 * if yes, we print the current path that this node is in.
	    	 */
	    	if(last_node == end){
	    		System.out.println("\nStitching Curve: ");
//	    		System.out.println("Cost: " + last_path.pathCost);
	    		for(int i = 0; i < last_path.path.size(); i++){
	    			Node print = last_path.path.get(i);
	    			System.out.print("(" + (print.state[0] + 1) + ","
	    					+ (print.state[1] + 1) + ")");
	    			if(i == last_path.path.size() - 1)
	    				break;
	    			System.out.print(", ");
	    		}
	    		System.out.println();
	    		return true;
	    	} else {
	    		System.out.print(", ");
	    	}
	    	
	    	/*
	    	 * if not the final goal, we add the next state into queue
	    	 * we do not add the path which contains the next state.
	    	 */
	    	for(int i = 0; i < last_node.getChildren().length; i++){
		    	Node n = last_node.getChildren()[i];
		    	if(n == null) continue;
		    	if(!last_path.path.contains(n)){
		    		n.level++;
		    		Path new_path = new Path();
		    		for(int j = 0; j < last_path.path.size(); j++){
		    			new_path.path.add(last_path.path.get(j));
		    			new_path.pathCost += last_path.path.get(j).cost;
		    		}
		    		new_path.path.add(n);
		    		new_path.pathCost += n.cost;
		    		if(type == UCSTYPE && new_path.pathCost < n.minCost){
		    			n.minCost = new_path.pathCost;
		    			queue.enqueue(new_path);	
		    		} else if(type == BFSTYPE){
		    			queue.enqueue(new_path);	
		    		}
		    	}
	    	}
	    }
	    return false;
	}
	
	/**
	 * Invoke the dfs method. The DFS will use the standard stack structure.
	 * @param start
	 * @param end
	 * @return
	 */
	private static boolean dfs(Node start, Node end){
		Stack<Path> dfsStack = new Stack<Path>();
		
		//initiate the program for the start node
		Path last_path = new Path();
		last_path.path.add(start);
		last_path.pathCost += start.cost;
		dfsStack.push(last_path);
		
		//Pop the node in the stack is the traversal order.
		System.out.println("Traversal Order: ");
	    while (!dfsStack.isEmpty()){
	    	//get the last node from the queue
	    	last_path = dfsStack.pop();
	    	Node last_node = last_path.path.getLast();
	    	System.out.print("(" + (last_node.state[0] + 1) + ","
	    			+ (last_node.state[1] + 1) + ")");
	    	
	    	/*
	    	 * detect the if the current node is the final goal
	    	 * if yes, we print the current path that this node is in.
	    	 */
	    	if(last_node == end){
	    		System.out.println("\nStitching Curve: ");
//	    		System.out.println("Cost: " + last_path.pathCost);
	    		for(int i = 0; i < last_path.path.size(); i++){
	    			Node print = last_path.path.get(i);
	    			System.out.print("(" + (print.state[0] + 1) + ","
	    					+ (print.state[1] + 1) + ")");
	    			if(i == last_path.path.size() - 1)
	    				break;
	    			System.out.print(", ");
	    		}
	    		System.out.println();
	    		return true;
	    	} else {
	    		System.out.print(", ");
	    	}
	    	
	    	/*
	    	 * if not the final goal, we add the next state into queue
	    	 * we do not add the path which contains the next state.
	    	 */
	    	for(int i = last_node.getChildren().length - 1; i >= 0 ; i--){
		    	Node n = last_node.getChildren()[i];
		    	if(n == null) continue;
		    	if(!last_path.path.contains(n)){
		    		n.level++;
		    		Path new_path = new Path();
		    		for(int j = 0; j < last_path.path.size(); j++){
		    			new_path.path.add(last_path.path.get(j));
		    			new_path.pathCost += last_path.path.get(j).cost;
		    		}
		    		new_path.path.add(n);
		    		new_path.pathCost += n.cost;
			    	dfsStack.push(new_path);
		    	}
	    	}
	    }
	    return false;
	}
}
