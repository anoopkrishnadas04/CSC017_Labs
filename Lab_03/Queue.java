package CSC017_Lab_03;

import java.util.Optional;

public interface Queue<T>
{
    int size();      // number of values stored in Queue
    Queue<T> push(T x);  // add to front of queue (queue used like a stack)
    Optional<T> pop();         // delete from front of queue
    Optional<T> peek();        // value at front without delete
    Queue<T> enqueue(T x); // add to end of queue
    Optional<T> dequeue();       // delete from end of queue
    Optional<T> last();          // last value in queue, without delete
}
