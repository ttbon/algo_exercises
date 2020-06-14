import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class BitonicSearch {
    int[] a;
    int s;
    boolean found = false;

    public BitonicSearch(int[] in_array, int searchval){
        a = in_array;
        s = searchval;
    }

    public boolean search(){
        StdOut.printf("looking for value %d \n", s);
        if (s == a[0] || s == a[a.length-1]) {
            return true;
        }
        if(s < a[0] && s < a[a.length]) return false;
        return bitonicSearch(0, a.length-1);
    }

    private boolean bitonicSearch(int lo, int hi) {
        int mid = lo + (hi - lo) / 2;

        // check if matches midpoint
        if (s == a[mid]) {
            return true;
        }

        if ((hi-lo) < 3) return false;

        // check which side is monotonic
        if (a[mid] < a[mid + 1]) { //left side is!
            if (s < a[mid]) {
                StdOut.printf("Checking monotonic section (left) bounded by %d and %d \n", a[lo], a[mid]);
                if (binarySearch(lo, mid)) return true;
            }
            StdOut.printf("Checking bitonic section (right) bounded by %d and %d \n", a[mid], a[hi]);
            if (bitonicSearch(mid, hi)) {
                return true;
            }
        } else if (a[mid] > a[mid + 1]) { //right side is!
            if (s < a[mid]) { //bounded
                StdOut.printf("Checking monotonic section (right) bounded by %d and %d \n", a[mid], a[hi]);
                if (binarySearchDec(mid, hi)) return true;
            }
            StdOut.printf("Checking bitonic section (left) bounded by %d and %d \n", a[lo], a[mid]);
            if (bitonicSearch(lo, mid)) return true;
        }
        // should never reach this point
        return false;
    }

    private boolean binarySearch(int lo, int hi){
        StdOut.println("Entering binary search");
        while((hi-lo)>=2){
            StdOut.printf("(lo,hi): (%d,%d) \n", lo, hi);
            int mid = lo + (hi - lo)/2;
            if (s < a[mid]) hi=mid;
            else if (s > a[mid]) lo=mid;
            else if (s == a[mid]) return true;
        }
        return false;
    }

    private boolean binarySearchDec(int lo, int hi){
        StdOut.println("Entering binary search");
        while((hi-lo)>=2){
            StdOut.printf("(lo,hi): (%d,%d) \n", lo, hi);
            int mid = lo + (hi - lo)/2;
            if (s > a[mid]) hi=mid;
            else if (s < a[mid]) lo=mid;
            else if (s == a[mid]) return true;
        }
        return false;
    }

    public static void main(String[] args){
        int[] in_array = new int[]{1,4,6,8,10,14,13,5,2,-4};
        int searchval = StdIn.readInt();
        BitonicSearch a = new BitonicSearch(in_array, searchval);
        StdOut.printf("Value %d was found? %s", searchval, a.search());
    }
}