package codility.adyen;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExerciseOneTest {

    private ExerciseOne exerciseOne = new ExerciseOne();

    @Test
    public void cases() {
        test(new int[]{1,1, 3,3, 5,5,5,5,5,5,5,5}, 2, 8);
        test(new int[]{1,1, 3,3, 5,5,5,5,5,5,5,5}, 8, 10);
        test(new int[]{1,1,1,1,1, 3, 5,5,5,5,5,5,5,5}, 8, 13);
        test(new int[]{1,1,1,1,1, 3, 5,5,5,5,5,5,5,5}, 0, 8);
        test(new int[]{1,1, 3,3,3,3, 5,5,5,5,5,5,5,5}, 10, 12);
        test(new int[]{1,1, 3,3,3,3,3, 5,5,5,5,5,5,5,5}, 10, 13);

        test(new int[]{1, 2,2, 3,3,3, 4,4,4,4, 5,5,5,5,5, 6,6,6,6,6,6, 7,7,7,7,7,7,7}, 1, 7);
    }

    private void test(int[] array, int hammerAttempts, int expectedResult) {
        assertEquals(expectedResult, exerciseOne.solution(array, hammerAttempts));
    }

}
/*
[[1, 1, 3, 3, 5,5,5,5,5,5,5,5], 2]
[[1,1, 3,3, 5,5,5,5,5,5,5,5], 8]
[[1,1,1,1,1, 3, 5,5,5,5,5,5,5,5], 8]
[[1,1,1,1,1, 3, 5,5,5,5,5,5,5,5], 0]
[[1,1, 3,3,3,3, 5,5,5,5,5,5,5,5], 10]
[[1,1, 3,3,3,3,3, 5,5,5,5,5,5,5,5], 10]
 */