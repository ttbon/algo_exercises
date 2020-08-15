import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import util.VisualAccumulator;

public class average_search_time{

    private class RedBlackBSTInternalPathLength<Key extends Comparable<Key>, Value> extends RedBlackBST<Key, Value>{
        private int compare_counter = 0;

        public RedBlackBSTInternalPathLength() {
        }

        private class Node {
            Key key;
            Value value;
            Node left, right;

            boolean color;
            int size;
            private int depth; //used only to compute the internal path length

            Node(Key key, Value value, int size, boolean color) {
                this.key = key;
                this.value = value;

                this.size = size;
                this.color = color;
            }
        }

        private Node root;

        public int size() {
            return size(root);
        }

        private int size(Node node) {
            if (node == null) {
                return 0;
            }

            return node.size;
        }

        private boolean isRed(Node node) {
            if (node == null) {
                return false;
            }

            return node.color == RED;
        }

        private Node rotateLeft(Node node) {
            if (node == null || node.right == null) {
                return node;
            }

            Node newRoot = node.right;

            node.right = newRoot.left;
            newRoot.left = node;

            newRoot.color = node.color;
            node.color = RED;

            newRoot.size = node.size;
            node.size = size(node.left) + 1 + size(node.right);

            return newRoot;
        }

        private Node rotateRight(Node node) {
            if (node == null || node.left == null) {
                return node;
            }

            Node newRoot = node.left;

            node.left = newRoot.right;
            newRoot.right = node;

            newRoot.color = node.color;
            node.color = RED;

            newRoot.size = node.size;
            node.size = size(node.left) + 1 + size(node.right);

            return newRoot;
        }

        private void flipColors(Node node) {
            if (node == null || node.left == null || node.right == null) {
                return;
            }

            //The root must have opposite color of its two children
            if ((isRed(node) && !isRed(node.left) && !isRed(node.right))
                    || (!isRed(node) && isRed(node.left) && isRed(node.right))) {
                node.color = !node.color;
                node.left.color = !node.left.color;
                node.right.color = !node.right.color;
            }
        }

        public void put(Key key, Value value) {
            if (key == null) {
                return;
            }

            if (value == null) {
                delete(key);
                return;
            }

            root = put(root, key, value);
            root.color = BLACK;
        }

        private Node put(Node node, Key key, Value value) {
            if (node == null) {
                return new Node(key, value, 1, RED);
            }

            int compare = key.compareTo(node.key);

            if (compare < 0) {
                node.left = put(node.left, key, value);
            } else if (compare > 0) {
                node.right = put(node.right, key, value);
            } else {
                node.value = value;
            }

            if (isRed(node.right) && !isRed(node.left)) {
                node = rotateLeft(node);
            }
            if (isRed(node.left) && isRed(node.left.left)) {
                node = rotateRight(node);
            }
            if (isRed(node.left) && isRed(node.right)) {
                flipColors(node);
            }

            node.size = size(node.left) + 1 + size(node.right);
            return node;
        }

        public int getPathLength(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to get() is null");
            this.compare_counter = 0;
            Value keyval = getPathLength(root, key);
            if (keyval == null) return null;
            else return this.compare_counter;
        }

        // value associated with the given key in subtree rooted at x; null if no such key
        private Value getPathLength(Node x, Key key) {
            while (x != null) {
                this.compare_counter++;
                int cmp = key.compareTo(x.key);
                if      (cmp < 0) x = x.left;
                else if (cmp > 0) x = x.right;
                else              return x.val;
            }
            return null;
        }
    }

    private static double runSingleXp(int N){
        // build tree
        int[] a = new int[N];
        for (int i; i<N; i++){
            a[i] = i;
        }
        StdRandom.shuffle(a);
        RedBlackBSTInternalPathLength<Integer,Integer> tree = new RedBlackBSTInternalPathLength<Integer,Integer>();
        for(int key : a){
            tree.put(key,0);
        }
        return tree.getPathLength(0) / (double) N;
    }

    private static void fullXp(){
        String title = "Average path length to a random node in a red-black BST built from random keys";
        String xAxisLabel = "number of keys N";
        String yAxisLabel = "compares";
        double maxNumberOfOperations = 10000;
        double maxCost = 20;
        int originValue = 1;

        VisualAccumulator visualAccumulator = new VisualAccumulator(originValue, maxNumberOfOperations, maxCost, title,
                xAxisLabel, yAxisLabel);

        double lastComputedAveragePathLength = 0;
        double lastExpectedAveragePathLength = -1;

        //loop through each tree size
        for(int size = originValue; size <= maxNumberOfOperations; size++) {
            int numberOfTrials = 1000;

            long totalAvgPathLengths = 0;

            //build sample for computing avg and std
            for(int t=0; t < numberOfTrials; t++) {
                double averagePathLength = runSingleXp(size);
                totalAvgPathLengths += averagePathLength;
            }

            if (size % 200 == 0) {
                visualAccumulator.drawDataValue(size, averagePathLength, StdDraw.GRAY);
            }


            double averageOfAveragesPathLength = totalAvgPathLengths / (double) numberOfTrials;

            if (size % 200 == 0) {
                visualAccumulator.drawDataValue(size, averageOfAveragesPathLength, StdDraw.RED);

                //Draw the expected average path length -> lg N - .5
                double expectedAveragePathLength = (Math.log(size) / Math.log(2)) - 0.5;
                visualAccumulator.drawDataValue(size, expectedAveragePathLength, StdDraw.BLACK);

                if (lastExpectedAveragePathLength != -1) {
                    StdDraw.line(size - 200, lastExpectedAveragePathLength, size, expectedAveragePathLength);
                }
                lastExpectedAveragePathLength = expectedAveragePathLength;
            }

            lastComputedAveragePathLength = averageOfAveragesPathLength;
        }

        double xCoordinate = maxNumberOfOperations + (maxNumberOfOperations * 0.02);
        String formattedLastComputedAveragePathLength = String.format("%.2f", lastComputedAveragePathLength);
            visualAccumulator.writeText(formattedLastComputedAveragePathLength, xCoordinate,
        lastComputedAveragePathLength, StdDraw.RED);
        }

    public static void main(String[] args) {
        fullXp();
    }
}

