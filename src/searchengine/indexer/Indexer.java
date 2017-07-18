/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Modified by Mahender on 12-10-2009
 */ 

package searchengine.indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import searchengine.dictionary.AVLDictionary;
import searchengine.dictionary.BSTDictionary;
import searchengine.dictionary.DictionaryInterface;
import searchengine.dictionary.HashDictionary;
import searchengine.dictionary.ListDictionary;
import searchengine.dictionary.MyHashDictionary;
import searchengine.dictionary.ObjectIterator;
import searchengine.element.PageElementInterface;
import searchengine.element.PageWord;

import searchengine.dictionary.*;
/**
 * Web-indexing objects.  This class implements the Indexer interface
 * using a list-based index structure.

A Hash Map based implementation of Indexing 

 */
public class Indexer implements IndexerInterface
{
	/** The constructor for ListWebIndex.
	 */

	// Index Structure 
	DictionaryInterface index;

	// This is for calculating the term frequency
	HashMap<Object,Object> wordFrequency;

	public Indexer(String mode)
	{
		// hash - Dictionary Structure based on a Hashtable or HashMap from the Java collections 
		// list - Dictionary Structure based on Linked List 
		// myhash - Dictionary Structure based on a Hashtable implemented by the students
		// bst - Dictionary Structure based on a Binary Search Tree implemented by the students
		// avl - Dictionary Structure based on AVL Tree implemented by the students

		if (mode.equals("hash")) 
			index = new HashDictionary();
		else if(mode.equals("list"))
			index = new ListDictionary();
		else if(mode.equals("myhash"))
			index = new MyHashDictionary();
		else if(mode.equals("bst"))
			index = new BSTDictionary();
		else if(mode.equals("avl"))
			index = new AVLDictionary();
	}

	/** Add the given web page to the index.
	 *
	 * @param url The web page to add to the index
	 * @param keywords The keywords that are in the web page
	 * @param links The hyperlinks that are in the web page
	 */
	public void addPage(URL url, ObjectIterator<?> keywords)	
	{
	    ////////////////////////////////////////////////////////////////////
	    //  Write your Code here as part of Integrating and Running Mini Google assignment
	    //  
	    ///////////////////////////////////////////////////////////////////

		int freq,k,i=0;
		Vector vec=keywords.returnVec();

			HashMap hm;

			while(vec.size()>0)
			{
				wordFrequency = new HashMap();
				String word=vec.get(0).toString();

				freq=1;
				
				for(i=1;i<(vec.size());i++)
				{
					String compare=vec.get(i).toString();
					if(word.equals(compare))
					{
						freq++;
						vec.remove(i);   
						i--;
					}
				}

				wordFrequency.put(url,freq);

				if(index.getValue(word)!=null)
				{
					hm=(HashMap) index.getValue(word);
					hm.putAll(wordFrequency);
					index.insert(word,hm);
				}

				else
				{
					index.insert(word,wordFrequency);
				}

				vec.remove(0);
			}

	}

	/** Produce a printable representation of the index.
	 *
	 * @return a String representation of the index structure
	 */
	public String toString()
	{
		////////////////////////////////////////////////////////////////////
	    //  Write your Code here as part of Integrating and Running Mini Google assignment
	    //  
	    ///////////////////////////////////////////////////////////////////
		return "You dont need to implement it\n";
	}

	/** Retrieve all of the web pages that contain the given keyword.
	 *
	 * @param keyword The keyword to search on
	 * @return An iterator of the web pages that match.
	 */
	public ObjectIterator<?> retrievePages(PageWord keyword)
	{
		////////////////////////////////////////////////////////////////////
	    //  Write your Code here as part of Integrating and Running Mini Google assignment
	    //  
	    ///////////////////////////////////////////////////////////////////
		Vector v = new Vector();
		Comparable[] keys= index.getKeys();

		for (int i=0;i<keys.length ;i++ )
		{
			if ((keyword.toString()).equals(keys[i]))
			{
				Hashtable ht = (Hashtable)index.getValue(keys[i]);
				
				v.add(ht);
			}
		}
		return new ObjectIterator<PageElementInterface>(new Vector<PageElementInterface>());
	}

