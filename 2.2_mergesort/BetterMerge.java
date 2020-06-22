import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BetterMerge {

    // This class should not be instantiated.
    private BetterMerge() { }

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge into aux[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              aux[k] = a[j++]; // left section exhausted
            else if (j > hi)               aux[k] = a[i++]; // right section exhausted
            else if (less(aux[j], aux[i])) aux[k] = a[j++]; // if right is less than right
            else                           aux[k] = a[i++]; // else take from left
        }

        // postcondition: a[lo .. hi] is sorted
        assert isSorted(a, lo, hi);
    }

    // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        //ensure aux is copied on first call
        if ( (hi+1) == a.length){
            for (int k = lo; k <= hi; k++) {
                aux[k] = a[k];
            }
        }

        if ( (hi - lo) <= 10) { // use insertion sort for small subarrays
            Insertion.sort(a, lo, hi);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(aux, a, lo, mid);
        sort(aux, a, mid + 1, hi);
        if (!less(a[mid], a[mid+1])) { //skip merge if already in order
            merge(aux, a, lo, mid, hi);
        }
    }

    /***************************************************************************
     *  Helper sorting function.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Comparable[] aux = new Comparable[a.length];
        BetterMerge.sort(a,aux, 0, a.length-1);
        show(a);
    }
}