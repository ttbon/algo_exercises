import edu.princeton.cs.algs4.BST;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;


public class gpa{
    public static void main(String[] args){

        // load in letter grade mapping
        In in = new In("gpa_mapping.txt");
        BST<String, Double> grade_map = new BST<String, Double>();
        while (in.hasNextChar()){
            grade_map.put(in.readString(), in.readDouble());
        }

        // read in grads
        int n = 0;
        Double s = 0.0;
        while (!StdIn.isEmpty()){
            String letter_grade = StdIn.readString();
            s += grade_map.get(letter_grade);
            n++;
        }

        // compute gpa
        Double gpa = s/n;
        StdOut.printf("The resulting GPA is %f\n", gpa);

    }
}

