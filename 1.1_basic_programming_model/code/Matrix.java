import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class Matrix {

    public static double dot(double[] x, double[] y) {
        assert (x.length == y.length);
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * y[i];
        }
        return sum;
    }

    public static double[][] transpose(double[][] in_array) {
        int rows = in_array.length;
        int cols = in_array[0].length;
        double[][] out_array = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                out_array[j][i] = in_array[i][j];
            }
        }
        return out_array;
    }

    public static double[][] mult(double[][] a, double[][] b) {

        assert (a[0].length == b.length);

        double[][] b_t = transpose(b);
        double[][] prod = new double[a.length][b_t.length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b_t.length; j++) {
                prod[i][j] = dot(a[i], b_t[j]);
            }
        }

        return prod;
    }

    public static double[] mult(double[][] a, double[] x) {
        assert (a[0].length == x.length);
        double[] prod = new double[a.length];

        for (int i = 0; i < a.length; i++) {
            prod[i] = dot(a[i], x);
        }
        return prod;
    }

    public static double[] mult(double[] y, double[][] a) {
        assert (y.length == a.length);
        double[][] a_t = transpose(a);
        double[] prod = new double[a_t.length];

        for (int i = 0; i < a_t.length; i++) {
            prod[i] = dot(y, a_t[i]);
        }
        return prod;
    }


    public static double[][] buildMatrix() {
        //StdOut.println("Insert matrix dimensions m,n: ");
        int m = StdIn.readInt();
        int n = StdIn.readInt();
        double[][] my_matrix = new double[m][n];

        //StdOut.println("Insert values: ");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                my_matrix[i][j] = StdIn.readDouble();
            }
        }
        return my_matrix;
    }

    public static double[] buildVector() {
        //StdOut.println("Insert vector length: ");
        int n = StdIn.readInt();
        double[] my_vector = new double[n];

        //StdOut.println("Insert values");
        for (int i = 0; i < n; i++) {
            my_vector[i] = StdIn.readDouble();

        }
        return my_vector;
    }

    public static void main(String[] args) {
        double[][] mat1 = buildMatrix();
        StdOut.println("mat1: \n" + Arrays.deepToString(mat1));

        double[][] mat2 = buildMatrix();
        StdOut.println("mat2: \n" + Arrays.deepToString(mat2));

        double[] vec1 = buildVector();
        StdOut.println("vec1: \n" + Arrays.toString(vec1));

        double[] vec2 = buildVector();
        StdOut.println("vec2: \n" + Arrays.toString(vec2));

        double my_dot = dot(vec1, vec1);
        StdOut.println("vec1 dot vec1: \n" + my_dot);

        double[][] my_matrix_prod = mult(mat1, mat2);
        StdOut.println("mat1 x mat2: \n" + Arrays.deepToString(my_matrix_prod));

        double[] mat_vec_prod = mult(vec2, mat1);
        StdOut.println("vec2 x mat1: \n" + Arrays.toString(mat_vec_prod));

        mat_vec_prod = mult(mat2, vec1);
        StdOut.println("mat2 x vec1: \n" + Arrays.toString(mat_vec_prod));

    }

}
