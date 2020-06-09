
import static org.junit.Assert.*;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/*
void test_random_currency()
void test_nearest_cent()
void test_random_single()
void test_random_range()
void test_near_equal()
void test_to_int()
*/

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
    public void test_to_int() {
        //
        assertTrue("no whole number rounding", 15 == Util.to_int(15));

        //
        assertTrue("rounds down", 0 == Util.to_int(0.12));

        //
        assertTrue("always rounds down", 13 == Util.to_int(13.9));
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
        assertTrue("Error random maximum: not equivalent", Util.random(0) == 0);
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
    public void test_near_equal() {
        //true equivalence
        assertTrue("equivalent", Util.near_equal(1251.14, 1251.14, 1));
        
        //inverse
        assertTrue("indiffernt to the order of these parameters", Util.near_equal(9172.01, 9172.05, 0.05));
        assertTrue("indiffernt to the order of these parameters", Util.near_equal(9172.05, 9172.01, 0.05));

        //intolerable error
        assertFalse("outside the range of tolerable error", Util.near_equal(7129.107, 8120.145, 0.1));

        //tolerable error
        assertTrue("equivalent to tolerable error", Util.near_equal(0.01, 0.02, 0.01));    
        assertTrue("within the range of tolerable error", Util.near_equal(1115.29, 1115.20, 0.10));

        //opposites
        assertFalse("not equivalent", Util.near_equal(100.10, -100.10, 0.01));

        //positive and negative 
        assertFalse("not equivalent", Util.near_equal(112.01, -13.00, 0.01));

        //negative and positive 
        assertFalse("not equivalent", Util.near_equal(-112.01, 13.00, 0.01));
        
        //negative and negative 
        assertTrue("equivalent", Util.near_equal(-90.23, -90.24, 0.01));
    }
}
