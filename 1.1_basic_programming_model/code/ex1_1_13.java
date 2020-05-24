import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;


public class ex1_1_13 {
    public static int[][] transpose(int[][] in_array) {
        int rows = in_array.length;
        int cols = in_array[0].length;
        int[][] out_array = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                out_array[j][i] = in_array[i][j];
            }
        }

        return out_array;
    }

    public static void main(String[] args) {
        int rows = StdIn.readInt();
        int cols = StdIn.readInt();

        StdOut.println("" + rows);
        StdOut.println("" + cols);

        int[][] in_array = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                in_array[i][j] = StdRandom.uniform(9);
            }
        }

        StdOut.println("Starting array");
        StdOut.println(Arrays.deepToString(in_array) + "\n");

        int[][] out_array = transpose(in_array);
        StdOut.println("Transposed array");
        StdOut.println(Arrays.deepToString(out_array) + "\n");

    }
}
