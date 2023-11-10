package CSC017_Lab_01;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class CSC017_Lab_01B{
    //Part 1:
    public static boolean inArray(String searchStr, String[] sA){
        for(String s : sA){
            if(searchStr.equals(s)) return true;
        }
        return false;
    }//inArray()

    //Part 2a:
    public static int sum(int[] A){
        int sumVal = 0;
        for(int num : A){
            sumVal += num;
        }
        return sumVal;
    }//sum()

    //Part 2b:
    public static String smallest(String[] sA){
        if(sA.length == 0) return null;
        String smallestStr = sA[0];
        for(int i = 1; i < sA.length; i++){
            if(smallestStr.compareTo(sA[i]) > 0) smallestStr = sA[i];
        }
        return smallestStr;
    }//smallest()

    //Part 3a:
    public static String reverse(String s){
        String reverseString = "";
        for(int i = s.length()-1; i >= 0; i--){
            reverseString += s.charAt(i);
        }
        return reverseString;
    }//reverse()

    //Part 3b:
    public static boolean palindrome(String str){return str.equals(reverse(str));}//palindrome()

    //Part 4:
    public static boolean hasDuplicates(String[] sA){
        HashSet<String> strSet = new HashSet<String>();
        for(String s : sA){
            if(strSet.contains(s)) return true;
            strSet.add(s);
        }
        return false;
    }//hasDuplicates()

    //Part 5:
    public static void main(String[] args){
        String[] strArr = { "ab", "meow", "potato", "aa", "fruit", "dog", "ab", "shark"};
        int[] intArr = {1, 3, 5, 7, 9};
        String testStr1 = "_";
        String testStr2 = "racecar";
        String testStr3 = "ball";
        String testStr4 = "Tacocat";

        System.out.println("strArr = " + strArr);
        System.out.println("Test 1a: Is 'meow' in strArr?\n" + inArray("meow", strArr)); //expected true
        System.out.println("Test 1b: Is 'cow' in strArr?\n" + inArray("cow", strArr)); //expected false

        System.out.println("\nTest 2a: Sum of intArr?\n" + sum(intArr)); //expected 25
        System.out.println("Test 2b: Smallest String in strArr?\n" + smallest(strArr)); //aa
        System.out.println("Test 2c: Smallest String in an empty array?\n" + smallest(new String[0])); //null

        System.out.println("\nTest 3a: Reverse String test");
        System.out.println("Original String1: " + testStr1); //expected _
        System.out.println("Original String2: " + testStr2); //expected racecar
        System.out.println("Original String3: " + testStr3); //expected ball 
        System.out.println("Original String4: " + testStr4); //expected Tacocat
        System.out.println("Reverse String1: " + reverse(testStr1)); //expected _
        System.out.println("Reverse String2: " + reverse(testStr2)); //expected racecar
        System.out.println("Reverse String3: " + reverse(testStr3)); //expected llab
        System.out.println("Reverse String4: " + reverse(testStr4)); //expected tacocaT

        System.out.println("\nTest 3b: Palindrome String test");
        System.out.println("Palindrome String1: " + palindrome(testStr1)); //expected true
        System.out.println("Palindrome String2: " + palindrome(testStr2)); //expected true
        System.out.println("Palindrome String3: " + palindrome(testStr3)); //expected false
        System.out.println("Palindrome String4: " + palindrome(testStr4)); //expected false

        System.out.println("\nTest 4: Does strArr have duplicates?\n" + hasDuplicates(strArr) + "\n"); //expected true

        team.run();
    }
}

//Part 6:
class team implements Playable{
    protected String teamName;
    protected int wins;
    protected int losses;
    protected int ties;
    protected double advantage;

