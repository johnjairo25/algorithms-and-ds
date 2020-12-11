package codility.lessons;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OddOccurrences {

    public int solution(int[] A) {
        Set<Integer> existingNumbers = new HashSet<>();
        Arrays.stream(A).forEach(element -> {
            if (!existingNumbers.contains(element)) {
                existingNumbers.add(element);
            } else {
                existingNumbers.remove(element);
            }
        });
        return (int) existingNumbers.toArray()[0];
    }

}
