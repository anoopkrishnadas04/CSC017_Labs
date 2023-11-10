package CSC017_Lab_03;

import java.util.Iterator;
import java.util.Optional;

import javax.print.attribute.SetOfIntegerSyntax;

public class CmpCQ<T extends Comparable<T>> extends CircQueue<T> implements CmpQueue<T>{
    
    //NEEDS to be in Ascending Order


    protected boolean sorted = true;

    @Override
    public boolean is_sorted(){return sorted;}

    @Override
    public CmpCQ<T> push(T x) //assuming CmpCQ is name of your class
    {
        super.push(x); // invokes inherited version of push
        if(size > 1 && A[front].compareTo(A[right(front)]) > 0) sorted = false;
        /*
        if (size>1 && A[front].compareTo(A[(front+1) % A.length])>0)
            sorted = false;
        */
        return this;
    }//push()

    //POSSIBLE ISSUE: lasti() is the real index of the last value
    @Override
    public CmpCQ<T> enqueue(T x)
    {
        super.enqueue(x); //invokes inherited version of enqueue
        if(size > 1 && A[lasti()].compareTo(A[left(lasti())]) < 0) sorted = false;
        /*
        if(size > 1 && A[lasti()].compareTo(A[left(lasti())]) < 0)
            sorted = false;
        */
        return this;
    }//enqueue()

    @Override
    public Optional<T> setnth(int n, T x){
        
        Optional<T> nValOpt = super.setnth(n, x);
        
        //Checks if list is now unsorted
        nValOpt.ifPresent(nVal -> { //If Value Exists, 
            Optional<T> nRightOpt = getnth(right(n));
            Optional<T> nLeftOpt = getnth(left(n));

            nLeftOpt.ifPresent(nLeft -> {
                if((nVal).compareTo(nLeft) < 0)
                    sorted = false;
            });

            nRightOpt.ifPresent(nRight -> {
                if((nVal).compareTo(nRight) > 0)
                    sorted = false;
            });
        });
        return nValOpt;
    }//setnth()

    
    public int search(T x){
        if(is_sorted()) return runBinarySearch(x);
        return runLinearSearch(x);
    }
    public int runBinarySearch(T x){
        //TO DO: Fill out based on Binary Search Algorithm
        int first = front;
        int last = lasti();
        int mid = -1;
        while(first <= last){
            mid = getMid(first, last);
            int compVal = (x).compareTo(A[mid]);
            if(compVal == 0) return mid;
            if(compVal < 0){
                last = left(mid);
            }
            else{
                first = right(mid);
            }
        }
        return -1;
    }
    //Check the getMid function
    public int getMid(int first, int last){return (first + last)/2;}

    public int runLinearSearch(T x){
        //TO DO: Fill out based on Linear Search Algorithm
        
        
        if(size < 1) return -1;
        
        /*
        for(int i = front; i <= lasti(); i = right(i)){
            if(x.compareTo(A[i]) == 0) return i;
            
            Optional<T> currPosOpt = getnth(i);
            currPosOpt.ifPresent((currPos) -> {
                if(currPos.compareTo(x) == 0)
                    valNotFound = false;
            });
            
        }
        return -1;
        */

        int i = front;
        while(i < size){
            if(A[i] != null && A[i].compareTo(x) == 0){
                return i;
            }
            i = right(i);
        }
        return -1;
    }

    
    public int insert_sorted(T x){
        if(!is_sorted()){
            System.out.println("REACHED LINE 124");//sort();
            return -1;
        }
        push(x); System.out.println("REACHED LINE 127");
        int currPos = front; System.out.println("REACHED LINE 128");

        //while the value to the right is greater
        while(size > 1 && withinBounds(currPos) && withinBounds(right(currPos)) && A[currPos].compareTo(A[right(currPos)]) > 0){
                                                        System.out.println("REACHED LINE 131");
            //swap currPos val with right val
            T currVal = A[currPos];                     System.out.println("REACHED LINE 133");
            T rightVal = A[right(currPos)];             System.out.println("REACHED LINE 134");
            setnth(currPos, rightVal);                  System.out.println("REACHED LINE 135");
            setnth(right(currPos), currVal);            System.out.println("REACHED LINE 136");
            currPos = right(currPos);                   System.out.println("REACHED LINE 137");

        }
        sorted = true;
        System.out.println("REACHED LINE 141");

        return currPos;
    }

    public boolean withinBounds(int index){
        return !(index < ri(front) && index > lasti());
    }



