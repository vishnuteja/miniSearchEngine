/**  
 *  
 * This program is part of an implementation for the Mini-Google project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Created by Mahender K on 12-10-2009
 */

package searchengine.dictionary;

public interface PQueueADT <E extends Comparable<E>> {
	
	/*
	 * Inserts the element in the priority Queue
	 */
	public void enqueue(E value);
	
	/*
	 * Deletes the Priority(first element) element from the Queue.
	 */
	public E dequeue();
	
	/*
	 * Returns the number of elements of the Queue
	 */
	public int size();
	
	/*
	 * Returns true if Queue is empty and false otherwise. 
	 */
	public boolean is_empty();
	
	/*
	 * Returns the Priority(first element) element of the Queue 
	 */
	public E front();
	
}
