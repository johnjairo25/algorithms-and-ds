package codility.lessons;

public class PlatformTest {

    public int solution(int[] A) {
        int numberOfElements = A.length;
        int maxExpectedNumber = numberOfElements + 1;
        boolean[] existingNumbers = new boolean[maxExpectedNumber + 1];
        for (int element : A) {
            if (element > 0 && element <= maxExpectedNumber) {
                existingNumbers[element] = true;
            }
        }
        for (int numberToCheck = 1; numberToCheck < existingNumbers.length; numberToCheck++) {
            if (!existingNumbers[numberToCheck]) {
                return numberToCheck;
            }
        }
        return maxExpectedNumber;
    }

}
