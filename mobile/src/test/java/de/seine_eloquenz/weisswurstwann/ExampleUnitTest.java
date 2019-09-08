package de.seine_eloquenz.weisswurstwann;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Ignore
    @Test
    public void test() throws IOException {
        boolean weisswurst = (new WeisswurstInfo()).checkWeisswurstStatusTomorrow();
    }

    @Ignore
    @Test
    public void testWeek() throws IOException {
        WWWeek ww = (new WeisswurstInfo()).checkWeisswurstStatusWeek();
    }
}