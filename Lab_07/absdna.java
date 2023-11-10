package CSC017_Lab_07;

import java.io.*;
import java.util.Optional;

public class absdna {
    protected String A;
    protected String B;
    protected int[][] M;
    protected int[][] M_trace;
    //-1 means unexpected problem, 0 means root
    //1 means top, 2 means left, 3 means topleft

    public absdna(int n, int m){
        A = random_dna(n);
        B = random_dna(m);

        A = "_" + A; System.out.println(A);
        B = "_" + B; System.out.println(B);

        //initializeArrays();
    }

    public void initializeArrays(){
        M = new int[A.length()][B.length()];
        M_trace = new int[A.length()][B.length()];
    }

    public absdna(String a, String b, boolean arefiles){
        if(arefiles){
            /* 
            Optional<String> a_Optional = load_dna(a);
            Optional<String> b_Optional = load_dna(b);

            A = a_Optional.orElse("GGG");
            B = b_Optional.orElse("AAA");
            */

            A = load_dna(a).orElse("GGG");
            B = load_dna(b).orElse("GGG");

            if(A.equals("") || B.equals("")){
                System.err.println("Invalid input. One of either A or B is an empty string.");
            }
        }
        else {
            A = a;
            B = b;
        }

        A = "_" + A; //System.out.println(A);
        B = "_" + B; //System.out.println(B);

        //initializeArrays();
    }

    public void fillMatrix(){
        int i = 0, k = 0;
        for(i = 0; i < A.length(); i++){
            for(k = 0; k < B.length(); k++){
                if(i == 0 && k == 0){
                    M[i][k] = 0;
                    M_trace[i][k] = 0;
                }
                else if(i == 0){
                    M[i][k] = M[i][k-1] + W(true);
                    M_trace[i][k] = 2;
                }
                else if(k == 0){
                    M[i][k] = M[i-1][k] + W(true);
                    M_trace[i][k] = 1;
                }
                else {
                    int gapPen;
                    if(i == A.length() - 1 || k == B.length() - 1) gapPen = W(true);
                    else gapPen = W(false);
                    M[i][k] = Math.max(
                        M[i-1][k-1] + score(i, k),
                        Math.max(
                            M[i-1][k] + gapPen,
                            M[i][k-1] + gapPen
                        )
                    );
                    M_trace[i][k] = getCase(i, k, gapPen);
                }
            }
        }

        //DEBUGGING:
        //printmatrix(A, B, M);
        //System.out.println("\n");
        //printmatrix(A, B, M_trace);
        //System.out.println("\n\n\n");

    
    }

    public int getCase(int i, int k, int gapPen){
        if(M[i][k] == M[i-1][k-1] + score(i, k)) return 3;
        else if(M[i][k] == M[i-1][k] + gapPen) return 1;
        else if(M[i][k] == M[i][k-1] + gapPen) return 2;
        return -1;
    }

    public String[] traceBack(){
        String A_trace = "", B_trace = "";
        int i = A.length()-1, k = B.length()-1;
        while(M_trace[i][k] != 0){
            switch(M_trace[i][k]){
                case 1:
                    A_trace += A.charAt(i);
                    B_trace += "_";
                    i--;
                    break;
                case 2:
                    A_trace += "_";
                    B_trace += B.charAt(k);
                    k--;
                    break;
                case 3:
                    A_trace += A.charAt(i);
                    B_trace += B.charAt(k);
                    i--; k--;
                    break;
                default:
                    System.err.println("Unexpected problem in fillArray.");
                    return new String[] {A_trace, B_trace};
            }
        }
        return new String[] {A_trace, B_trace}; 
    }

    public int score(int i, int k){
        if(A.charAt(i) == B.charAt(k)) return 1;
        return 0;
    }

    public int W(){
        return 0;
    }

    public int W(boolean edge){
        return W();
    }

    public void align(){
        initializeArrays();
        fillMatrix();
        /*printResult(*/traceBack()/* )*/;
        System.out.println(M[A.length()-1][B.length()-1]);
    }

    public void printResult(String[] temp_result){
        String[] result = new String[temp_result.length];
        for(int j = 0; j < temp_result.length; j++){
            String tempStr = "";
            for(int i = temp_result[j].length()-1; i >= 0; i--){
                tempStr += temp_result[j].charAt(i);
            }
            result[j] = tempStr;
        }
        for(String str : result){
            System.out.println(str);
        }
    }

    
    public static String random_dna(int n) { // random dna sequence of length n
        if (n<1) return "";
        char[] C = new char[n];
        char[] DNA = {'A','C','G','T'};
        for(int i=0;i<n;i++)
            C[i] = DNA[ (int)(Math.random()*4) ];
        return new String(C); // converts char[] to String
    }//random_dna

    //load dna from file    
    public static Optional<String> load_dna(String filename) { 
        Optional<String> answer = Optional.empty();
	    try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            answer = Optional.of(br.readLine());
            br.close();
        } // IOExceptions MUST be caught
        catch (IOException ie) {}
	return answer;
    }//load_dna

    // for debugging only!  only call on small strings
    public static void printmatrix(String A, String B, int[][] M) {
        if (A==null || B==null || M==null || M[0]==null) return;
        if (A.length() != M.length || B.length() != M[0].length) return;
        int rows = A.length();
        int cols = B.length();
        System.out.print("    ");
        for(int i=0;i<cols;i++) System.out.printf(" %2s ",B.charAt(i));
        System.out.println();
        for(int i=0;i<rows;i++) {
            System.out.print("   "+A.charAt(i));
            for(int k=0;k<cols;k++) {
                System.out.printf(" %2d ",M[i][k]);
            }//for k
            System.out.println();
        }//for i
    }//printmatrix
}