    public team(String tN){
        teamName = tN;
        wins = 0;
        losses = 0;
        ties = 0;
        advantage = 0;
    }
    public void win(){
        this.wins++;
        advantage += 0.05;
    }
    public void lose(){
        this.losses++;
        advantage -= 0.02;
    }
    public void tie(){
        this.ties++;
    }
    public void printrecord(){
        System.out.println("W-L: " + wins + "-" + ties + "-" + losses);
    }
    public void play(team x){
        //Random rnd = new Random();
        //double r = rnd.nextGaussian();
        //double winrate = (2 - (Math.pow(2, -1.0 * ((this.wins + x.losses) - (x.wins + this.losses)) )));
        
        double gameResult = Math.random() * 3 - 1;
        double winrate = (this.advantage - x.advantage)/2;

        if(gameResult < winrate){
            this.win(); x.lose(); System.out.printf("%s win\n", this.teamName);
        } else if(winrate < gameResult) {
            x.win(); this.lose(); System.out.printf("%s win\n", x.teamName);
        } else {
            this.tie(); x.tie(); System.out.printf("%s ties with %s\n", this.teamName, x.teamName);
        }
    }
    public double winningpercentage(){
        if(wins + losses == 0) return 0;
        return wins / (double)(wins + ties + losses);
    }
    public static void run(){
        team team1 = new team("giants");
        team team2 = new team("jets");
        team1.lose();
        team2.win();
        team2.lose();
        team2.printrecord(); // should print "W-L: 1-1"
        team1.play(team2); // should print "giants win" or "jets win"
        System.out.println(team1.winningpercentage()); // prints .000 to 1.00

        String[] fbn = {"Giants","Jets","Rams","Patriots","Falcons","Steelers","Packers","Eagles","Chiefs","Bills","Seahawks","Cowboys","Chargers","Raiders","Dolphins","Saints","49ers","Broncos"};
        team[] teamList = new team[18];
        int index = 0;
        for(String tN : fbn){teamList[index] = (new team(tN));index++;}
        double fairShift = 0.3;
        for(int i = 0; i < teamList.length; i++){
            teamList[i].advantage += fairShift;
            fairShift -= 0.02;
        }
        for(int i = 0; i < teamList.length-1; i++){
            for(int j = i+1; j < teamList.length; j++){
                teamList[i].play(teamList[j]);
            }
        }

        QuickSort ob = new QuickSort();
		ob.sort(teamList, 0, teamList.length-1);

        System.out.println(teamList[teamList.length-1].teamName + " has the highest win percentage of: " + teamList[teamList.length-1].winningpercentage());

        QuickSort.printArray(teamList);

    }
}

interface Playable
{
   void win();
   void lose();
   // optionally, you may also allow ties.
   void printrecord();
   void play(team x);
   double winningpercentage();
}


//This program was implemented from an online source, as I have not used quicksort before
//But I made sure to modify the algorithm to work for the team class
// Java program for implementation of QuickSort
class QuickSort
{
	/* This function takes last element as pivot,
	places the pivot element at its correct
	position in sorted array, and places all
	smaller (smaller than pivot) to left of
	pivot and all greater elements to right
	of pivot */
	int partition(team[] arr, int low, int high)
	{
		double pivot = arr[high].winningpercentage();
		int i = (low-1); // index of smaller element
		for (int j=low; j<high; j++)
		{
			// If current element is smaller than or
			// equal to pivot
			if (arr[j].winningpercentage() <= pivot)
			{
				i++;

				// swap arr[i] and arr[j]
				team temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		// swap arr[i+1] and arr[high] (or pivot)
		team temp = arr[i+1];
		arr[i+1] = arr[high];
		arr[high] = temp;

		return i+1;
	}//partition()


	/* The main function that implements QuickSort()
	arr[] --> Array to be sorted,
	low --> Starting index,
	high --> Ending index */
	void sort(team arr[], int low, int high)
	{
		if (low < high)
		{
			/* pi is partitioning index, arr[pi] is
			now at right place */
			int pi = partition(arr, low, high);

			// Recursively sort elements before
			// partition and after partition
			sort(arr, low, pi-1);
			sort(arr, pi+1, high);
		}
	}//sort()
	static void printArray(team arr[])
	{
		int n = arr.length;
		for (int i=0; i<n; ++i)
			System.out.println(arr[i].teamName + " - " + arr[i].winningpercentage());
		System.out.println();
	}//Formatted printArray()
}
/*This code is contributed by Rajat Mishra */
