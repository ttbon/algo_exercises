import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Fast3way {

    // This class should not be instantiated.
    private Fast3way() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    // quicksort the subarray a[lo .. hi] using 3-way partitioning
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;

        int p = lo+1, i = lo;
        int q = hi, j=hi+1;

        Comparable v = a[lo];

        StdOut.printf(">> NEW CALL -- lo:%d, p:%d, i:%d, j:%d, q:%d, hi:%d \n", lo, p, i, j, q, hi);
        show(a);
        while(true){
            while(a[++i].compareTo(v) <= 0){ //left continues until something great than v
                show(a);
                if (i==hi) break;
                if (a[i].compareTo(v) == 0){
                    exch(a, p++, i);
                }
            }
            show(a);
            StdOut.printf(">> p: %d, i: %d \n", p, i);

            while(a[--j].compareTo(v) >= 0){ //right continues until something less than v
                show(a);
                if (j == lo) break;
                if (a[j].compareTo(v) == 0){
                    exch(a, q--, j);
                }
            }
            show(a);
            StdOut.printf(">> j: %d, q: %d \n", j, q);
            if (i>=j) break;
            show(a);
            exch(a,i,j);
        }

        // put all v's into position
        int orig_lo = lo, orig_hi = hi;
        show(a);
        StdOut.printf(">> lo:%d, p:%d, i:%d, j:%d, q:%d, hi:%d \n", lo, p, i, j, q, hi);
        while( (lo < p) && (j > p)){
            show(a);
            StdOut.printf(">> lo: %d, p:%d, j:%d \n", lo, p, j);
            exch(a, lo++, j--);
        }
        while( (hi > q) && (i < p)){
            show(a);
            StdOut.printf(">> i: %d, q:%d, hi:%d \n", i, q, hi);
            exch(a, hi--, i++);
        }

        sort(a, orig_lo, j);
        sort(a, i, orig_hi);
    }



    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
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
            StdOut.printf("%s ", a[i]);
        }
        StdOut.println();
    }

    /**
     * Reads in a sequence of strings from standard input; 3-way
     * quicksorts them; and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Fast3way.sort(a);
        show(a);
    }

}