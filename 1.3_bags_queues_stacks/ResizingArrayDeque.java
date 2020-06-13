import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class ResizingArrayDeque<Item> implements Iterable<Item> {
    private int first;
    private int last;
    private int n;
    private Item[] a;

    public ResizingArrayDeque() {
        a = (Item[]) new Object[2];
        n = first = last = 0;
    }

    public boolean isEmpty() {
        return (n == 0);
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        int i = 0;
        int j = first;
        do {
            copy[j] = a[i];
            i++;
            j = (j + 1) % a.length;
        } while (j != (last + 1) % a.length);
        a = copy;
        first = 0;
        last = j;

    }

    private void checkResize() {
        if (n == a.length) resize(a.length * 2);
        else if (n == a.length / 4) resize(a.length / 2);
    }

    public void pushLeft(Item item) {
        checkResize();
        if (first == 0) first = a.length - 1;
        else first--;
        a[first] = item;
        n++;
    }

    public void pushRight(Item item) {
        checkResize();
        if (last == (a.length - 1)) last = 0;
        else last++;
        a[last] = item;
        n++;
    }

    public Item popLeft() {
        Item item = a[first];
        checkResize();
        if (first == (a.length - 1)) first = 0;
        else first++;
        n--;
        return item;
    }

    public Item popRight() {
        Item item = a[last];
        checkResize();
        if (last == 0) last = a.length - 1;
        else last--;
        n--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ResizingArrayDequeIterator();
    }

    private class ResizingArrayDequeIterator implements Iterator<Item> {
        int i = first;

        public boolean hasNext() {
            return i != (first + n) % a.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item item = a[i];
            i = (i + 1) % a.length;
            return item;
        }
    }

    public static void main(String[] args) {
        ResizingArrayDeque<Integer> dq = new ResizingArrayDeque<Integer>();
        dq.pushLeft(1);
        dq.pushLeft(2);
        dq.pushLeft(3);

        StdOut.printf("Size: %d \n", dq.size());

        StdOut.println("Iterator print: ");
        for (int i : dq) {
            StdOut.println(i);
        }
        StdOut.printf("popLeft: %d \n", dq.popLeft());
        StdOut.printf("popRight: %d \n", dq.popRight());
        StdOut.printf("Size: %d \n", dq.size());
    }
}
