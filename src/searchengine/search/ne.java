package searchengine.search;

import java.util.*;

class ne
{
	Url out[];
	int  els;
	int maxSize;
	ne(Vector v)
	{
		out= new Url[v.size()];
		els=0;
		maxSize = v.size();
	}

	boolean isEmpty()
	{
		return (els==0);
	}
	
	Url[] insert(Url u)
	{
		if(els==maxSize)
		{
			System.out.println("Queue is filled");
			return out;
		}
		out[els]=u;
		siftUp(els++);
		return out;
	}
	void siftUp(int index){
		int parent = (index -1)/2;
		Url bottom =out[index];
		while (index>0&& out[parent].rank<bottom.rank)
		{
			 out[index]=out[parent];
			 index = parent;
			 parent = (parent-1)/2;
		}
		out[index]=bottom;
	}

	Url remove(){
		Url nod = out[0];
		out[0]=out[--els];
		siftDown(0);
		return nod;
	}
		
	void siftDown(int a){
		int high;
		Url top = out[a];
		while (a<els/2)
		{
			int lc = 2*a+1;
			int rc = lc+1;
			if (rc<els&&out[lc].rank<out[rc].rank)
			{
				high = rc;
			}
			else
				high = lc;
			if(top.rank>=out[high].rank)
				break;
			out[a]=out[high];
			a=high;
		}
		out[a]=top;
	}
}