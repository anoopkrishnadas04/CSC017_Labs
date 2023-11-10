import java.util.HashMap;
import java.util.Optional;

public interface Twowaymap<TA,TB> //bijective map between types TA and TB
{
   void set(TA x, TB y);  // insert or change pair, x,y must be non-null.

   Optional<TB> get(TA x); // gets corresponding TB value given TA value x

   Optional<TA> reverse_get(TB y); // get TA value given TB value x

   Optional<TB> removeKey(TA x); // remove pair associated with TA x as key,
                                 // returns TB value if present
   Optional<TA> removeVal(TB y); // remove pair associated with TB y as key,
                                 // returns TA value if present
   int size();   // number of pairs in map. (equal to number of keys)
}