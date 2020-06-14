import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class TwoStackQueue<Item> {
    int size = 0;
    Stack<Item> s1 = new Stack<Item>();
    Stack<Item> s2 = new Stack<Item>();

    public void enqueue(Item item){
        s1.push(item);
        size++;
    }

    private void shift(){
        while (!s1.isEmpty()){
            s2.push(s1.pop());
        }
    }

    public Item dequeue(){
        if(s2.isEmpty()) shift();
        size--;
        return s2.pop();
    }

    public static void main(String[] args){
        TwoStackQueue<Integer> a = new TwoStackQueue<Integer>();
        for (int i=0; i<5; i++){
            a.enqueue(i);
        }
        for (int i=0; i<5; i++){
            StdOut.println(a.dequeue());
        }
    }
}