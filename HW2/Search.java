import java.util.*;

public class Search {
	
	private static int disimilarity = Integer.MAX_VALUE;
	private static ArrayList<Integer> traversalSeq;
	private static ArrayList<Integer> path;
	private static HashSet<Integer> visitedSet;
	private static HashMap<Integer, Integer> backTrack = new HashMap<Integer, Integer>();
	
	public static void search(int type, Dissimilarity movieDis, 
			int[] heuristic, int start, int end){
		int[] AstarHeuristic = heuristic;
		path = new ArrayList<Integer>();
		traversalSeq = new ArrayList<Integer>();
		visitedSet = new HashSet<Integer>();
		Queue<Integer> queue = new Queue<Integer>();
		queue.enqueue(start);
		visitedSet.add(start);
		
		while(!queue.isEmpty()){
			queue = Sort.mergeSort(queue, AstarHeuristic);
			int current = queue.dequeue();
			traversalSeq.add(current);
			if(current == end){
				getPath(start, end);
				break;
			}
			
			int[][] disMatrix = movieDis.getDisimilarity();
			for(int j = 0; j < disMatrix[0].length; j++){
				if(disMatrix[current][j] < Integer.MAX_VALUE && !visitedSet.contains(j)){
					queue.enqueue(j);
					visitedSet.add(j);
					backTrack.put(j, current);
					if(type == Main.ASTAR){
						getPath(start, j);
						computeDis(movieDis, start, j);
						AstarHeuristic[j] =  disimilarity + AstarHeuristic[j];
					}
				}
			}
		}
		computeDis(movieDis, start, end);
		printResult(type);
	}
	
	private static void computeDis(Dissimilarity movieDis, int start, int end){
		disimilarity = 0;
		int index = start;
		while(index < path.size() - 1){
			disimilarity += 
				movieDis.getDisimilarity()[path.get(index)][path.get(index + 1)];
			index++;
		}
	}
	
	public static void getPath(int start, int end){
//		System.out.println("test");
		path = new ArrayList<Integer>();
		path.add(end);
		int loop = end;
		while(backTrack.containsKey(loop)){
//			System.out.println(backTrack.get(loop));
			loop = backTrack.get(loop);
			path.add(0, loop);
		}
	}
	
	public static void printResult(int type){
		switch (type) {
		case Main.GREEDY:
			System.out.println("Greedy Search:");
			break;
		case Main.ASTAR:
			System.out.println("A* Search:");
			break;
		default:
			break;
		}
		
		System.out.println("Traversal sequence:");
		for(int i = 0; i < traversalSeq.size(); i++){
			System.out.print("m" + (traversalSeq.get(i) + 1));
			if(i != traversalSeq.size() - 1)
				System.out.print(", ");
		}
		System.out.println();
		
		System.out.println("Dissimilarity: " + disimilarity);
		
		System.out.println("Propagating path:");
		for(int i = 0; i < path.size(); i++){
			System.out.print("m" + (path.get(i) + 1));
			if(i != path.size() - 1)
				System.out.print(", ");
		}
		
		System.out.println();
	}
//	
//	private static void printQueue(Queue<Integer> queue){
//		for(int i = 0; i < queue.size(); i++){
//			System.out.print(queue.get(i) + " ");
//		}
//		System.out.println();
//	}
}