import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ex1_1_21 {
    public static void main(String[] args) {

        String name;
        double num1;
        double num2;
        double quotient;


        while (!StdIn.isEmpty()) {
            name = StdIn.readString();
            num1 = StdIn.readDouble();
            num2 = StdIn.readDouble();
            quotient = num1 / num2;
            StdOut.println(
                    name + "\t" + num1 + "\t" + num2 + "\t" + quotient
            );
        }
    }
}
