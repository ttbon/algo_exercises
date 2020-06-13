import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomBag<Item> implements Iterable<Item> {
    Item[] a = (Item[]) new Object[2];
    int n = 0;

    public boolean isEmpty() {
        return (n == 0);
    }

    public int size() {
        return n;
    }

    public void add(Item item) {
        checkResize();
        a[n++] = item;
    }

    private void checkResize() {
        if (size() == a.length) resize(a.length * 2);
    }

    private void resize(int capacity) {
        assert capacity >= size();
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            copy[i] = a[i];
        }
        a = copy;
    }

    public Iterator<Item> iterator() {
        return new RandomBagIterator();
    }

    private class RandomBagIterator implements Iterator<Item> {
        int i = 0;
        int[] seq = new int[size()];

        public RandomBagIterator() {
            for (int k = 0; k < seq.length; k++) {
                seq[k] = k;
            }
            StdRandom.shuffle(seq);
//            StdOut.println("Shuffle result:");
//            for (int k : seq) {
//                StdOut.println(k);
//            }
        }

        public boolean hasNext() {
            return i != size();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            return a[seq[i++]];
        }
    }

    public static void main(String[] args) {
        RandomBag<Integer> rb = new RandomBag<Integer>();
        for (int i = 0; i < 5; i++) {
            rb.add(i);
        }
        StdOut.printf("Size: %d \n", rb.size());

        StdOut.println("Iterator print: ");
        for (int i : rb) {
            StdOut.println(i);
        }

        StdOut.println("Iterator print: ");
        for (int i : rb) {
            StdOut.println(i);
        }
    }


}
