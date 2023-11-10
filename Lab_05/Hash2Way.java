import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

public class Hash2Way<TA, TB> {
    public HashMap<TA, TB> forwardHashMap = new HashMap<TA, TB>();
    public HashMap<TB, HashSet<TA>> reverseHashMap = new HashMap<TB, HashSet<TA>>();
    
    public void put(TA x, TB y){
        if(x == null || y == null) return;

        TB tempY = forwardHashMap.put(x, y);
        if(tempY != null && reverseHashMap.containsKey(tempY)){
            reverseHashMap.get(tempY).remove(x);
        }

        if(reverseHashMap.containsKey(y)){
            reverseHashMap.get(y).add(x);
        } else {
            HashSet<TA> tempSet = new HashSet<TA>();
            tempSet.add(x);
            reverseHashMap.put(y, tempSet);
        }
    }

    public Optional<TB> get(TA x){
        if(x == null) return Optional.empty();
        if(!forwardHashMap.containsKey(x)) return Optional.empty();
        else return Optional.of(forwardHashMap.get(x));
    }

    public Optional<HashSet<TA>> reverse_get(TB y){
        if(y == null) return Optional.empty();
        if(!reverseHashMap.containsKey(y)) return Optional.empty();
        else return Optional.of(reverseHashMap.get(y));
    }

    public static void main(String[] av)
    {
       // create a Hash2Way hashmap to map student names to GPAs
        Hash2Way<String,Double> GPA = new Hash2Way<String,Double>();
        GPA.put("Mary",3.7);
        GPA.put("Larz",4.0);
        GPA.put("Narx",4.0);
        GPA.put("Lenz",4.0);
        GPA.put("Bryant",3.5);
        GPA.put("Cryans",3.5);

        GPA.get("Mary")
            .ifPresent(g -> System.out.println("Mary's GPA: "+g));
            System.out.println("Students with 4.0 GPAs:");
        GPA.reverse_get(4.0)
            .ifPresent(set -> { for(String s:set) System.out.println(s); });

            GPA.put("Narx", 3.7); // Narx had a hard semester ...
            System.out.println("\nNarx's new GPA: "+GPA.get("Narx"));
            System.out.println("Students with 4.0 GPAs:");
        GPA.reverse_get(4.0)
            .ifPresent(set -> { for(String s:set) System.out.println(s); });

            System.out.println("Students with 3.7 GPAs:");
        GPA.reverse_get(3.7)
            .ifPresent(set -> { for(String s:set) System.out.println(s); });
    }//main


    //.get(TA)
    //.reverse_get(TB)
}
