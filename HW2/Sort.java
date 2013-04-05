
/**
 * The sort class stores the mergeSort function, which can sort the Path.pathcost
 * in a Queue.
 * 
 * @author joshluo
 *
 */
public class Sort{
	public static Queue<Integer> mergeSort(Queue<Integer> queue, int[] heuristic){
		Queue<Integer> first = new Queue<Integer>();
		Queue<Integer> second = new Queue<Integer>();
		if(queue.size() < 2) return queue;
		for(int i = 0; i < queue.size() / 2; i++)
			first.enqueue(queue.get(i));
		for(int i = queue.size() / 2; i < queue.size(); i++)
			second.enqueue(queue.get(i));
		first = mergeSort(first, heuristic);
		second = mergeSort(second, heuristic);
		return merge(first, second, heuristic);
	}
	
	public static Queue<Integer> merge(Queue<Integer> first, 
			Queue<Integer> second, int[] heuristic){
		Queue<Integer> result = new Queue<Integer>();
		int i = 0;
		int j = 0;
		while(i < first.size() && j < second.size()){
			if(heuristic[first.get(i)] <= heuristic[second.get(j)]){
				result.enqueue(first.get(i));
				i++;
			} else {
				result.enqueue(second.get(j));
				j++;
			}
		}
		if(i < first.size()){
			for(int k = i; k < first.size(); k++){
				result.enqueue(first.get(k));
			}
		} else {
			for(int k = j; k<second.size(); k++){
                result.enqueue(second.get(k));
            }
		}
		return result;
	}
}
