import static org.junit.Assert.*;
import org.junit.Test;

public class OldWebTest {
    @Test
    public void test_calcNet_calculate() {
        //Checks result
        assertTrue("Error calculateNet class: Incorrect output", Double.parseDouble(OldWeb.Xrates("1", "AUD", "AUD")) > 0);
    }

    @Test
    public void test_XRate_calculate() {
        CurrencyCalculator testCal = new XRate();
        
        //Checks result
        assertTrue("Error calculateNet class: Incorrect output", Double.parseDouble(OldWeb.calcNet("1", "AUD", "AUD")) > 0);
    }
}