package codility.lessons;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlatformTestTest {

    private final PlatformTest underTest = new PlatformTest();

    @Test
    public void allNumbersExist() {
        int[] array = new int[]{1, 2, 3, 4, 5};

        int solution = underTest.solution(array);

        assertEquals(6, solution);
    }

    @Test
    public void allNumbersAreNegative() {
        int[] array = new int[]{-1, -3};

        int solution = underTest.solution(array);

        assertEquals(1, solution);
    }

    @Test
    public void lackNumberInTheMiddle() {
        int[] array = new int[]{1, 3, 4, 5};

        int solution = underTest.solution(array);

        assertEquals(2, solution);
    }

}