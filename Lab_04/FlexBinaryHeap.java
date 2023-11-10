package CSC017_Lab_04;

public class FlexBinaryHeap<T extends HeapValue<? super T>> extends BinaryHeap<T> implements MinMaxHeap<T>
{
    public FlexBinaryHeap(int cap, boolean maxmin) { super(cap,maxmin); }
    public FlexBinaryHeap() { super(); }


    @Override
    public boolean reposition(T x) {
        int i = x.getIndex();
        if(i < 0 || i >= H.length || H[i] != x){System.out.println("Out of Bounds"); return false;}
        if(i == swapup(i)) swapdown(i);
        return true;
    }

    @Override
    protected void swap(int i, int k){
        super.swap(i, k);
        H[k].setIndex(k);
        H[i].setIndex(i);
    }

    @Override
    public BinaryHeap<T> add(T x){
        //super.add(x);
        if (x==null) {                // amortized O(1) average case
            System.err.println("null value ignored in BinaryHeap.add");
            return this;
        }
        if (size == H.length) resize(); // double capacity
        H[size] = x; // place at end of tree
        int i = size;
        size++;
        if(i == swapup(size-1)){
            H[size-1].setIndex(i);
        }
        return this;
    }




    @Override
    @SuppressWarnings("unchecked") /* don't use unless you're REALY sure...*/
    protected T[] makearray(int n) { return (T[]) new HeapValue[n]; }

    //...
    // implement a real  public boolean reposition(T x) {...}

    public static void main(String[] av)
    {
        scholar a = new scholar("A",1.5f);
        scholar b = new scholar("B",2.5f);
        scholar c = new scholar("C", 3.3f);
        scholar d = new scholar("D",3.0f);
        scholar e = new scholar("E",3.2f);
        scholar f = new scholar("F",3.7f);
        scholar g = new scholar("G",0.1f);
        scholar h = new scholar("H",2.6f);
        
        scholar[] Scholars = {a,b,c,d,e,f,g,h};
        FlexBinaryHeap<scholar> FPQ = new FlexBinaryHeap<scholar>();
        for (scholar x:Scholars){
            FPQ.add(x); System.out.println("Repositioning: " + x.name); FPQ.reposition(x); 
        }
        FPQ.peek().ifPresent(s -> {
            System.out.println("top scholar: "+ s.name);
        });
        
        /*
        System.out.println("before GPA updates...");
        while (FPQ.size()>0) FPQ.poll().ifPresent(s -> System.out.println(s.name));
        */


        // priorities change over time...
        d.updateGPA(3.5f);  FPQ.reposition(d);
        e.updateGPA(3.7f);  FPQ.reposition(e);

        f.updateGPA(0.1f);  FPQ.reposition(f);
        g.updateGPA(3.8f);  FPQ.reposition(g);

        System.out.println("after GPA updates...");
        while (FPQ.size()>0) FPQ.poll().ifPresent(s -> System.out.println(s.name));
    }
}