	/** Retrieve all of the web pages that contain any of the given keywords.
	 *	
	 * @param keywords The keywords to search on
	 * @return An iterator of the web pages that match.
	 * 
	 * Calculating the Intersection of the pages here itself
	 **/
	@SuppressWarnings("unchecked")
	public ObjectIterator<?> retrievePages(ObjectIterator<?> keywords)
	{

		@SuppressWarnings("unused")
		Vector v = keywords.returnVec();
		String[] ks= (String[]) index.getKeys();
		Vector vv= new Vector();
		while (keywords.hasNext())
		{
			Hashtable hd = new Hashtable();
			String k = keywords.next().toString();
			
			for (int i=0;i<ks.length ;i++ )
			{
				if(k.equals(ks[i]))
				{
					 HashMap vals = (HashMap)index.getValue(ks[i]);
					 URL urls[]= new URL[vals.size()];
					 Set s = vals.keySet();
					 Object [] arr = s.toArray();
				
					 for (int j =0;j<urls.length ;j++ )
					 {   
						 urls[j]=(URL)arr[j];
						 hd.put(urls[j],vals.get(urls[j]));
					 }
				}
			}
			
				Elmnt el = new Elmnt();
				el.word = k;
				el.val = hd;
				vv.add(el);
		}
		return new ObjectIterator(vv);
	}

	/** Save the index to a file.
	 *
	 * @param stream The stream to write the index
	 */
	public void save(FileOutputStream stream) throws IOException
	{
		////////////////////////////////////////////////////////////////////
	    //  Write your Code here as part of Integrating and Running Mini Google assignment
	    //  
	    ///////////////////////////////////////////////////////////////////
			 
			 PrintStream pw = new PrintStream(stream);

			 Comparable[] a= index.getKeys();

			 for (int i=0;i<a.length ;i++ )
			 {
				 HashMap hd = (HashMap)index.getValue((String) a[i]);
				 URL urls[]= new URL[hd.size()];
				 Set s = hd.keySet();
				 Object [] arr = s.toArray();	
				 
				 for (int j =0;j<urls.length ;j++ )
				 {
					 urls[j]=(URL)arr[j];
				 }
				 
				 for (int k=0;k<urls.length ;k++ )
				 {
					 pw.println(a[i]+"\t"+urls[k]+"\t"+hd.get(urls[k]));
				 }
			 }

			 pw.close();
	}

	/** Restore the index from a file.
	 *
	 * @param stream The stream to read the index
	 */
	@SuppressWarnings("unchecked")
	public void restore(FileInputStream stream) throws IOException
	{
		////////////////////////////////////////////////////////////////////
	    //  Write your Code here as part of Integrating and Running Mini Google assignment
	    //  
	    ///////////////////////////////////////////////////////////////////
		Scanner scan = new Scanner(stream);
		String tmp=null;
		String b=null;
		int c=0;
		
		while (scan.hasNext())
		{
			 String a = scan.next();
			 b= scan.next();
			 
			 if(!scan.hasNextInt())
			 {
				c=Integer.parseInt(b.toString());
				System.out.println(a);
				b=a;
				a="";
			 }

			 if(scan.hasNextInt())
				 					c= scan.nextInt();
			
			URL u = new URL(b);
			HashMap nhm = new HashMap();
			nhm.put(u,c);
			
			if (index.getValue(a)!=null)
			{
				HashMap tmpmap = (HashMap)index.getValue(a);
				nhm.putAll(tmpmap);
			}
			
			index.insert(a,nhm);
		}

		save(new FileOutputStream(new File("newfile.txt")));

	}

	public void removePage(URL url)
	{
	}
};