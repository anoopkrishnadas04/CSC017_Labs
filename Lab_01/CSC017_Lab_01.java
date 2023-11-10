package CSC017_Lab_01;
class Time {
    protected int minutes;
    protected int seconds;

    public Time(){}//no-args constructor
    public Time(int minutes, int seconds){
        this.minutes = minutes + (seconds / 60); 
        this.seconds = seconds % 60; 
    }//constructor

    public int totalSeconds(){return seconds + 60 * minutes;} //totalSeconds()

    public String toString(){
        return minutes + " min " + seconds + " sec "; 
    }//toString()

    public void tick(){
        seconds++; 
        minutes += seconds / 60; 
        seconds %= 60; 
    }//tick()

    public int compareTo(Time other){ // >0 if bigger, 0 if equal, <0 if smaller
        return this.totalSeconds() - other.totalSeconds();
    }//compareTo

    public Time add(Time other){
        int s = other.totalSeconds();
        s += seconds; 
        int m = minutes + s/60; 
        s %= 60; 
        return new Time(m, s); 
    }//add()
}//Time class end

class HTime extends Time{
    protected int hours; 

    public HTime(int hours, int minutes, int seconds){
        super(minutes, seconds); 
        this.hours = hours + this.minutes / 60; 
        this.minutes %= 60; 
    }//HTime constructor


    @Override
    public int totalSeconds(){return 3600*hours + 60 * minutes + seconds; } //totalSeconds() override

    @Override
    public String toString(){
        return hours + " hours " + super.toString(); 
    }//toString() override

    @Override
    public void tick(){
        super.tick(); 
        hours = minutes / 60; 
        minutes %= 60; 
    }//tick() override

    @Override
    public Time add(Time other){
        return new HTime(0, 0, this.totalSeconds() + other.totalSeconds()); 
    }//add() override
}


public class CSC017_Lab_01{
    public static void main(String[] args){
        runTime(); 
    }

    public static void runTime(){
        Time t1 = new Time(2, 15); 
        Time t2 = new Time(1, 44); 
        Time t3 = t1.add(t2); 
        System.out.println("t1: " + t1); 
        t1.tick(); 
        System.out.println("t2: " + t2); 
        System.out.println("Sum time: " + t3); 
        System.out.println("compare t1 and t2: " + t1.compareTo(t2));
        System.out.println("compare t1 with equivalent value: " + t1.compareTo(new Time(0, 136))); 
        runHTime(); 
    }

    public static void runHTime(){
        Time t1 = new Time(2, 30); 
        Time t2 = new HTime(1, 2, 30); 
        System.out.println(t1.add(t2)); 

        Time[] times = new Time[4];
        times[0] = new Time(2, 30);
        times[1] = new HTime(1, 30, 25); 
        times[2] = new Time(5, 45); 
        times[3] = new HTime(2, 0, 0); 

        Time sum = new HTime(0,0,0); 

        for (Time t : times){sum = sum.add(t);}

        System.out.println("Total time: " + sum); 

        System.out.println("Can still compare HTimes: " + sum.compareTo(t2)); 

        System.out.println(new HTime(1,0,0).compareTo(new Time(60,0))); 
    }
}
