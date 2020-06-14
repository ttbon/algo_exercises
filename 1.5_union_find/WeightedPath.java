import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;

public class WeightedPath
{
    public int[] id;   // access to component id (site indexed)
    private int count;  // number of components
    private int[] sz;
    private Stack<Integer> pStack = new Stack<Integer>(); //added
    private Stack<Integer> qStack = new Stack<Integer>(); //added

    public WeightedPath (int N)
    { // Initialize component id array.
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;
        sz = new int[N];
        for (int i = 0; i < N; i++) sz[i] = 1;
    }

    public int count()
    { return count; }

    public boolean connected(int p, int q)
    { return find(p) == find(q); }

    public int find(int p) {
        while (p != id[p]){
            p = id[p];
        }
        return p;
    }

    public int find(int p, boolean use_pstack) {
        Stack<Integer> stack;
        if (use_pstack) stack = pStack;
        else stack = qStack;

        while (p != id[p]){
            stack.push(p); // track nodes along the way
            p = id[p];
        }
        return p;
    }

    public void pathCompress(int root, Stack<Integer> stack){
        while (!stack.isEmpty()){
            id[stack.pop()] = root;
        }
    }

    public void union(int p, int q){

        int pRoot = find(p, true);
        int qRoot = find(q, false);

        if (pRoot == qRoot) {
            pathCompress(pRoot, pStack);
            pathCompress(qRoot, qStack);
            return;
        }

        if (sz[pRoot] < sz[qRoot]) {
            id[pRoot] = qRoot;
            pathCompress(qRoot, pStack);
            pathCompress(qRoot, qStack);
        } else {
            id[qRoot] = pRoot;
            pathCompress(pRoot, pStack);
            pathCompress(pRoot, qStack);
        }
        count--;
    }

    public static void main(String[] args)
    { // Solve dynamic connectivity problem on StdIn.
        int N = StdIn.readInt();
        // Read number of sites.
        WeightedPath uf = new WeightedPath(N);
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