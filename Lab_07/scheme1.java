package CSC017_Lab_07;

class scheme1 extends absdna {  //put this in a different file
    @Override
     public int score(int i, int k) {
         if (A.charAt(i) == B.charAt(k)) return 1;
         else return 0;
     } 
     @Override
     public int W() { return 0; }
 
     // constructors
     public scheme1(int n, int m) { super(n,m); }
     public scheme1(String n, String m, boolean arefiles) {super(n,m,arefiles);}
 }// scheme1