    public boolean forced_check() // force O(n) check for sorted, FOR DEBUGGING
    {
        for(int i=0;i<size-1;i++)
        if (size>1 && A[(front+i)%A.length].compareTo(A[(front+i+1)%A.length])>0) return false;
        return true;
    }

   /// This is needed because of a java peculiarity (the type erasure of T 
   /// in the subclass is Comparable, not Object):
   @Override
   protected T[] makearray(int cap) { return (T[]) new Comparable[cap]; }

   public CmpCQ(int initcapacity) // constructor
    {
        super(initcapacity);
    }

   // ... won't compile until you've implemented the interfaces

   public static void main(String[] av)
    {
        //TESTING ALL FUNCTIONS:
        System.out.println("Testing: ");

    //TESTING is_sorted():
        //Testing Sorted Input
        var t1q = new CmpCQ<Integer>(1);
        for(int i = 0; i < 20; i++){
            t1q.enqueue(i);
        }

        //Testing Unsorted Input
        var t2q = new CmpCQ<Integer>(1);
        for(int i = 0; i < 20; i++){
            t2q.push(i);
        }

        //Output:
        //t1q.printinfo(); t2q.printinfo();
        System.out.println("is_sorted() Test:");
        System.out.println("Expected t1q.is_sorted() Result: " + t1q.forced_check());
        System.out.println("Actual t1q.is_sorted() Result: " + t1q.is_sorted());
        System.out.println("Expected t2q.is_sorted() Result: " + t2q.forced_check());
        System.out.println("Actual t2q.is_sorted() Result: " + t2q.is_sorted());
        

    //TESTING setnth():
        //Testing Original Queue
        var t3q = new CmpCQ<Integer>(1);
        t3q.push(181)
            .push(168)
            .push(95)
            .push(47)
            .push(20);
        
        //Output:
        System.out.println("\nsetnth() Test:");
        t3q.printinfo();
        System.out.println("Original Queue is_sorted() Result: " + t3q.forced_check()); //Expected true
        
        t3q.setnth(1, 56);
        t3q.printinfo();
        System.out.println("Valid Input Queue is_sorted() Result: " + t3q.forced_check()); //Expected true
        
        t3q.setnth(2, 175);
        t3q.printinfo();
        System.out.println("Invalid Input Queue is_sorted() Result: " + t3q.forced_check()); //Expected false


    //TESTING search():
        System.out.println("\nsearch() Test:");
        //Testing Binary Search:
        var t4q = new CmpCQ<Integer>(1);
        for(int i = 0; i < 32; i++){
            t4q.enqueue(i);
        }
        System.out.println("BINARY Search Test:");
        System.out.println("t4q is_sorted Result: " + t4q.is_sorted());
        t4q.printinfo();
        System.out.println("t4q Search for (11): " + t4q.search(11)); //Expected: 11
        System.out.println("t4q BinarySearch for (11): " + t4q.runBinarySearch(11)); //Expected: 11


        //Testing Linear Search:
        var t5q = new CmpCQ<Integer>(1);
        for(int i = 0; i < 32; i++){
            t5q.push(i);
        }
        System.out.println("LINEAR Search Test:");
        System.out.println("t5q is_sorted Result: " + t5q.is_sorted());
        t5q.printinfo();
        System.out.println("t5q Search for (11): " + t5q.search(11)); //Expected: 11
        System.out.println("t5q LinearSearch for (11): " + t5q.runLinearSearch(11)); //Expected: 11

    //TESTING insert_sorted():
        System.out.println("\ninsert_sorted() Test:");
        var t6q = new CmpCQ<Integer>(1);
        System.out.println("Empty t6q: ");
        t6q.printinfo();
        System.out.println("\n");

        for(int i = 13; i < 19; i++){
            /*System.out.println(*/t6q.insert_sorted(i)/*)*/;
            System.out.println((i-12) + " Elements Input:");
            t6q.printinfo();
            System.out.print("\n");
        }





        /*
        int n = 200;
        //int n = 11;
        var cq = new CmpCQ<Double>(1);
        // insert in order n random values
        while (--n>0) cq.insert_sorted(Math.random()*1000);
        System.out.println("sorted: "+cq.is_sorted());
        System.out.println("forced check: "+cq.forced_check());    
	System.out.println("testing search: "+ cq.search(123.456));
        cq.getnth(10) // get the 10th value from queue
          .ifPresent(x -> {
            System.out.println("search2: "+ cq.search(x)); //should find it
          });
        cq.printinfo();
        */
    }
}//class
