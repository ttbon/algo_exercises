import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class rotation {
    public static boolean isRotation(String a, String b) {
        if (a.length() != b.length()) return false;

        String rotated = "";
        for (int i = 0; i < a.length(); i++) {
            rotated = a.substring(i) + a.substring(0, i);
            StdOut.println(rotated);
            if (rotated.equals(b)) return true;
        }
        return false;
    }

    public static boolean isRotation(String a, String b) {
        for ()
    }

    public static void main(String[] args) {

        StdOut.println("Input first string: ");
        String a = StdIn.readString();

        StdOut.println("Input second string: ");
        String b = StdIn.readString();

        StdOut.println("Is " + a + " a rotation of " + b + ": " + isRotation(a, b));
    }
}
