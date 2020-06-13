import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

    private class Node {
        Node previous;
        Node next;
        Item item;
    }

    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return (n == 0);
    }

    public int size() {
        return n;
    }

    public void pushLeft(Item item) {
        Node my_node = new Node();
        my_node.item = item;
        if (n > 0) {
            my_node.next = first;
            first.previous = my_node;
        }
        first = my_node;
        if (n == 0) last = my_node;
        n++;
    }

    public void pushRight(Item item) {
        Node my_node = new Node();
        my_node.item = item;
        if (n > 0) {
            my_node.previous = last;
            last.next = my_node;
        }
        last = my_node;
        if (n == 0) first = my_node;
        n++;
    }

    public Item popLeft() {
        Node old_first = first;
        first = old_first.next;
        if (n == 1) first.previous = null;
        n--;
        return old_first.item;
    }

    public Item popRight() {
        Node old_last = last;
        last = old_last.previous;
        if (n == 1) last.next = null;
        n--;
        return old_last.item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        Node current = first;

        public boolean hasNext() {
            return (current != null);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
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
