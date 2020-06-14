import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Josephus {
    public static void main(String[] args) {

        int n = StdIn.readInt();
        int m = StdIn.readInt();

        Queue<Integer> circle = new Queue<Integer>();

        //build circle
        for (int i=0; i<n; i++){
            circle.enqueue(i);
        }

        while(circle.size() > 0){
            int j = m % circle.size();
            if (j==0) j=m;
            // advance around the circle
            for (int i=0; i < (j-1); i++){
                circle.enqueue(circle.dequeue());
            }
            StdOut.print(" " + circle.dequeue());
            StdOut.println();
        }
    }
}
