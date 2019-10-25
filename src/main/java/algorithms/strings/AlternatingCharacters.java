package algorithms.strings;

import java.util.Scanner;

public class AlternatingCharacters {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int queries = sc.nextInt();
        for (int i  = 0; i < queries; i++) {
            solve(sc.next());
        }
    }

    private static void solve(String text) {
        char[] elements = text.toCharArray();
        if (elements.length == 0 || elements.length == 1) {
            System.out.println("0");
            return;
        }
        int toErase = 0;
        char lastChar = elements[0];
        for (int i = 1; i < elements.length; i++) {
            char current = elements[i];
            if (current == lastChar) {
                toErase++;
            } else {
                lastChar = current;
            }
        }
        System.out.println(toErase);
    }

}
