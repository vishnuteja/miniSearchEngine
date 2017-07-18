package searchengine.dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListDictionary <K extends Comparable<K>, V> implements DictionaryInterface <K,V>
{
	 private int N;           // number of key-value pairs
     private Node first;      // the linked list of key-value pairs

     class Node
     {
	    K key;
	    V value;
	    Node next;

	    Node(K key, V value, Node next)
	    {
	        this.key   = key;
	        this.value = value;
	        this.next  = next;
	    }
     }
	
	
	@Override
	public K[] getKeys()
	{
		int count=0;
		for (Node x = first; x != null; x = x.next)
		{
            count++;
        }
		
		String[] temp=new String[count];
		int i=0;
		for (Node x = first; x != null; x = x.next)
		{
            temp[i]=(String) x.key;
            i++;
        }
		return (K[]) temp;
	}

	@Override
	public V getValue(K str)
	{
		 for (Node x = first; x != null; x = x.next)
		 {
	            if (str.equals(x.key)) return x.value;
	     }
	        return null;     // not found
	}

	@Override
	public void insert(K key, V value)
	{
		for (Node x = first; x != null; x = x.next)
		{    
			if (key.equals(x.key)) 
            {
            	x.value = value; return;
            }
		}
		
		first = new Node(key, value, first);
        N++;
	}

	@Override
	public void remove(K key) {

		Node Temp=first;


		while(Temp!=null)
		{

		if(Temp.key.equals(key))
		{
		first=first.next;
		break;

		}
		else
		{
		if(Temp.next.key.equals(key))
		{

		//K keyt = (K) Temp.next.key;
		Temp.next = Temp.next.next;
		//size--;
		break;

		}
		Temp = Temp.next;

		}
		}

		}

}
