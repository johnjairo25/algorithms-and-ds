package codility.lessons;

import org.junit.Assert;
import org.junit.Test;

public class ConsecutiveIntegersTest {

    private ConsecutiveIntegers underTest = new ConsecutiveIntegers();

    @Test
    public void test() {
        Assert.assertEquals(0, underTest.solution(32));
        Assert.assertEquals(4, underTest.solution(529));
        Assert.assertEquals(1, underTest.solution(20));
        Assert.assertEquals(5, underTest.solution(1041));
        Assert.assertEquals(3, underTest.solution(1162));
    }

    @Test
    public void verifyLogic() {
        Assert.assertEquals(0, underTest.solution("1000"));
        Assert.assertEquals(3, underTest.solution("1000101"));
        Assert.assertEquals(3, underTest.solution("1010001"));
        Assert.assertEquals(1, underTest.solution("101000"));
        Assert.assertEquals(6, underTest.solution("10000001"));
        Assert.assertEquals(2, underTest.solution("101001000"));
    }

}
