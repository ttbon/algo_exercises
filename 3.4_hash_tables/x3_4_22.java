import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.StdOut;

public class x3_4_22 {

        private class Point2D{
                private double x;
                private double y;

                Point2D(double x, double y) {
                        this.x = x;
                        this.y = y;
                }

                @Override
                public int hashCode() {
                        int hash = 17;
                        hash = 31 * hash + ((Double) x).hashCode();
                        hash = 31 * hash + ((Double) y).hashCode();
                        return hash;
                }
        }

        private class Interval{
                private double min;
                private double max;

                Interval(double min, double max){
                        this.min = min;
                        this.max = max;
                }

                @Override
                public int hashCode(){
                        int hash = 17;
                        hash = 31*hash + ((Double) min).hashCode();
                        hash = 31*hash + ((Double) max).hashCode();
                        return hash;
                }
        }

        private class Interval2D{
                private Interval x;
                private Interval y;

                Interval2D(Interval x, Interval y){
                        this.x = x;
                        this.y = y;
                }

                @Override
                public int hashCode(){
                        int hash = 17;
                        hash = 31*hash + (x == null ? 0 : x.hashCode() );
                        hash = 31*hash + (y == null ? 0 : y.hashCode() );
                        return hash;
                }
        }

        private class Date{
                private int year;
                private int month;
                private int day;

                Date(int year, int month, int day){
                        this.year = year;
                        this.month = month;
                        this.day = day;
                }

                @Override
                public int hashCode(){
                        int hash = 17;
                        hash = 31*hash + year;
                        hash = 31*hash + month;
                        hash = 31*hash + day;
                        return hash;
                }
        }

        public static void main(String[] args) {
                x3_4_22 exercise22 = new x3_4_22();
                Point2D point2D = exercise22.new Point2D(20, 13);
                StdOut.println("Point 2D hash code: " + point2D.hashCode());

                Interval interval = exercise22.new Interval(1, 999);
                StdOut.println("Interval hash code: " + interval.hashCode());

                Interval interval1D1 = exercise22.new Interval(10.2, 30.20);
                Interval interval1D2 = exercise22.new Interval(2, 1000);
                Interval2D interval2D = exercise22.new Interval2D(interval1D1, interval1D2);
                StdOut.println("Interval 2D hash code: " + interval2D.hashCode());

                Date date = exercise22.new Date(18, 4, 1989);
                StdOut.println("Date hash code: " + date.hashCode());
        }

}