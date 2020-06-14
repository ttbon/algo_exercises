import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;


public class ClosestPair {

    public static void main(String[] args){
        double[] in_array = new double[]{2.3, 2.4, 3, 3.5, 3.67, 6, 2, 1.8, 2.45};
        Arrays.sort(in_array);

        int i0 = 0;
        double min_diff = in_array[i0+1] - in_array[i0];

        for (int i=1; i<(in_array.length-1); i++){
            double new_diff = in_array[i+1] - in_array[i];
            if (new_diff < min_diff){
                min_diff = new_diff;
                i0 = i;
            }
        }

        StdOut.printf("Values %f and %f are closest \n", in_array[i0], in_array[i0+1]);

    }


}