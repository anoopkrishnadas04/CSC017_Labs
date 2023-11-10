package CSC017_Lab_07;

/// simple scoring scheme, no gap penalty
public class scheme3 extends absdna { // another scheme, with a main
    @Override
     public int score(int i, int k) {
         if (A.charAt(i) == B.charAt(k)) return 2;
         else return 0;
     }
     @Override
     public int W() { return -1; }
 
     // constructors
     public scheme3(int n, int m) { super(n,m); }
     public scheme3(String n, String m, boolean arefiles) {super(n,m,arefiles);}
 }// scheme3