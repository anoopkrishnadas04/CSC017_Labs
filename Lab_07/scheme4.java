package CSC017_Lab_07;
class scheme4 extends absdna {
    @Override
     public int score(int i, int k) {
         if (A.charAt(i) == B.charAt(k)) return 2;
         else return 0;
     } 
     @Override
     public int W(boolean edge) { if (edge) return 0; else return -1; }
 
     // constructors ...
     public scheme4(int n, int m) { super(n,m); }
     public scheme4(String n, String m, boolean arefiles) {super(n,m,arefiles);}
 }// scheme4
