package testcase;

import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.StdOut;
import priority_queuse.IndexMinPQ;

public class Multiway {
	//this class should not be instantiated
	private Multiway() {}
	
 //merge together the sorted input streams and write the sorted results to standard output
	private static void merge(In[] streams){
		int n=streams.length;
		IndexMinPQ<String> pq=new IndexMinPQ<String>(n);
		for(int i=0;i<n;i++)
			if(!streams[i].isEmpty())
				pq.insert(i, streams[i].readString());
		
		//Extract and print min  and read next from its stream
		while(!pq.isEmpty()){
			StdOut.println(pq.minKey()+" ");
			int i=pq.delMin();
			if(!streams[i].isEmpty())
				pq.insert(i, streams[i].readString());
		}
		StdOut.println();
	}
	

    /**
     *  Reads sorted text files specified as command-line arguments;
     *  merges them together into a sorted output; and writes
     *  the results to standard output.
     *  Note: this client does not check that the input files are sorted.
     *
     * @param args the command-line arguments
     */
	

	public static void main(String[] args) {
		int n=args.length;
		In[] streams=new In[n];
	
		for(int i=0;i<n;i++)
			streams[i]=new In(args[i]);
		merge(streams);
	}

}

