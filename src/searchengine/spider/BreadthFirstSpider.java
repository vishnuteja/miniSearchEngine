/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Modified by Mahender on 12-10-2009
 */
package searchengine.spider;

import java.io.IOException;
import java.net.*;
import java.util.*;

import searchengine.dictionary.DictionaryDriver;
import searchengine.dictionary.ObjectIterator;
import searchengine.element.PageElementInterface;
import searchengine.element.PageHref;
import searchengine.element.PageWord;
import searchengine.indexer.Indexer;
import searchengine.parser.PageLexer;
import searchengine.url.URLTextReader;
class Queue1
{
	Qnode front=null;
	Qnode rear=null;
	void enQueue(Qnode n){
		if (front==null)
		{
			front=n;
		}else{
			Qnode current= front;
			while (current.next!=null)
			{
				current=current.next;
			}
			current.next=n;
			rear=n;
		}
	}
	void deQueue(){
		if (front!=null)
		{
			if (front.next!=null)
			{
				System.out.println("---DeQueuing---:"+front.element);
				front=front.next;
			}
			else{
				front=null;
			}
		}
	}
	
	void display()
	{
		Qnode current=front;
		
		while (current!=null&&current.next!=null)
		{
			System.out.println("\t\tReq ID: "+current.element);
			current=current.next;
		}
		
		if (current!=null)
		{
			System.out.println("\t\tReq ID:"+current.element);
		}
	}
	
	boolean contains(String a)
	{
		Qnode current = front;
	
		while (current!=null)
		{
			if (current.element.equals(a))
			{
				return true;
			}
			current= current.next;
		}
		
		return false;
	}
	
	int size()
	{
		Qnode curr= front;
		int i=0;
		while (curr!=null)
		{
			i++;
			curr=curr.next;
		}
		return i;
	}
	
}
class Qnode
{
	String element;
	Qnode next;
	Qnode(String e)
	{
		element = e;
	}
}

/** Web-crawling objects.  Instances of this class will crawl a given
 *  web site in breadth-first order.*/

public class BreadthFirstSpider implements SpiderInterface {

	/** Create a new web spider.
	@param u The URL of the web site to crawl.
	@param i The initial web index object to extend.
	 */

	private Indexer i = null;
	private URL u; 
	Queue1 q = new Queue1();
	public BreadthFirstSpider (URL u, Indexer i) {
		this.u = u;
		this.i = i;
	}

	/** Crawl the web, up to a certain number of web pages.
	@param limit The maximum number of pages to crawl.
	 */
	public Indexer crawl (int limit) {
	
		////////////////////////////////////////////////////////////////////
	    //  Write your Code here as part of Breadth First Spider assignment
	    //  
	    ///////////////////////////////////////////////////////////////////
		System.out.println("Crawling Webpage ---------"+u);
		int flag=0;
		
		 Vector vec = new Vector();
		 Vector ext = new Vector();
    	
		 try
		{
			URLTextReader in = new URLTextReader(u);
			PageLexer pl = new PageLexer(in, u);
			int count = limit;
			while (pl.hasNext()) 
			{
				PageElementInterface pe = (PageElementInterface)pl.next();

				String std = pe.toString();
				
				if (pe instanceof PageHref&&!pe.toString().contains("#")&&count!=0)
				{
					int f = 0;
					String str=pe.toString();
					URL url= new URL(str);
					Qnode n = new Qnode(str);
					
					for (int i =0; i<ext.size();i++ )
					{
						if (ext.get(i).toString().equals(str))
						{
							f=1;
						}
					}
					
					if (f ==0)
					{
						q.enQueue(n);
						ext.add(n);
						
						count--;
					}
					
				}
				
				else if(pe instanceof PageWord)
				{
						String string = pe.toString();
						if(string.startsWith("<"))
						{
							flag=1;
						}
						
						if(string.endsWith(">"))
						{
							flag=0;
						}
						
						if(!string.endsWith(">")&&flag==0&&!string.contains("\t")&&!string.contains(" "))
						{
						vec.add(pe.toString());
						}
				}
				
			}
			
			ObjectIterator oi = new ObjectIterator(vec);
			i.addPage(u,oi);
			
			if(q.size()!=0)
			{
				u = new URL(q.front.element);
				q.deQueue();
				System.out.println("*************The Queue Contains***************");
				q.display();
			
				limit--;
				System.out.println("Current Limit "+limit);
			
				if(limit>0)
					crawl(limit);
			}
			
		}
		
		catch(MalformedURLException e)
		{
			
		}
		
		catch(IOException e)
		{
			try
			{
				System.out.println("IO exception thrown");
				
				if(q.front!=null)
				{
					u = new URL(q.front.element);
					q.deQueue();
					System.out.println("*************The Queue Contains***************");
					q.display();
					System.out.println(limit);
					 if(limit>0)
						 crawl(limit);
				}
			}
			
			catch(MalformedURLException e1)
			{
				
			}
			
		}
		
		catch(Exception e)
		{
			System.out.println("Exception thrown");
		}
    	
		return i;
	}

	/** Crawl the web, up to the default number of web pages.
	 */
	public Indexer  crawl()
	{
		// This redirection may effect performance, but its OK !!
		System.out.println("Crawling: "+u.toString());
		return  crawl(crawlLimitDefault);
	}

	/** The maximum number of pages to crawl. */
	public int crawlLimitDefault = 3;

}