/******************************************************************************
 *  Compilation:  javac ArrayBST.java
 *  Execution:    java ArrayBST
 *  Dependencies: StdIn.java StdOut.java Queue.java
 *  Data files:   https://algs4.cs.princeton.edu/32bst/tinyST.txt  
 *
 *  A symbol table implemented with a binary search tree.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java ArrayBST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 ******************************************************************************/

package edu.princeton.cs.algs4;

import java.util.NoSuchElementException;

/**
 *  The {@code ArrayBST} class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides ordered methods for finding the <em>minimum</em>,
 *  <em>maximum</em>, <em>floor</em>, <em>select</em>, <em>ceiling</em>.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *  <p>
 *  This implementation uses an (unbalanced) <em>binary search tree</em>.
 *  The <em>put</em>, <em>contains</em>, <em>remove</em>, <em>minimum</em>,
 *  <em>maximum</em>, <em>ceiling</em>, <em>floor</em>, <em>select</em>, and
 *  <em>rank</em>  operations each take &Theta;(<em>n</em>) time in the worst
 *  case, where <em>n</em> is the number of key-value pairs.
 *  The <em>size</em> and <em>is-empty</em> operations take &Theta;(1) time.
 *  The keys method takes &Theta;(<em>n</em>) time in the worst case.
 *  Construction takes &Theta;(1) time.
 *  <p>
 *  For alternative implementations of the symbol table API, see {@link ST},
 *  {@link BinarySearchST}, {@link SequentialSearchST}, {@link RedBlackArrayBST},
 *  {@link SeparateChainingHashST}, and {@link LinearProbingHashST},
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/32bst">Section 3.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class ArrayBST<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int[] left;
    private int[] right;
    private int[] size;
    private int root;
    private int n = 0;
    private int alloc;

    /**
     * Initializes an empty symbol table.
     */
    public ArrayBST(int alloc) {
        keys = new Key[alloc];
        vals = new Value[alloc]
        left = new int[alloc];
        right = new int[alloc];
        size = new int[alloc];
        this.alloc = alloc;
    }

    private int newNode(Key key, Value val, int size){
        int i = 0;
        while (keys[i] != null) {
            i++;
            if (i < alloc) throw new IllegalArgumentException("out of Node space");
        }

        keys[i] = key;
        vals[i] = val;
        size[i] = size;
        n++;

        return i;
    }


    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in ArrayBST rooted at x
    private int size(int idx) {
        if (size[idx] == null) return 0;
        else return size[idx];
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(int idx, Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (idx == null) return null;
        int cmp = key.compareTo(keys[idx]);
        if      (cmp < 0) return get(left[idx], key);
        else if (cmp > 0) return get(right[idx], key);
        else              return keys[idx];
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    private int put(int idx, Key key, Value val) {
        if (idx == null) return newNode(key, val, 1);
        int cmp = key.compareTo(keys[idx]);
        if      (cmp < 0) left[idx]  = put(left[idx],  key, val);
        else if (cmp > 0) right[idx] = put(right[idx], key, val);
        else              vals[idx]   = val;
        size[idx] = 1 + size[left[idx]] + size[right[idx]];
        return idx;
    }


    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        assert check();
    }

    private int deleteMin(int idx) {
        if (left[idx] == null){ //reached min node
            int right_idx = right[idx];
            delete(idx);
            return right_idx;
        }
        left[idx] = deleteMin(left[idx]);
        size[idx] = size(left[idx]) + size(right[idx]) + 1;
        return idx;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
        assert check();
    }

    private int deleteMax(Node x) {
        if (right[idx] == null) {
            int left_idx = left[idx];
            delete(idx);
            return left_idx;
        }
        right[idx] = deleteMax(right[idx]);
        size[idx] = size(left[idx]) + size(right[idx]) + 1;
        return idx;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
        assert check();
    }

    private int delete(int idx, Key key) {
        if (idx == null) return null;

        int cmp = key.compareTo(keys[idx]);
        if      (cmp < 0) left[idx]  = delete(left[idx],  key);
        else if (cmp > 0) right[idx] = delete(right[idx], key);
        else {
            if (right[idx] == null) return left[idx];
            if (left[idx]  == null) return right[idx];
            fill_idx = min(right[idx]);
            keys[idx] = keys[fill_idx];
            vals[idx] = vals[fill_idx];

            // delete fill node
            right[idx] = deleteMin(right[idx]); //technically don't need reassignment
        }
        size[idx] = size(left[idx]) + size(right[idx]) + 1;
        return idx;
    }

    private void delete(int idx){
        keys[idx] = null;
        vals[idx] = null;
        right[idx] = null;
        left[idx] = null;
        size[idx] = null;
    }



    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return keys[min(root)];
    }

    private int min(int idx) {
        if (left[idx] == null) return idx;
        else                return min(left[idx]);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return keys[max(root)];
    }

    private int max(int idx) {
        if (right[idx] == null) return idx;
        else                 return max(right[idx]);
    }

    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        int idx = floor(root, key);
        if (idx == null) throw new NoSuchElementException("argument to floor() is too small");
        else return keys[idx];
    }

    private int floor(int idx, Key key) {
        if (idx == null) return null;
        int cmp = key.compareTo(keys[idx]);
        if (cmp == 0) return idx;
        if (cmp <  0) return floor(left[idx], key);
        int t = floor(right[idx], key);
        if (t != null) return t;
        else return idx;
    }

    public Key floor2(Key key) {
        Key x = floor2(root, key, null);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x;

    }

    private Key floor2(Node x, Key key, Key best) {
        if (x == null) return best;
        int cmp = key.compareTo(keys[idx]);
        if      (cmp  < 0) return floor2(left[idx], key, best);
        else if (cmp  > 0) return floor2(right[idx], key, keys[idx]);
        else               return keys[idx];
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param  key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        int idx = ceiling(root, key);
        if (idx == null) throw new NoSuchElementException("argument to floor() is too large");
        else return keys[idx];
    }

    private int ceiling(int idx, Key key) {
        if (idx == null) return null;
        int cmp = key.compareTo(keys[idx]);
        if (cmp == 0) return idx;
        if (cmp < 0) {
            int t = ceiling(left[idx], key);
            if (t != null) return t;
            else return idx;
        }
        return ceiling(right[idx], key);
    }

    /**
     * Return the key in the symbol table of a given {@code rank}.
     * This key has the property that there are {@code rank} keys in
     * the symbol table that are smaller. In other words, this key is the
     * ({@code rank}+1)st smallest key in the symbol table.
     *
     * @param  rank the order statistic
     * @return the key in the symbol table of given {@code rank}
     * @throws IllegalArgumentException unless {@code rank} is between 0 and
     *        <em>n</em>–1
     */
    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    // Return key in ArrayBST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private Key select(int idx, int rank) {
        if (idx == null) return null;
        int leftSize = size(left[idx]);
        if      (leftSize > rank) return select(left[idx],  rank);
        else if (leftSize < rank) return select(right[idx], rank - leftSize - 1);
        else                      return keys[idx];
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param  key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    // Number of keys in the subtree less than key.
    private int rank(Key key, int idx) {
        if (idx == null) return 0;
        int cmp = key.compareTo(keys[idx]);
        if      (cmp < 0) return rank(key, left[idx]);
        else if (cmp > 0) return 1 + size(left[idx]) + rank(key, right[idx]);
        else              return size(left[idx]);
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<Key> keys() {
        if (isEmpty()) return new Queue<Key>();
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(int idx, Queue<Key> queue, Key lo, Key hi) {
        if (idx == null) return;
        int cmplo = lo.compareTo(keys[idx]);
        int cmphi = hi.compareTo(keys[idx]);
        if (cmplo < 0) keys(left[idx], queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(keys[idx]);
        if (cmphi > 0) keys(right[idx], queue, lo, hi);
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return the number of keys in the symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    /**
     * Returns the height of the ArrayBST (for debugging).
     *
     * @return the height of the ArrayBST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }
    private int height(int idx) {
        if (idx == null) return -1;
        return 1 + Math.max(height(left[idx]), height(right[idx]));
    }

    /**
     * Returns the keys in the ArrayBST in level order (for debugging).
     *
     * @return the keys in the ArrayBST in level order traversal
     */
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(keys[idx]);
            queue.enqueue(left[idx]);
            queue.enqueue(right[idx]);
        }
        return keys;
    }

    /*************************************************************************
     *  Check integrity of ArrayBST data structure.
     ***************************************************************************/
    private boolean check() {
        if (!isArrayBST())            StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        return isArrayBST() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isArrayBST() {
        return isArrayBST(root, null, null);
    }

    // is the tree rooted at x a ArrayBST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isArrayBST(int idx, Key min, Key max) {
        if (idx == null) return true;
        if (min != null && keys[idx].compareTo(min) <= 0) return false;
        if (max != null && keys[idx].compareTo(max) >= 0) return false;
        return isArrayBST(left[idx], min, keys[idx]) && isArrayBST(right[idx], keys[idx], max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (idx == null) return true;
        if (size[idx] != size(left[idx]) + size(right[idx]) + 1) return false;
        return isSizeConsistent(left[idx]) && isSizeConsistent(right[idx]);
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }


    /**
     * Unit tests the {@code ArrayBST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        ArrayBST<String, Integer> st = new ArrayBST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        for (String s : st.levelOrder())
            StdOut.println(s + " " + st.get(s));

        StdOut.println();

        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
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
