/**
 * The sort class stores the mergeSort function, which can sort the Path.pathcost
 * in a Queue.
 * 
 * @author joshluo
 *
 */
public class Sort{
	public static Queue<Path> mergeSort(Queue<Path> paths){
		Queue<Path> first = new Queue<Path>();
		Queue<Path> second = new Queue<Path>();
		if(paths.size() < 2) return paths;
		for(int i = 0; i < paths.size() / 2; i++)
			first.enqueue(paths.get(i));
		for(int i = paths.size() / 2; i < paths.size(); i++)
			second.enqueue(paths.get(i));
		first = mergeSort(first);
		second = mergeSort(second);
		return merge(first, second);
	}
	
	public static Queue<Path> merge(Queue<Path> first, 
			Queue<Path> second){
		Queue<Path> result = new Queue<Path>();
		int i = 0;
		int j = 0;
		while(i < first.size() && j < second.size()){
			if(first.get(i).pathCost <= second.get(j).pathCost){
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
