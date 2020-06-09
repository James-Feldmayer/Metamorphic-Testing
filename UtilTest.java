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
            assertTrue("Error random range: not in range (Positive)", actual >= minimum && actual <= maximum);
            actual = Util.random(minimum, maximum);
            assertTrue("Error random range: not in range (Positive)", actual >= minimum && actual <= maximum);
        }
        //Checking negative range 
        maximum = -192.512;
        minimum = -18325.19;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random(maximum, minimum);
            assertTrue("Error random range: not in range (Positive)", actual >= minimum && actual <= maximum);
            actual = Util.random(minimum, maximum);
            assertTrue("Error random range: not in range (Positive)", actual >= minimum && actual <= maximum);
        }
        //Checking positive and negative range
        maximum = 9124.01;
        minimum = -512.19;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random(maximum, minimum);
            assertTrue("Error random range: not in range (Positive)", actual >= minimum && actual <= maximum);
            actual = Util.random(minimum, maximum);
            assertTrue("Error random range: not in range (Positive)", actual >= minimum && actual <= maximum);
        }
        //Checking equivalent
        assertTrue("Error random range: not equivalent", Util.random(567, 567) == 567);
    }

    @Test
    public void test_money_single() {
        //Checking positive range and 2 d.p.
        double maximum = 86213.01;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random_money(maximum);
            assertTrue("Error money maximum: not in range (Positive)", actual >= 0 && actual <= maximum);
            assertTrue("Error money maximum: not 2 decimal places", (actual * 100) % 1 == 0);
        }
        //Checking negative range and 2 d.p.
        double minimum = -1052.19;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random_money(minimum);
            assertTrue("Error money maximum: not in range (Negative)", actual <= 0 && actual >= minimum);
            assertTrue("Error money maximum: not 2 decimal places", (actual * 100) % 1 == 0);
        }
        //Checking equavlent to 0
        assertTrue("Error money maximum: not equivalent", Util.random_money(maximum) == 0);
    }

    @Test
    public void test_money_range() {
        //Checking positive range and 2 d.p.
        double maximum = 79125.412;
        double minimum = 512.871;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random(maximum, minimum);
            assertTrue("Error money range: not in range (Positive)", actual >= minimum && actual <= maximum);
            actual = Util.random(minimum, maximum);
            assertTrue("Error money range: not in range (Positive)", actual >= minimum && actual <= maximum);
            assertTrue("Error money range: not 2 decimal places", (actual * 100) % 1 == 0);
        }
        //Checking negative range and 2 d.p.
        maximum = -50.51;
        minimum = -5678.18;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random_money(maximum, minimum);
            assertTrue("Error money range: not in range (Positive)", actual >= minimum && actual <= maximum);
            actual = Util.random_money(minimum, maximum);
            assertTrue("Error money range: not in range (Positive)", actual >= minimum && actual <= maximum);
            assertTrue("Error money range: not 2 decimal places", (actual * 100) % 1 == 0);
        }
        //Checking positive and negative range and 2 d.p.
        maximum = 8752.01;
        minimum = -4654.92;
        for (int i = 0; i < 10000; i++) {
            double actual = Util.random(maximum, minimum);
            assertTrue("Error money range: not in range (Positive)", actual >= minimum && actual <= maximum);
            actual = Util.random(minimum, maximum);
            assertTrue("Error money range: not in range (Positive)", actual >= minimum && actual <= maximum);
        }
        //Checking equivalence
        assertTrue("Error random range: not equivalent", Util.random(246, 246) == 246);
    }

    @Test
    public void test_near_equal() {
        //Checking equivalence
        assertTrue("Error near equal: not equivalent", Util.near_equal(1251.14, 1251.14, 1));
        //Checking past threshold
        assertFalse("Error near equal: equivalent", Util.near_equal(7129.107, 8120.145, 0.1));
        //Checking inverse
        assertTrue("Error near equal: bad order of parameters", Util.near_equal(9172.01, 9172.05, 0.05));
        assertTrue("Error near equal: bad order of parameters", Util.near_equal(9172.05, 9172.01, 0.05));
        //Hitting the threshold
        assertFalse("Error near equal: threshold equal", Util.near_equal(6932, 6932.01, 0.1));
    }
}