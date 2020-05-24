import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class ex1_1_15 {
    public static int[] histogram(int[] a, int M) {
        int[] hist = new int[M];
        for (int i = 0; i < M; i++) {
            hist[i] = 0;
        }
        for (int i = 0; i < a.length; i++) {
            hist[a[i]]++;
        }
        return hist;
    }

    public static void main(String[] args) {
        int num_values = StdIn.readInt();
        int[] a = new int[num_values];
        for (int i = 0; i < num_values; i++) {
            a[i] = StdRandom.uniform(9);
        }
        StdOut.println(Arrays.toString(a));

        int[] b = histogram(a, 9);
        StdOut.println(Arrays.toString(b));

    }
}
