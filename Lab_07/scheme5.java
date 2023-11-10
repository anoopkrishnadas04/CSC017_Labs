package CSC017_Lab_07;
class scheme5 extends absdna {
    @Override
    public int score(int i, int k) {
        if ((A.charAt(i)|32) == (B.charAt(k)|32)) return 3; else return -1;
    }


    @Override
    public int W(boolean edge) {
        if (edge) return 0; else return -2;
      }      

    // constructors ...
    public scheme5(int n, int m) { super(n,m); }
    public scheme5(String n, String m, boolean arefiles) {super(n,m,arefiles);}
}// scheme4
