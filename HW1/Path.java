import java.util.LinkedList;
/**
 * Path class is used to store a path containing a sequence of nodes.
 * @author joshluo
 *
 */
public class Path {
	public LinkedList<Node> path;
	public int pathCost;
	public Path(){
		path = new LinkedList<Node>();
		pathCost = 0;
	}
}