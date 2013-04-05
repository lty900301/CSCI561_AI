import java.util.ArrayList;

/**
 * This graph class describes how the graph is installed in this program, matrix.
 * The functions are getWidth(), getHeight(), init(int matrix[][]), printGraph()
 * 
 * @author joshluo
 *
 */
public class Graph{
	ArrayList<ArrayList<Node>> list = new ArrayList<ArrayList<Node>>();
	public int getWidth(){
		return list.size();
	}
	public int getHeight(){
		return list.get(0).size();
	}
	/**
	 * Used to transfer a 2-dimensional array into a graph.
	 * 
	 * @param matrix
	 */
	public Graph(int matrix[][]){
		for(int i = 0; i < matrix.length; i++){
			ArrayList<Node> col = new ArrayList<Node>();
			for(int j = 0; j < matrix[0].length; j++){
				Node n = new Node(matrix[i][j], i ,j);
				col.add(n);
			}
			list.add(col);
		}
		for(int i = 0; i < getWidth(); i++){
			for(int j = 0; j < getHeight(); j++){
				Node n = list.get(i).get(j);
				//link to the right node
				if(i == (getWidth() - 1)){
					n.right = null;
				} else {
					n.right = list.get(i+1).get(j);
				}
				
				//link to the down node
				if(j == (getHeight() - 1)){
					n.down = null;
				} else {
					n.down = list.get(i).get(j+1);
				}
				
				//link to the left node
				if(i == 0){
					n.left = null;
				} else {
					n.left = list.get(i-1).get(j);
				}
				
				//link to the down node
				if(j == 0){
					n.up = null;
				} else {
					n.up = list.get(i).get(j-1);
				}
			}
		}
	}
	
	/**
	 * This program print the graph in an structured order.
	 */
	public void printGraph(){
		for(int j = 0; j < getHeight(); j++){
			for(int i = 0; i < getWidth(); i++){
				Node n = list.get(i).get(j);
				System.out.print(n.cost + "\t");
			}
			System.out.print("\n");
		}
	}
}
