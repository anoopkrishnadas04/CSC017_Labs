package CSC017_Lab_04;

import java.util.Optional;

public class HeapSort {

    public <T> void heapsort(BinaryHeap B){
        for(int i = B.H.length-1; i >= 0; i--){
            Optional<T> optVal = B.poll();
            B.H[i] = (Comparable) optVal.orElse(null);
            if(B.H[i] == null) System.err.println("no Value present");
        }
    }

    public static void main(String[] args){ //Tests HeapSort
        Integer[] arr = {3, 9, 5, 6, 11, 85, 66, -12};
        BinaryHeap<Integer> B = new BinaryHeap<Integer>(arr, arr.length, true);

        HeapSort hSort = new HeapSort();
        hSort.heapsort(B);

        for(int n : B.H){
            System.out.print(n + ", ");
        }
    }
}
