package CSC017_Lab_04;

/*
This interface is implemented by objects that can be inserted into a
Flexible Priority Queue (Binary Heap) data structure.  These values are 
aware of the index where they're stored at in the underlying array of the
heap.  It is up to the programmer to dynamically check that, if H is
the heap array, then H[i].getIndex()==i. Given a HeapValue object x, 
if x.getIndex()== -1, then the index is invalid and must be ignored. 
Otherwise it must hold that H[x.getIndex()] == x, else the index of x 
is invalid.

The purpose of these operations is to support the ability to 'requeue' an
object when its priority changes.  But don't confuse the index with the
priority, which is to be determined by the required compareTo method that
this interface inherits from the Comparable interface.
*/

public interface HeapValue<T> extends Comparable<T>
{
    int getIndex();
    void setIndex(int x);
    // int compareTo(T x); // inherited from Comparable<T>
}


// A sample class that implements HeapValue.  Imagine students in 
// competition for a scholarship are ranked by their GPAs.

class scholar implements HeapValue<scholar>
{
    public final String name;
    protected float gpa; // with up to 3 digits of precision
    public scholar(String n, float g) // constructor
    {
       if (n==null || g<0 || g>4.0) {
         n="";  g=0;  
	 System.err.println("invalid scholar, creating object with default values");
       }
       name=n;  gpa=g;
    }//constructor

    public void updateGPA(float g)  // OOPS, the "priority" can change!
    {
	if (g>=0 && g<=4.0) gpa = g;
    }

    // for Comparable interface
    public int compareTo(scholar other)
    {
	return (int)(gpa*100+0.5) - (int) (other.gpa*100+0.5);
    }

    // for HeapValue interface
    private int hi = -1; // heap index, -1 means index invalid
    public int getIndex() { return hi; }
    public void setIndex(int n) { if (n>=0) hi=n; }
  
}//student scholar  class

// scholar objects are now ready to be inserted into a flex-priority queue