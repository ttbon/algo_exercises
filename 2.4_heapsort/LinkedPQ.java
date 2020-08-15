/******************************************************************************
 *  Compilation:  javac MinPQ.java
 *  Execution:    java MinPQ < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/24pq/tinyPQ.txt
 *
 *  Generic min priority queue implementation with a binary heap.
 *  Can be used with a comparator instead of the natural order.
 *
 *  % java MinPQ < tinyPQ.txt
 *  E A E (6 left on pq)
 *
 *  We use a one-based array to simplify parent and child calculations.
 *
 *  Can be optimized by replacing full exchanges with half exchanges
 *  (ala insertion sort).
 *
 ******************************************************************************/


import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *  The {@code MinPQ} class represents a priority queue of generic keys.
 *  It supports the usual <em>insert</em> and <em>delete-the-minimum</em>
 *  operations, along with methods for peeking at the minimum key,
 *  testing if the priority queue is empty, and iterating through
 *  the keys.
 *  <p>
 *  This implementation uses a <em>binary heap</em>.
 *  The <em>insert</em> and <em>delete-the-minimum</em> operations take
 *  &Theta;(log <em>n</em>) amortized time, where <em>n</em> is the number
 *  of elements in the priority queue. This is an amortized bound
 *  (and not a worst-case bound) because of array resizing operations.
 *  The <em>min</em>, <em>size</em>, and <em>is-empty</em> operations take
 *  &Theta;(1) time in the worst case.
 *  Construction takes time proportional to the specified capacity or the
 *  number of items used to initialize the data structure.
 *  <p>
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Key> the generic type of key on this priority queue
 */
public class LinkedPQ<Key> {
    private Node first;                    // store items at indices 1 to n
    private Node last;
    private int n;                       // number of items on priority queue
    private Comparator<Key> comparator;  // optional comparator

    // helper linked list class
    private class Node {
        private Key val;
        private Node up;
        private Node left;
        private Node right;
    }

    /**
     * Initializes an empty priority queue with the given initial capacity.
     *
     * @param  initCapacity the initial capacity of this priority queue
     */
    public LinkedPQ() {
        n = 0;
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return {@code true} if this priority queue is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of keys on this priority queue.
     *
     * @return the number of keys on this priority queue
     */
    public int size() {
        return n;
    }

    private void incLast() {
        Node latest = new Node();

        if (isEmpty()) {
            first = latest;
            last = latest;
            n++;
            return;
        }

        Node ptr = last;
        if (ptr.up != null && ptr == ptr.up.left){ //slot to right
            ptr.up.right = latest;
            latest.up = ptr.up;
        } else if (ptr.up != null && ptr.up.up != null && ptr.up == ptr.up.up.left) { // jump to next branch
            ptr.up.up.right.left = latest;
            latest.up = ptr.up.up.right;
        }
        else {
            ptr = first;
            while (ptr.left != null) ptr = ptr.left;
            ptr.left = latest;
            latest.up = ptr;
        }
        last = latest;
        n++;
    }

    private void decLast() {
        if (n==1){
            first = null;
            last = null;
            n--;
            return;
        }
        Node ptr = last;
        if (ptr.up != null && ptr == ptr.up.right){
            last = ptr.up.left;
            ptr.up.right = null;
        } else if (ptr.up != null && ptr.up.up != null && ptr.up == ptr.up.up.right) {
            last = ptr.up.up.left.right;
            ptr.up.left = null;
        } else {
            Node x = first;
            while (x.right != null) x = x.right;
            last = x;
            ptr.up.left = null;
        }
        n--;
    }

    /**
     * Returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return first.val;
    }

    /**
     * Adds a new key to this priority queue.
     *
     * @param  x the key to add to this priority queue
     */
    public void insert(Key x) {

        // add x, and percolate it up to maintain heap invariant
        incLast();
        last.val = x;
        swim(last);
        StdOut.printf("Current last is %s \n", last.val);
        StdOut.printf("Current first is %s \n", first.val);
        assert isMinHeap();
    }

    /**
     * Removes and returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key delMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");

        StdOut.printf("Current last is %s \n", last.val);
        StdOut.printf("Current first is %s \n", first.val);

        Key min = first.val;
        exch(first, last);
        decLast();
        if (!isEmpty()){
            sink(first);
        }

        assert isMinHeap();
        return min;
    }


    /***************************************************************************
     * Helper functions to restore the heap invariant.
     ***************************************************************************/

    private void swim(Node k) {
        while (k.up != null && greater(k.up.val, k.val)) {
            exch(k, k.up);
            k = k.up;
        }
    }

    private void sink(Node k) {
        while (k.left != null) {
            Node child = k.left;
            if (k.right != null && greater(k.left.val, k.right.val)) child = k.right;
            if (!greater(k.val, child.val)) break;
            exch(k, child);
            k = child;
        }
    }

    /***************************************************************************
     * Helper functions for compares and swaps.
     ***************************************************************************/
    private boolean greater(Key i, Key j) {
        if (comparator == null) {
            return ((Comparable<Key>) i).compareTo(j) > 0;
        }
        else {
            return comparator.compare(i, j) > 0;
        }
    }

    private void exch(Node i, Node j) {
        Key swap = i.val;
        i.val = j.val;
        j.val = swap;
    }

    // is pq[1..n] a min heap?
    private boolean isMinHeap() {
        if (first == null) return true;
        else return isMinHeapOrdered(first);
    }

    // is subtree of pq[1..n] rooted at k a min heap?
    private boolean isMinHeapOrdered(Node k) {
        if (k.left != null && greater(k.val, k.left.val))  return false;
        if (k.right != null && greater(k.val, k.right.val)) return false;
        return isMinHeapOrdered(k.left) && isMinHeapOrdered(k.right);
    }



    /**
     * Unit tests the {@code MinPQ} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        LinkedPQ<String> pq = new LinkedPQ<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.print(pq.delMin() + "\n");
        }
        StdOut.println("(" + pq.size() + " left on pq)");
    }

}

/******************************************************************************
 *  Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
