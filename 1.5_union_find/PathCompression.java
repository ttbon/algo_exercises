import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;

public class PathCompression
{
    public int[] id;   // access to component id (site indexed)
    private int count;  // number of components
    private Stack<Integer> pathStack = new Stack<Integer>(); //added

    public PathCompression (int N)
    { // Initialize component id array.
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    public int count()
    { return count; }

    public boolean connected(int p, int q)
    { return find(p) == find(q); }

    public int find(int p) {
        while (p != id[p]){
            pathStack.push(p); // track nodes along the way
            p = id[p];
        }
        return p;
    }

    public void union(int p, int q){
        int qRoot = find(q);

        //path compress qTree
        while (!pathStack.isEmpty()){
            id[pathStack.pop()] = qRoot;
        }

        int pRoot = find(p);
        if (pRoot != qRoot){
            id[pRoot] = qRoot;
            pRoot = qRoot;
            count--;
        }

        //path compress pTree
        while (!pathStack.isEmpty()){
            id[pathStack.pop()] = pRoot;
        }

    }

    public static void main(String[] args)
    { // Solve dynamic connectivity problem on StdIn.
        int N = StdIn.readInt();
        // Read number of sites.
        PathCompression uf = new PathCompression(N);
        // Initialize N components.
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();    // Read pair to connect.
            if (uf.connected(p, q)) continue; // Ignore if connected.
            uf.union(p, q); // Combine components
            StdOut.println(p + " " + q);    // and print connection.
        }
        StdOut.println(Arrays.toString(uf.id)); // path depth starting from node 4
    }
}