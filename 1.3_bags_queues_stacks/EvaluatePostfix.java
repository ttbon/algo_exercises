import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class EvaluatePostfix {
    public static void EvaluatePostfix() {
        Stack<Double> vals = new Stack<Double>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            switch (s) {
                case "+":
                    vals.push(vals.pop() + vals.pop());
                    break;
                case "-":
                    vals.push(vals.pop() - vals.pop());
                    break;
                case "*":
                    vals.push(vals.pop() * vals.pop());
                    break;
                case "/":
                    vals.push(vals.pop() / vals.pop());
                    break;
                default:
                    vals.push(Double.parseDouble(s));
            }
        }
        StdOut.println(vals.pop());
    }

    public static void main(String[] args) {
        EvaluatePostfix();
    }
}
