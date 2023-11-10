import java.util.HashMap;
import java.util.Optional;


public class Bimap<TA, TB> implements Twowaymap<TA, TB> {
    protected HashMap<TA, TB> forwardHashMap = new HashMap<TA, TB>();
    protected HashMap<TB, TA> reverseHashMap = new HashMap<TB, TA>();
    
    @Override
    public void set(TA x, TB y) {
        if(x == null || y == null){
            System.err.println("Null Input on Line 27");
            return;
        }
        if(forwardHashMap.containsKey(x)){
            TB tempY = forwardHashMap.get(x);
            forwardHashMap.replace(x, y);
            reverseHashMap.remove(tempY, x);
            reverseHashMap.put(y, x);
        } else {
            forwardHashMap.put(x, y);
            reverseHashMap.put(y, x);
        }
    }

    @Override
    public Optional<TB> get(TA x) {
        if(x == null) return Optional.empty();
        if(forwardHashMap.containsKey(x)) return Optional.of(forwardHashMap.get(x));
        return Optional.empty();
    }
    @Override
    public Optional<TA> reverse_get(TB y) {
        if(y == null) return Optional.empty();
        if(reverseHashMap.containsKey(y)) return Optional.of(reverseHashMap.get(y));
        return Optional.empty();
    }
    @Override
    public Optional<TB> removeKey(TA x) {
        if(x == null) return Optional.empty();
        if(!forwardHashMap.containsKey(x)) return Optional.empty();
        TB y = forwardHashMap.remove(x);
        if(y == null) return Optional.empty();
        reverseHashMap.remove(y);
        return Optional.of(y);
    }
    @Override
    public Optional<TA> removeVal(TB y) {
        if(y == null) return Optional.empty();
        if(!reverseHashMap.containsKey(y)) return Optional.empty();
        TA x = reverseHashMap.remove(y);
        if(x == null) return Optional.empty();
        forwardHashMap.remove(x);
        return Optional.of(x);
    }
    @Override
    public int size() {
        return forwardHashMap.size();
    }

    public static void main(String[] args)
    {
        // bijective map between letter grades and grade point values
        Bimap<String,Double> GP = new Bimap<String,Double>();
        String[] Grades = {"A","A-","B+","B","B-","C+","C","C-","D+","D","F"};
        Double[] Points = {4.0,3.7,3.3,3.0,2.7,2.3,2.0,1.7,1.3,1.0,0.0};
        for(int i=0;i<Grades.length;i++) GP.set(Grades[i],Points[i]);
        for(String g:Grades) System.out.println(GP.get(g));
        for(Double p:Points) System.out.println(GP.reverse_get(p));	

        GP.set("A-",3.75); // CHANGE value of A- from 3.7 to 3.75
        GP.set("B+",3.25); // change another key/value
        GP.removeVal(1.3); // remove the D+ grade

        System.out.println(GP.get("A-")); // should print Optional[3.75]
        System.out.println(GP.reverse_get(3.75)); // should print A-
        System.out.println(GP.get("D+")); // should print empty optional
        System.out.println(GP.reverse_get(1.3)); // empty optional

        /// THIS ONE IS THE MOST IMPORTANT, EASY TO GET WRONG: ****

        System.out.println("3.7: "+GP.reverse_get(3.7)); 

            // This MUST PRINT AN EMPTY OPTIONAL
        // because you already changed A- to correspond to 3.75, there
        // should be no value associated with 3.7.

            // Also be sure the check this one:  ****
        //GP.set("A+",4.0); // this will also erase value for "A". why?
        System.out.println(GP.size());  // should print 10

            var p = GP.get("G"); // this better not throw a NullPointerException
            p = GP.get(null);    // neither should this

        // Pay special attention to cases marked with **** above
    }//test
}
