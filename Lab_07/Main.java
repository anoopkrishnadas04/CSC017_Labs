package CSC017_Lab_07;

public class Main {
    public static void main(String[] args) {
        /*
        scheme1 S1 = new scheme1(10,11); 
        scheme2 S2 = new scheme2("GATTACA","GCATGCG",false);
        //scheme3 S3 = new scheme3("seq1.dna","seq2.dna",true); //load from files
        S1.align();
        S2.align();
        //S3.align();
        */

        /*
        //PRACTICE TESTING:
        scheme1 S1_manual_test = new scheme1("ACTCGT", "GACTAGTA", false);
        S1_manual_test.align();

        scheme2 S2_manual_test = new scheme2("AGTC", "AGT", false);
        S2_manual_test.align();


        scheme4 s4 = new scheme4("AGTC", "AGT", false);
        s4.align();
        */

        //Lab Testing:

        String armanFile = "C:\\Users\\anoop\\OneDrive\\Desktop\\CSC017 Labs\\CSC017_Lab_07\\arman.dna";
        String artyanFile = "C:\\Users\\anoop\\OneDrive\\Desktop\\CSC017 Labs\\CSC017_Lab_07\\artyan.dna";
        String motherFile = "C:\\Users\\anoop\\OneDrive\\Desktop\\CSC017 Labs\\CSC017_Lab_07\\mother.dna";
        String fatherFile = "C:\\Users\\anoop\\OneDrive\\Desktop\\CSC017 Labs\\CSC017_Lab_07\\father.dna";


        //ARMAN CASES:
        //Arman vs Father
        scheme5 armFath = new scheme5(armanFile, fatherFile, true);
        System.out.println("The alignment score between Arman and Father is:");
        armFath.align();

        //Arman vs Mother
        scheme5 armMoth = new scheme5(armanFile, motherFile, true);
        System.out.println("The alignment score between Arman and Mother is:");
        armMoth.align();


        //ARTYAN CASES:
        //Artyan vs Father
        scheme5 artFath = new scheme5(artyanFile, fatherFile, true);
        System.out.println("The alignment score between Artyan and Father is:");
        artFath.align();

        //Artyan vs Mother
        scheme5 artMoth = new scheme5(artyanFile, motherFile, true);
        System.out.println("The alignment score between Artyan and Mother is:");
        artMoth.align();

        //BROTHER CASE:
        //Arman vs Artyan
        scheme5 brothers = new scheme5(armanFile, artyanFile, true);
        System.out.println("The alignment score between Arman and Artyan is:");
        brothers.align();

        //PARENTS CASE:
        //Father vs Mother
        scheme5 parents = new scheme5(fatherFile, motherFile, true);
        System.out.println("The alignment score between Mother and Father is:");
        parents.align();


     }//main
}
