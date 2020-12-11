package codility.lessons;

public class ConsecutiveIntegers {

    public int solution(int N) {
        return solution(Integer.toBinaryString(N));
    }

    public int solution(String binaryRepresentation) {
        char[] binaryArray = binaryRepresentation.toCharArray();
        int max = 0;
        int count = 0;
        boolean lastZero = false;
        for (char element : binaryArray) {
            boolean isZero = '0' == element;
            if (isZero) {
                count++;
            } else {
                max = lastZero && max < count ? count : max;
                count = 0;
            }
            lastZero = isZero;
        }
        return max;
    }

}
