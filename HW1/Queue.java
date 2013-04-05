import java.util.LinkedList;

/**
 * The self-designed QUEUE will have the basic queue functions. Including 
 * enqueue(), dequeue(), isEmpty(), peek(), size(), get(index).
 * @author joshluo
 *
 * @param <T>
 */
public class Queue<T>{
	public LinkedList<T> queue;
	public Queue(){
		queue = new LinkedList<T>();
	}
	public void enqueue(T e){
		queue.add(e);
	}
	public T dequeue(){
		return queue.removeFirst();
	}
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	public T peek(){
		return queue.peek();
	}
	public int size(){
		return queue.size();
	}
	public T get(int index){
		return queue.get(index);
	}
}