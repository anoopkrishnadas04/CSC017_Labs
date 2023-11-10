package CSC017_Lab_03_v2;

import java.util.Optional;

public class CmpCQ<T extends Comparable<T>> extends CircQueue<T> implements CmpQueue<T>{
    protected boolean sorted = true;   
    
    
    
    public boolean forced_check() // force O(n) check for sorted, FOR DEBUGGING
    {
        for(int i=0;i<size-1;i++)
            if (size>1 && A[(front+i)%A.length].compareTo(A[(front+i+1)%A.length])>0) return false;
        return true;
    }
    
    @Override
    public boolean is_sorted() {
        return sorted; //ASCENDING Order
    }
    @Override
    public CmpCQ<T> push(T x) //assuming CmpCQ is name of your class
    {
        super.push(x); // invokes inherited version of push
        if (size>1 && A[front].compareTo(A[(front+1)%A.length])>0)
            sorted = false;
	    return this;
    }
    @Override
    public CmpCQ<T> enqueue(T x){
        super.enqueue(x); //invokes superclass enqueue algorithm
        if (size > 1 && A[lasti()].compareTo(A[(lasti()-1)%A.length]) < 0)
            sorted = false;
        return this;
    }


    @Override
    public int search(T x) {
        if(sorted) return BinarySearch(x);
        return LinearSearch(x);
    }

    public int BinarySearch(T x){
        if(size() == 0){
            return -1;
        }

        if(size() == 1 && A[front].compareTo(x) == 0){
            return front;
        }


        int diffFactor = (int)(this.size() / 2);

        int mid = ri(diffFactor);

        while(diffFactor > 0){
            int c = x.compareTo(A[mid]);
            diffFactor = (int)(diffFactor / 2);
            if(c < 0){
                mid -= diffFactor;
            }
            if(c > 0){
                mid += diffFactor;
            }
            if(c == 0) return mid;
        }
        return -1;
    }

    public int LinearSearch(T x){
        int current = front;
        for(int i = 0; i < size-1; i++){
            if(A[current].compareTo(x) == 0) return current;
            current = right(current);
        }
        return -1;
    }

    @Override
    public Optional<T> setnth(int n, T x) {
        if (n>=size || n<0 || x==null) {
            System.err.println("value doesn't exist");
            return Optional.empty();
        }
        int ni = (front+n)%A.length; // index of nth value
        T ax = A[ni];
        A[ni] = x;
        if(A[ni].compareTo(A[right(ni)]) <= 0 && A[left(ni)].compareTo(A[ni]) <= 0)
            sorted = false;
        return Optional.of(ax);
    }

    @Override
    public int insert_sorted(T x) {
        if(!sorted) return -1;
        
        if(this.size() == 0){
            push(x);
            return 0;
        }

        if(x.compareTo(A[lasti()]) > 0){
            enqueue(x);
            return size-1;
        }

        int currIndex = front;
        int distanceTraversed = 0;
        push(x);
        int c = A[currIndex].compareTo(A[right(currIndex)]);

        while(c > 0){ //while bigger than next value
            //swap right
            T temp = A[currIndex];
            A[currIndex] = A[right(currIndex)];
            A[right(currIndex)] = temp;

            //update distanceTraversed and currIndex
            distanceTraversed++;
            currIndex = right(currIndex);

            //redefine c
            c = A[currIndex].compareTo(A[right(currIndex)]);
        }

        return distanceTraversed;
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
 
    public static void main(String[] av){
        
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
        System.out.println("t4q BinarySearch for (11): " + t4q.BinarySearch(11)); //Expected: 11


        //Testing Linear Search:
        var t5q = new CmpCQ<Integer>(1);
        for(int i = 0; i < 32; i++){
            t5q.push(i);
        }
        System.out.println("LINEAR Search Test:");
        System.out.println("t5q is_sorted Result: " + t5q.is_sorted());
        t5q.printinfo();
        System.out.println("t5q Search for (11): " + t5q.search(11)); //Expected: 11
        System.out.println("t5q LinearSearch for (11): " + t5q.LinearSearch(11)); //Expected: 11

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
}
