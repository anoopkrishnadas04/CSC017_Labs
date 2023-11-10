package CSC017_Lab_03_v2;

/*
This program implements a circular queue, which uses an array underneath. It
can be used to implement stack (lifo) or queue (fifo) data structures.  This
program will also illustrate the uses of interfaces, generics, and inheritance.

This program also introduces the concept of "Amalgamated Time Complexity",
which is the average time over a number of calls (as opposed to average over
the form of data).  The worst-case time of inserting a value at the front or
back of the queue is O(n), but over n calls, the time is still O(n), so the
"amalgated" time complexity of the operation is O(1).

The advantages of this approach, compared to a linked list implementation using
pointers to the next cell is that:

1.  Accessing the ith element becomes O(1) instead of O(n)
2.  Deleting the last cell becomes O(1) instead of O(n) in singly linked lists
3.  Binary search becomes feasible, with O(log n) time on sorted queues.
4.  It's faster to allocate a large amount of memory all at once compared
    to a few bytes at a time.

The potential disadvantage of this approach is not knowing how much
capacity to allocate.  But careful analysis (and experimentation) show
that dynamically resizing and copying the queue generates an
acceptable overhead.  If we double the size of the array each time it
needs to be resized, then the amount of copying we need to do is still
linearly proportional to n, the number of values in the queue.  For
example, if n=128 and the array initially has a capacity of one, then
resizing the queue requires creating and copying arrays of sizes:

   1 + 2 + 4 + 8 + 16 + 32 + 64 + 128 = 255 = 2n-1

Inserting n values into a queue, even if starting with a capacity of 1,
still takes time linearly proportional to n.

*/
import java.util.Iterator;  // interface enables for loops
import java.util.Optional;

public class CircQueue<T> implements Iterable<T>, Queue<T>
{
    protected T[] A; // underlying array representing the queue
    protected int front; // index of first value in queue
    protected int size;  // number of values in queue, not same as A.length
    public int size() {return size;} // readonly accessor for size.
    public int capacity() { return A.length;}

    protected int ri(int i)    // real index of virtual index i:
    {
        return (front+i)%A.length;
    }

    protected int lasti() // real index of last value, if it exists  O(1)
    {
	return (front+size-1)%A.length;
    }
    protected int right(int i) // real index to "right" of real index i
    { return (i+1)%A.length; }

    protected int left(int i) // index of cell to the "left" of index i O(1)
    {
        return (i+A.length-1) % A.length;
    }

    protected int translate_index(int i) { return (front+i)%A.length; }

    //// this function is needed because of a quirk of java generics, and
    //  should be @Overridden in subclasses where type T is further constrained
    @SuppressWarnings("unchecked")
    protected T[] makearray(int cap) // create a generic array of size cap
    {
        return (T[]) new Object[cap]; // will normally get compiler warning
    }

    // constructor
    public CircQueue(int cap) // create a queue with an initial capacity
    {
        if (cap<1) cap=16;
	// A = new T[cap]; // won't compile in Java
	//A = (T[]) new Object[cap]; // compiles with compiler warning
        A = makearray(cap);
	front = 0;
	size = 0;
    }//constructor

    public CircQueue(T[] B) { A = B; front=0; size=0; } //alternate constructor

    public void resize() // doubles size, copy
    {
	T[] B = makearray(A.length*2); //(T[]) new Object[A.length*2];
	for(int i=0;i<size;i++)  B[i] = A[(front+i)%A.length];
	A = B; // new array now represents queue
	front = 0;
    }//resize
    public void doublesize() // alias
    {
        resize();
    }
    public int shrink() // shrink capacity to 50% full, return amount shrunk
    {
        // want size/capacity==.5 so target capacity = size/.5 = 10*size/5
        int targetcap = (1000*size)/500;
        if (A.length <= targetcap) return 0; // not possible to shrink
	T[] B = makearray(targetcap);
	for(int i=0;i<size;i++)  B[i] = A[(front+i)%A.length];
	int answer = A.length - targetcap; // amount shrunk
        A = B;
        front = 0;
        return answer;
    }

    // insert x into front of queue (push)   O(1)
    // this function returns a pointer to the queue so other ops can follow
    public CircQueue<T> push(T x) 
    {   if (x==null) return this; //ignores null input
	if (size >= A.length) resize();
	// special case: front not valid if size is 0
	if (size>0) front = left(front);
	A[front] = x;
	size++;
	return this;
    }

