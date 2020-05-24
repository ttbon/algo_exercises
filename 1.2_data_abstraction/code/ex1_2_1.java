import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class ex1_2_1 {
    public static void main(String[] args) {
        int N = StdIn.readInt();
        Point2D[] pointArray = new Point2D[N - 1];
        for (int i = 0; i < N - 1; i++) {
            pointArray[i] = new Point2D(StdRandom.uniform(), StdRandom.uniform());
            pointArray[i].draw();
        }

        double min_dist = 2;
        double new_dist;
        int i_star = 0;
        int j_star = 1;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N - 1; j++) {
                new_dist = pointArray[i].distanceTo(pointArray[j]);
                if (new_dist < min_dist) {
                    min_dist = new_dist;
                    i_star = i;
                    j_star = j;
                }
            }
        }

        // draw min distance
        pointArray[i_star].drawTo(pointArray[j_star]);
        StdOut.println("shortest distance found was: " + min_dist);
    }
}
