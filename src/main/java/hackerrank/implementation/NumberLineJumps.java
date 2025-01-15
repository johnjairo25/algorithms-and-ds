package hackerrank.implementation;

public class NumberLineJumps {

    public static String kangaroo(int x1, int v1, int x2, int v2) {
        if (v1 > v2 && (x2 - x1) % (v1 - v2) == 0) {
            return "YES";
        } else {
            return "NO";
        }
    }

    public static void main(String[] args) {
        System.out.println(kangaroo(0, 3, 4, 2));
    }

}
