import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class TwoSumFaster {

    private int[] a;

    public TwoSumFaster (int[] in_array){
        a = in_array; //shallow copy
    }

    public int twoSum(int sumval){
        int lo = 0;
        int hi = a.length-1;
        int count = 0;
        while (lo != hi){
            int k = a[lo] + a[hi] - sumval;
            if (k == 0){
                count++;
                lo++;
            }
            else if (k < 0) lo++;
            else if (k > 0) hi--;
        }
        return count;
    }

    public static void main(String[] args){

        int[] in_array = new int[]{-5,-3,-2,1,2,4,5};
        Arrays.sort(in_array);
        TwoSumFaster a = new TwoSumFaster(in_array);
        StdOut.println(a.twoSum(0));

    }

}