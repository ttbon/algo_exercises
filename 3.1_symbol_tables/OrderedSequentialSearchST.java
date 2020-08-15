import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Queue;

public class OrderedSequentialSearchST<Key extends Comparable<Key>, Value> {
    private Node root, tail; //lowest key, ascending order
    private int size = 0;

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node next,prev;     // left and right subtrees

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    public OrderedSequentialSearchST() {
    }

    public void put(Key key, Value val) {

        if (this.isEmpty()){
            Node my_node = new Node(key, val);
            size++;
            tail = my_node;
            root = my_node;
            return;
        }

        Node x = root;
        int c = key.compareTo(x.key);
        while (c > 0){
            x = x.next;
            if (x == null) break; // exit if reached the end
            c = key.compareTo(x.key);
        }


        if (c == 0){    // if key exists
            x.val = val;
            return;
        }

        if (x == null){    // if reached the end
            Node my_node = new Node(key, val);
            size++;
            tail.next = my_node;
            my_node.prev = tail;
            tail = my_node;
            return;
        }

        // if found key that is greater
        Node my_node = new Node(key, val);
        size++;
        //preceding link
        my_node.prev = x.prev;
        if(x.prev != null) x.prev.next = my_node;
        else root = my_node;
        //forward link: will always exist because x.next is not null
        my_node.next = x;
        x.prev = my_node;

        tail = my_node;

    }

    public Value get(Key key) {
        Node x = root;
        while (x != null & key.compareTo(x.key) > 0) x = x.next;
        if (x == null | key.compareTo(x.key) < 0) return null; //key not found
        return x.val;
    }

    public void delete(Key key) {
        if (this.isEmpty()) return;
        if (this.size() == 1 & key.compareTo(root.key) == 0){
            root = null;
            tail = null;
            size--;
            return;
        }

        Node x = root;
        while (x != null & key.compareTo(x.key) > 0) x = x.next;

        if (key.compareTo(x.key) < 0 | x == null) return; //key missing

        size--; // key is found

        if (x == root){
            root = x.next;
            root.prev = null;
            return;
        }

        if (x == tail){
            tail = x.prev;
            tail.next = null;
            return;
        }

        x.prev.next = x.next;
        x.next.prev = x.prev;
        return;
    }

    public boolean contains(Key key) {
        Node x = root;
        while (x != null && key.compareTo(x.key) > 0) x = x.next;
        if (x == null || key.compareTo(x.key) < 0) return false;
        return true;

    }

    public boolean isEmpty() {
        return (this.size == 0);
    }

    public int size() {
        return this.size;
    }

    public Key min() {
        return this.root.key;
    }

    public Key max() {
        return this.tail.key;
    }

    public Key floor(Key key) {
        if (key.compareTo(root.key) < 0) return null;
        
        Node x = tail;
        while (x != null && key.compareTo(x.key) < 0) x = x.prev;
        return x.key;
    }

    public Key ceiling(Key key) {
        if (key.compareTo(tail.key) > 0) return null;
        Node x = root;
        while (x != null && key.compareTo(x.key) > 0) {
            x = x.next;
        }
        return x.key;
    }

    public int rank(Key key) {
        int rank = 0;
        Node x = root;
        while (x != null && key.compareTo(x.key) > 0) {
            x = x.next;
            rank++;
        }
        return rank;
    }

    public Key select(int k) {
        if (k > this.size()) return null;

        Node x = root;
        for (int i = 0; i<k; i++) x = x.next;
        return x.key;
    }

    public void deleteMin() {
        Key minkey = this.select(0);
        this.delete(minkey);
        return;
    }

    public void deleteMax() {
        Key maxkey = this.select(this.size()-1);
        this.delete(maxkey);
        return;
    }

    public int size(Key lo, Key hi) {
        int r1 = this.rank(lo);
        int r2 = this.rank(hi);
        int size = r2-r1;
        return size;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        Node x = root;
        while(x != null) {
            queue.enqueue(x.key);
            x = x.next;
        }
        return queue;
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        Node x = root;
        while(hi.compareTo(x.key) >= 1) {
            if (lo.compareTo(x.key) <= 1) queue.enqueue(x.key);
            x = x.next;
        }
        return queue;
    }

    public static void main(String[] args){
        OrderedSequentialSearchST<String,Integer> st = new OrderedSequentialSearchST<String,Integer>();
        st.put("a",3);
        StdOut.printf("The value for %s is %d\n", "a", st.get("a"));
        st.delete("a");
        StdOut.printf("After deleting, the value for %s is found? %s\n", "a", st.contains("a"));
        StdOut.printf("Is the ST empty? %s\n", st.isEmpty());
        StdOut.printf("Current size: %d\n", st.size());

        st.put("b",12);
        st.put("e",15);
        st.put("h",10);
        st.put("j",20);
        st.put("m",11);

        StdOut.printf("Min: %s\n", st.min());
        StdOut.printf("Max: %s\n", st.max());

        String k = "c";
        StdOut.printf("Floor of %s: %s\n", k, st.floor(k));
        StdOut.printf("Ceiling of %s: %s\n", k, st.ceiling(k));
        StdOut.printf("Rank of %s: %s\n", k, st.rank(k));
        StdOut.printf("Rank of %s: %s\n", "z", st.rank("z"));

        StdOut.printf("Rank %d key is: %s\n", 2, st.select(2));

        st.deleteMin();
        st.deleteMax();

        StdOut.printf("Remaining keys are...\n");
        for (String key : st.keys()) StdOut.printf("%s\n",key);

        StdOut.printf("Rank of %s: %s\n", "b", st.rank("b"));
        StdOut.printf("Rank of %s: %s\n", "i", st.rank("i"));
        StdOut.printf("Size from key %s to key %s is %d \n", "b", "i", st.size("a","i"));



    }

}
