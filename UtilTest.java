import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class UtilTest {
    
    @Test
    public void test_random_currency() {
        String[] predicted_values = { "GBP", "EUR", "JPY", "CHF", "USD", "ARS", "AED", "AUD" };
        List<String> list = Arrays.asList(predicted_values);
        for (int i = 0; i < 10000; i++) {
            String actual = Util.random_currency();
            assertTrue(list.contains(actual));
        }
    }

    @Test
    public void test_nearest_cent() {
        //No whole number rounding
        assertTrue("Error nearest cent: whole number rounding", 15 == Util.nearest_cent(15));
        //No cent rounding
        assertTrue("Error nearest cent: cent number rounding", 20.98 == Util.nearest_cent(20.98));
        //Rounding up
        assertTrue("Error nearest cent: rounding up", 102.82 == Util.nearest_cent(102.819124));
        //Rounding down
        assertTrue("Error nearest cent: rounding down", 34.80 == Util.nearest_cent(34.803051));
    }

    @Test
    public void test_random_single() {
        //Checking positive range
        double maximum = 86213.01;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random(maximum);
            assertTrue("Error random maximum: not in range (Positive)", actual >= 0 && actual <= maximum);
        }
        //Checking negative range 
        double minimum = -1052.19;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random(minimum);
            assertTrue("Error random maximum: not in range (Negative)", actual <= 0 && actual >= minimum);
        }
        //Checking 0
        assertTrue("Error random maximum: not equivalent", Util.random(maximum) == 0);
    }

    @Test
    public void test_random_range() {
        //Checking positive range
        double maximum = 11725.01;
        double minimum = 124.125;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random(maximum, minimum);
            assertTrue("Error random maximum: not in range (Positive)", actual >= minimum && actual <= maximum);
            actual = Util.random(minimum, maximum);
            assertTrue("Error random maximum: not in range (Positive)", actual >= minimum && actual <= maximum);
        }
        //Checking negative range 
        maximum = -192.512;
        minimum = -18325.19;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random(maximum, minimum);
            assertTrue("Error random maximum: not in range (Positive)", actual >= minimum && actual <= maximum);
            actual = Util.random(minimum, maximum);
            assertTrue("Error random maximum: not in range (Positive)", actual >= minimum && actual <= maximum);
        }
        //Checking positive and negativ range
        maximum = 9124.01;
        minimum = -512.19;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random(maximum);
            double actual = Util.random(maximum, minimum);
            assertTrue("Error random maximum: not in range (Positive)", actual >= minimum && actual <= maximum);
            actual = Util.random(minimum, maximum);
            assertTrue("Error random maximum: not in range (Positive)", actual >= minimum && actual <= maximum);
        }
        //Checking equivalent
        assertTrue("Error random maximum: not equivalent", Util.random(567, 567) == 567);
    }
}