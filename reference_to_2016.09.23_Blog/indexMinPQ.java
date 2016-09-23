package priority_queuse;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
	private int maxN;                                                                                                //maximum number of elements on PQ
	private int n;													//number of elements on PQ
	private int[] pq;											        //binary heap using 1-based indexing
	private int[] qp;											       //inverse of pq---qp[pq[i]]=pq[qp[i]]=i
    private Key[] keys;										                        //keys[i]=piority of i
     
     public IndexMinPQ(int maxN){
    	 if (maxN < 0) throw new IllegalArgumentException();
         this.maxN = maxN;
         n=0;
    	 keys=(Key[]) new Comparable[maxN+1];
    	 pq=new int[maxN+1];
    	 qp=new int[maxN+1];
    	 for(int i=0;i<=maxN;i++)
    		 qp[i]=-1;
     }
     
     public boolean isEmpty(){
    	 return n==0;
     }

     public boolean contains(int k){
    	 if (k < 0 || k >= maxN) throw new IndexOutOfBoundsException();
    	 return qp[k]!=-1; 	 
     }
     
     public int size(){
    	 return n;
     }
     
     public void insert(int i,Key key){
    	 if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
    	 if(contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
    	 n++;
    	 qp[i]=n;
    	 pq[n]=i;
    	 keys[i]=key;
    	 swim(n);
     }
     
     public int minIndex(){
    	 if(n==0) throw new NoSuchElementException("Piority queue underflow");
    	 return pq[1];
     }
     
     public Key minKey(){
    	 if(n==0) throw new NoSuchElementException("Piority queue underflow");
    	 return keys[pq[1]];
     }
     
     public int delMin(){
    	 if(n==0) throw new NoSuchElementException("Piority queue underflow");
    	 int min=pq[1];
    	 exch(1,n--);
    	 sink(1);
    	 assert min == pq[n+1];
    	 qp[min]=-1;;                                     //delete
    	 keys[min]=null;                           //to help with garbage collection
    	 pq[n+1]=-1;;
    	 return min;
    	 
     }
    	public void changeKey(int i,Key key){
    		if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
    		if(!contains(i)) throw new NoSuchElementException("index is not in the piority queue");
    		keys[i]=key;
    		swim(qp[i]);
    		sink(qp[i]);
    	}
     
    	public void delete(int i){
    		if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
    		if(!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
    		int index=qp[i];
    		exch(index,n--);
    		swim(index);
    		sink(index);
    		keys[i]=null;
    		qp[i]=-1; 		
    	}
    	
    	/******************************************************8
    	 * General helper functions
    	 * @param args
    	 */
    	private boolean greater(int i,int j){
    		return keys[pq[i]].compareTo(keys[pq[j]])>0;
    	}
    	
    	private void exch(int i,int j){
    		int swap=pq[i];
    		pq[i]=pq[j];
    		pq[j]=swap;
    		qp[pq[i]]=i;
    		qp[pq[j]]=j;
    	}
    	
    	/********************************
    	 * Heap helper functions
    	 * 
    	 * @param args
    	 */
    	private void swim(int k){
    		while(k>1&&greater(k/2,k)){
    			exch(k/2,k);
    			k=k/2;
    		} 		
    	}
    	private void sink(int k){
    		while(2*k<n){
    			int j=2*k;
    			if(j<n&&greater(j,j+1)) j++;
    			if(!greater(k,j)) break;
    			exch(k,j);
    			k=j;
    		}
    	}
    	
    	@Override
		public Iterator<Integer> iterator() {
			// TODO Auto-generated method stub
			return new HeapIterator();
		}
    	private class HeapIterator implements Iterator<Integer>{

    		//create a new pq
    		private IndexMinPQ<Key> copy;
    		
    		//add all elements to copy of heap
    		//takes linear time since already in heap order so no keys move
    		public HeapIterator() {
				copy=new IndexMinPQ<Key>(pq.length-1);
				for(int i=1;i<=n;i++)
					copy.insert(pq[i], keys[pq[i]]);
			}
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return !copy.isEmpty();
			}

			@Override
			public Integer next() {
				// TODO Auto-generated method stub
				if(!hasNext()) {throw new NoSuchElementException();}
				return copy.delMin();
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
				throw new UnsupportedOperationException();
			}
    		
    	}
    	
    	//Unit Test
    	
	public static void main(String[] args) {
		//insert a bunch of strings
		String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };
		
		IndexMinPQ<String> pq=new IndexMinPQ<String>(strings.length);
		for(int i=0;i<strings.length;i++){
			pq.insert(i, strings[i]);
             }
		
		//print each key using iterator
		for(int i:pq){
			StdOut.println(i+" "+strings[i]);
		}
		
		StdOut.println();

	}

		

 }