    // delete and return from front of queue  O(1)
    public Optional<T> pop()
    {
	if (size<1) return Optional.empty();  // replaces null
	size--;
	var answer = Optional.of(A[front]);
        A[front] = null; // helps garbage collector, still need null here
	front = right(front);
	return answer;
    }
    public Optional<T> peek() // return first value without delete, O(1)
    {
	if (size<1) return Optional.empty();
	else return Optional.of(A[front]);
    }
    public Optional<T> first() { return peek(); } // alias for peek

    // insert into back of queue (enqueue)  O(1)
    public CircQueue<T> enqueue(T x)
    {   if (x==null) return this;
	if (size>=A.length) resize();
	int nexti = (front+size)%A.length; // if size==0, nexti==front
	A[nexti] = x;
	size++;
	return this;
    }

    // delete from back of queue  O(1) - constrast with singly linked list
    public Optional<T> dequeue()
    {
	if (size<1) return Optional.empty();
	var answer = Optional.of(A[lasti()]);
        A[lasti()] = null; // informs garbage collector
	size--;
	return answer;
    }
    public Optional<T> last() // returns last value value in list, O(1)
    {
	if (size<1) return Optional.empty();
	else return Optional.of(A[lasti()]);
    }

    // additional functions not in interface:

    // finding the nth value from the front, 0th = first
    public Optional<T> getnth(int n) // returns nth value, O(1)
    {
	if (n>=size || n<0) {
	    System.err.println("value doesn't exist at "+n); // error output
	    return Optional.empty();
	}
	else return Optional.of(A[(front+n)%A.length]);
    }

    // sets the nth value from front to x, return previous value  O(1)
    public Optional<T> setnth(int n, T x)
    {
        if (n>=size || n<0 || x==null) {
            System.err.println("value doesn't exist");
            return Optional.empty();
        }
        int ni = (front+n)%A.length; // index of nth value
        T ax = A[ni];
        A[ni] = x;
        return Optional.of(ax);
    }

    public boolean contains(T x) // search for x using .equals, O(n) ***
    {   if (x==null) return false;
	for (int i=0;i<size;i++) 
	    if (x.equals(getnth(i))) return true;
	return false;
    } // Contains, can be improved ...

    // delete ith item, shift over others, returns deleted value, O(n)***
    public Optional<T> deleteAt(int i) 
    {
        if (i<0 || i>=size) return Optional.empty();
        if (i==0) return pop();
        if (i==size-1) return dequeue();
        // now can assume i is somewhere in the middle...
        int ri = (front+i)%A.length; // real index of ith item
        T answer = A[ri];
        if (i < size/2) { // shift front elements to the right
            for(int k=i;k>0;k--)
                A[(front+k)%A.length] = A[(front+k-1)%A.length];
            A[front] = null; // tell garbarge collector OK to collect
            front = right(front);
        }
        else  { // shift last elements left
            for(int k=i;k<size-1;k++)
                A[(front+k)%A.length]=A[(front+k+1)%A.length];
            A[(front+size-1)%A.length] = null;
        }
        size--;
        return Optional.of(answer);
    }//deleteAt



    // for DEBUGGING PURPOSES:
    void printinfo()
    {
	System.out.println("Internal array:");
	for(int i=0;i<A.length;i++) System.out.print(i+":"+A[i]+"--");
	System.out.println();
	System.out.printf("front==%d, last==%d\n",front,lasti());
    }
    
    /////////////// For Iterator and Iterable interfaces...

    class Qiterator implements Iterator<T>  // internal class has access
    {                                       // to front, size, etc
	private int i = 0;
	//public Qiterator() {} // default constructor
	public boolean hasNext()
	{
	    return i<size;
	}
	public T next()
	{   
            i++;
	    return A[(front+i-1)%A.length];
	}
    }//Qiterator

    public Iterator<T> iterator() // required by Iterable interface
    {
	return new Qiterator();
    }

    // main is only for testing
    public static void main(String[] av)
    {
	CircQueue<Integer> Q = new CircQueue<Integer>(1);
	for(Integer i=2;i<16;i+=2) {Q.push(i); Q.enqueue(i+1); }
	Q.dequeue();
	Q.pop()
	    .ifPresent(x -> System.out.println(x+" was popped from queue"));
	Q.printinfo();        
	for(Integer x:Q) System.out.println(x);
        Q.deleteAt(3);
        Q.deleteAt(9);
	Q.printinfo();
    }
}//CircQueue


