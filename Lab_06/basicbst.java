//package CSC017_Lab_06;
/*  In this slightly modified version of binary search trees (as sets),
    we adopt the current classes and interfaces with my graphic display program.
   
    The interfaces and classes Node, nil and vertex are made compatible with
    two other interfaces, DisplayNode and Nonempty, defined in BSTdisplay.java

    This program must be compiled with BSTdisplay.java:
      javac basicbst.java BSTdisplay.java

    In particular, the Nonempty interface requires you to implement a method
   
      String get_item();

    that returns a string to be displayed as the contents of a graphical vertex.
    You can define this method to display node any way you like such as
    key+":"+val, or just key.toString().

    The code in main has been augmented to demonstrate how BSTdisplay works.
*/

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;

//Original Node<T>
/*
interface Node<T extends Comparable<T>> extends DisplayNode {
    boolean isempty();  // repeated from DisplayNode
    int size();
    int depth();   // O(n)
    int height();  // O(1)
    Node<T> add(T x); // add something
    boolean search(T x); // true if x is in the tree
    Node<T> remove(T x);
    void inorder(Consumer<T> c);
    void preorder(Consumer<T> c);
    void postorder(Consumer<T> c);
    <R> R match(Function<nil<T>,R> nfun,
	           Function<vertex<T>,R> vfun);
    Node<T> makeclone();
}//interface Node
*/

public class basicbst {
    private static int size = 0;
    public static void main(String[] av) {
        /*
        Node<Integer> t= new nil<Integer>();
        t = t.add(30);
        for(int i=0;i<10;i++) {
            t = t.add((int)(Math.random()*100));
        }
        t.inorder(x -> System.out.print(x+" "));
        System.out.printf("\nsize %d, depth %d\n", t.size(), t.depth()); // endl;

        t.preorder(x ->System.out.print(x+" "));
            System.out.println("\n----");

            t = t.remove(30);

        t.postorder(x -> System.out.print(x+" "));
        System.out.println("\n----");

        // int size won't work: can't change local variable inside lambda
            int[] size = {0};
        t.inorder(x -> size[0]++);
        System.out.println("size via inorder: "+size[0]);

            /// testing graphical display:
        t= new nil<Integer>();  // start over with empty tree
            for(int i=0;i<100;i++) t = t.add((int)(Math.random()*500));
            //for(int i=0;i<100;i++) t = t.add(i); // extremely unbalanced
            BSTdisplay window = new BSTdisplay(1024,768);
            window.drawtree(t);
            window.draw_caption_top("Size: "+t.size()+", height "+t.height());
            window.delay(5000); // 5 sec. delay 
            for(int i=0;i<100;i++) t = t.remove((int)(Math.random()*500));
            window.drawtree(t);
            window.draw_caption_bottom("Size now "+t.size()+", height "+t.height());
        */

        Node<Integer,Double> t = new nil<Integer,Double>();                                 System.out.println("Reached Line 83.");
        // this may also be a wrapper class
        for(int i=0;i<200;i++) t = t.set(i, i*0.5);                                         System.out.println("Reached Line 85.");
        BSTdisplay window = new BSTdisplay(1024,768);                                   System.out.println("Reached Line 86.");
        window.drawtree((DisplayNode) t);                                                   System.out.println("Reached Line 87.");
        window.draw_caption_top("Size: "+t.size()+", height "+t.height());                  System.out.println("Reached Line 88.");
        window.delay(5000); /*5 second delay*/                                           System.out.println("Reached Line 89.");
        for(int i=0;i<200;i+=2) t = t.remove(i); /*remove even numbers*/                    System.out.println("Reached Line 90.");
        window.drawtree((DisplayNode) t);                                                   System.out.println("Reached Line 91.");
        window.draw_caption_bottom("Size now "+t.size()+", height "+t.height());            System.out.println("Reached Line 92.");

        /* 
         * Hi Professor Liang. I'm not sure if you are reading this but I have no clue
         * how the display works. The main function runs in its entirety, so I think I
         * am either not updating one of my files or I am missing something, but the 
         * display part does not work.
         * 
         * I checked on paper for each of the functions and they algorithmically work.
         * I believe the only issue should be the display.
        */
    }// main

        /*
        // This part is just for your benefit...
        //functional-programming style implementation of depth using .match:
        static <T extends Comparable<T>> int depth(Node<T> node) {
        return
        node.match(
            (n) -> 0,   // nil case
            (v) -> Math.max(depth(v.left),depth(v.right))+1 //vertex case
            );
        }// functional depth

        static <T extends Comparable<T>> Node<T> clone(Node<T> node) {
        return
            node.match(
                (n) -> n,
                (v) -> new vertex<T>(v.val,clone(v.left),clone(v.right))
                );
        }// functional clone
        */

}//basicbst

// compile with BSTdisplay.java