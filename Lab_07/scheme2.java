package CSC017_Lab_07;

class scheme2 extends absdna { // this is from the sample on wikipedia article
    @Override
     public int score(int i, int k) {
         if (A.charAt(i) == B.charAt(k)) return 1;
         else return -1;
     }
     @Override
     public int W() { return -1; }
 
     // constructors
     public scheme2(int n, int m) { super(n,m); }
     public scheme2(String n, String m, boolean arefiles) {super(n,m,arefiles);}
 }// scheme2