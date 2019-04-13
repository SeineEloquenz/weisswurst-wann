package de.seine_eloquenz.weisswurstwann;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Ignore
    @Test
    public void test() {
        boolean weisswurst = (new WeisswurstInfo()).checkWeisswurstStatus();
        Assert.assertTrue(weisswurst);
    }
}