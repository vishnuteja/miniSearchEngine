/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Modified by Mahender K on 12-10-2009
 */ 


package searchengine.search;


import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import searchengine.dictionary.ObjectIterator;
import searchengine.indexer.Elmnt;
import searchengine.indexer.Indexer;


/**
 * The user interface for the index structure.
 *
 * This class provides a main program that allows users to search a web
 * site for keywords.  It essentially uses the index structure generated
 * by WebIndex or ListWebIndex, depending on parameters, to do this.
 *
 * To run this, type the following:
 *
 *    % java SearchDriver indexfile list|custom keyword1 [keyword2] [keyword3] ...
 *
 * where indexfile is a file containing a saved index and list or custom indicates index structure.
 *
 */

class Url
{
	String url;
	int freq;
	double rank;
	int nums=0;
	Url(String a, int b, double c){
		url = a;
		freq = b;
		rank = c;
	}
}

public class SearchDriver
{
    public static Object[] main(Object dict,String w1,String w2) throws MalformedURLException
    {
        String result[] = null;
    	Indexer w = null;
        Object[] array;
        String dictType=dict.toString();
        
        Vector v=new Vector();
		Vector urlDetails = new Vector();
	//if(args.length<3)
	  //  System.out.println("Usage: java SearchDriver indexfile list|hash keyword1 [keyword2] [keyword3] [...]");
	//else
	    {
		
		
		// Take care to use the right usage of the Index structure
		// hash - Dictionary Structure based on a Hashtable or HashMap from the Java collections 
		// list - Dictionary Structure based on Linked List 
		// myhash - Dictionary Structure based on a Hashtable implemented by the students
		// bst - Dictionary Structure based on a Binary Search Tree implemented by the students
		// avl - Dictionary Structure based on AVL Tree implemented by the students
		if(dictType.equalsIgnoreCase("list") || dictType.equals("hash") || dictType.equals("myhash") || dictType.equals("bst")
				|| dictType.equals("avl")){
		    w = new Indexer(dictType);
		}
		else
		{
			System.out.println("Invalid Indexer mode \n");
		}
		
		try{
		    FileInputStream indexSource=new FileInputStream("index.txt");
		    w.restore(indexSource);//calls restore()
		}
		catch(IOException e){
		    System.out.println(e.toString());
		}
		
		//for(int i=2;i<args.length;i++)
		 // if(!w1.equals(" "))
                      v.addElement(w1);
                if(!w2.equals(" "))
                	v.addElement(w2);
		
		ObjectIterator<?> i= w.retrievePages(new ObjectIterator<String>(v));
		
		if(i!=null)
		{
			while(i.hasNext())//next element
			{

				Elmnt elm = (Elmnt)i.next();
				Hashtable htble = (Hashtable)elm.val;
				String ks[]= new String[htble.size()];
				Enumeration e= htble.keys();
				for (int j=0;j<ks.length ;j++ )
				{
					ks[j]=e.nextElement().toString();
					int f= (Integer)htble.get(new URL(ks[j]));
					System.out.println("URL :"+ks[j]+"\tFrequency"+f);
					String depth[]= ks[j].split("/");
					double pr = pagerank(f,(depth.length-2));
					Url url = new Url(ks[j],f,pr);
					urlDetails.add(url);
				}
			}
		System.out.println("complete====================");
			
		Url arr[]= new Url[urlDetails.size()];
		for (int h=0;h<arr.length ;h++ )
		{
			arr[h]=(Url)urlDetails.get(h);
		}
		Hashtable htble = new Hashtable();
		int add=0;

		for (int h=0;h<arr.length ;h++ )
		{
			if (htble.containsKey(arr[h].url))
			{
				Url uu = (Url)htble.get(arr[h].url);
				int fr = uu.freq+arr[h].freq;
				int nums=uu.nums+1;
				uu.freq = fr;
				if(fr>add)
					add=fr;
				uu.nums=nums;
				htble.put(arr[h].url,uu);
			}
			
			else
			{
				htble.put(arr[h].url,arr[h]);
			}
		}

		Enumeration e = htble.keys();
		System.out.println(add);
		String string[]= new String[htble.size()];
		Url[] input = new Url[string.length];
		ne p = new ne(urlDetails);
		Url[] output = new Url[string.length];
		
		for (int h=0;e.hasMoreElements();h++ )
		{
			string[h]=e.nextElement().toString();
			Url uu =(Url) htble.get(string[h]);
			String r[]= uu.url.split("/");
			System.out.println("\tdepth\t"+(r.length-2)+"\n\t"+uu.url+"\n\tRank\t"+uu.rank+"\n\tfrequency\t"+uu.freq+"\nnums\t  "+uu.nums);
			uu.freq=uu.freq+uu.nums*add;
			String dep[]= uu.url.split("/");
			uu.rank=pagerank(uu.freq,dep.length-2);
			input[h]=uu;
			System.out.println("\tdepth\t"+(r.length-2)+"\n\t"+uu.url+"\n\tRank\t"+uu.rank+"\n\tfrequency\t"+uu.freq+"\n\t  "+uu.nums);				
			p.insert(uu);
		}
		
		System.out.println("Final List\t:\n-------------");
		
		int j=0;
		result = new String[output.length];
		int count = 0;
		
		while (!p.isEmpty())
		{
			output[j]=p.remove();
			System.out.println("\t\t\tURL \t"+output[j].url+"\tFrequency \t"+output[j].rank);
			result[count] =output[j].url;
			count++;
		}
			
			System.out.println("Search complete.");
			System.out.println("---------------\n");			
		}
		else
		{
			System.out.println("XXXXXXXXXXXXXX No Results Found XXXXXXXXXXXXXX");
		}
		
	    }

	    return result;
    
    }

    static double pagerank(int a, int b)
	{
		return (a*1/b*5);
	}
};
