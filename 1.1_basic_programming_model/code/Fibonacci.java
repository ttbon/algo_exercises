import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Fibonacci {
    private static ArrayList<Long> fiboList = new ArrayList<Long>();

    public static long F(int N) {
        if (fiboList.size() < 2) {
            fiboList.add((long) 0);
            fiboList.add((long) 1);
        }

        if ((fiboList.size() - 1) < N) fiboList.add(F(N - 1) + F(N - 2));

        return fiboList.get(N);

    }

    public static void main(String[] args) {
        for (int N = 0; N < 10; N++)
            StdOut.println(N + " " + F(N));
    }
}
