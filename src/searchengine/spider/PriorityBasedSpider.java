/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Created by Mahender on 12-10-2009
 */
package searchengine.spider;

import java.net.*;

import searchengine.indexer.Indexer;

/** Web-crawling objects.  Instances of this class will crawl a given
 *  web site in Priority-first order.
 */
public class PriorityBasedSpider implements SpiderInterface {

	/** Create a new web spider.
	@param u The URL of the web site to crawl.
	@param i The initial web index object to extend.
	 */

	private Indexer i = null;
	private URL u; 

	public PriorityBasedSpider (URL u, Indexer i) {
		this.u = u;
		this.i = i;

	}

	/** Crawl the web, up to a certain number of web pages.
	@param limit The maximum number of pages to crawl.
	 */
	public Indexer crawl (int limit) {
	
		////////////////////////////////////////////////////////////////////
	    //  Write your Code here as part of Priority Based Spider assignment
	    //  
	    ///////////////////////////////////////////////////////////////////
		
		return i;
	}

	/** Crawl the web, up to the default number of web pages.
	 */
	public Indexer  crawl() {
		// This redirection may effect performance, but its OK !!
		System.out.println("Crawling: "+u.toString());
		return  crawl(crawlLimitDefault);
	}

	/** The maximum number of pages to crawl. */
	public int crawlLimitDefault = 10;

}
