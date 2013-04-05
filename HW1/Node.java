/**
 * This is a class of a node in the graph. It has a cost, (x, y) state and 4 
 * children. It also stores the minimum cost of a path when it exists in multiple
 * paths. parent indicates the node's parent in a path. It has a level indicating 
 * which level it exists in a tree.
 * 
 * @author joshluo
 *
 */
public class Node {
	public Node right;
	public Node down;
	public Node left;
	public Node up;
	public Node parent;
	public int minCost;
	public int level;
	public int cost;
	public int state[] = new int[2];
	public boolean isVisited;
    
	public Node(int cost, int x, int y) {
		this.cost = cost;
		state[0] = x;
		state[1] = y;
		minCost = Integer.MAX_VALUE;
		isVisited = false;
	}
	
	/**
	 * This method defines the node's parent.
	 * 
	 * @param parent
	 */
	public void setPrarent(Node parent){
	    if (parent == null)
	        this.level = 0;
	    else
	        this.level = parent.level + 1;
			this.parent = parent;
	}
	
	/**
	 * Used to get all the children as an assigned order 
	 * @return children[]
	 */
	public Node[] getChildren(){
		Node[] children = {right, up, left, down};
		return children;
	}
}