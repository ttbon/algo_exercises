public class ex1_1_2 {
    public static void main(String[] args) {

        System.out.printf("\n (1 + 2.236)/2 = %f", (1 + 2.236) / 2);
        System.out.print("\n Is double? " + Double.class.isInstance((1 + 2.236) / 2));
        System.out.println();

        System.out.printf("\n 1 + 2 + 3 + 4.0 = %f", 1 + 2 + 3 + 4.0);
        System.out.print("\n Is double? " + Double.class.isInstance(1 + 2 + 3 + 4.0));
        System.out.println();

        System.out.printf("\n 4.1 >= 4 = %s", 4.1 >= 4);
        System.out.print("\n Is boolean? " + Boolean.class.isInstance(4.1 >= 4));
        System.out.println();

        System.out.printf("\n 1 + 2 + \"3\" = %s", 1 + 2 + "3");
        System.out.print("\n Is string? " + String.class.isInstance(1 + 2 + "3"));
        System.out.println();


    }
}
