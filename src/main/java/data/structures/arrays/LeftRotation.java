package data.structures.arrays;

import java.util.Scanner;

public class LeftRotation {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int size = sc.nextInt();
        int rotations = sc.nextInt();
        int[] originalArray = new int[size];
        // read array
        for (int i = 0; i < size; i++) {
            originalArray[i] = sc.nextInt();
        }
        // print rotated array
        int firstElement = rotations % size;
        for (int i = 0; i < size; i++) {
            int currentElement = (firstElement + i) % size;
            System.out.print(originalArray[currentElement] + " ");
        }
    }

}
