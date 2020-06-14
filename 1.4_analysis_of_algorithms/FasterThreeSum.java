import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class FasterThreeSum {
    private int[] a;
    int count = 0;

    public FasterThreeSum(int[] in_array){
        a = in_array;
    }

    public void twoSum(int sumval){
        int lo = 0;
        int hi = a.length-1;
        while (lo < hi){
            //StdOut.printf("Check indeces %d and %d", lo, hi);
            if (a[lo]==sumval){
                lo++;
                continue;
            } else if (a[hi] == sumval){
                hi--;
                continue;
            }
            int k = a[lo] + a[hi] + sumval;
            if (k == 0){
                StdOut.printf("matched %d with %d and %d \n", sumval, a[lo], a[hi]);
                count++;
                lo++;
            }
            else if (k < 0) lo++;
            else if (k > 0) hi--;
        }
    }

    public void threeSum(){
        for (int i = 0; i < a.length; i++){
            StdOut.printf("Assessing %d \n", a[i]);
            twoSum(a[i]);
        }
    }

    public static void main (String[] args){

        int[] in_array = new int[]{-10,-5,-3,-2,1,2,4,5,6,7};
        Arrays.sort(in_array);
        FasterThreeSum a = new FasterThreeSum(in_array);
        a.threeSum();
        StdOut.println(a.count/3);

    }


}