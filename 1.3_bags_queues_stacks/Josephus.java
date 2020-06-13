import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;

public class Josephus {
    public static void main(String[] args) {

        int n = StdIn.readInt();
        int m = StdIn.readInt();

        Queue<Integer> circle = new Queue<Integer>();

        for (int i = 0; i < n; i++) {
            circle.enqueue(i);
        }

        Queue<Integer> circle = new Queue<Integer>();
        Queue<Integer> nextCircle = new Queue<Integer>();

    }
}
