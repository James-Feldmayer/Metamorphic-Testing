import static org.junit.Assert.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

public class WebTest {

    @Test
    public void test_calcNet_calculate() {
        CurrencyCalculator testCal = new CalculateNet();
        
        //Checks result
        assertTrue("Error calculateNet class: Incorrect output", testCal.calculate(1, "AUD", "AUD") > 0);
    }

    @Test
    public void test_XRate_calculate() {
        CurrencyCalculator testCal = new XRate();
        
        //Checks result
        assertTrue("Error calculateNet class: Incorrect output", testCal.calculate(1, "AUD", "AUD") > 0);
    }

    @Test
    public void test_html_scape() {
        Web test = new CalculateNet();
        
        //Single line input
        String exHTML = "<span>12<b>34</b>5</span>6789";
        Reader inputHTML = new StringReader(exHTML);
        BufferedReader br = new BufferedReader(inputHTML);
        String result = test.html_scape(br, "<span>");

        assertTrue("Error test tag: Incorrect output", result.equals("12345"));
        
        //Multiline input
        exHTML = "<span>12\n<b>\n34\n</b>5\n</span>\n6789";
        inputHTML = new StringReader(exHTML);
        br = new BufferedReader(inputHTML);
        result = test.html_scape(br, "<span>");
        assertTrue("Error test tag: Incorrect output", result.equals("12345"));
        
        //Empty line input and zero results
        exHTML = "";
        inputHTML = new StringReader(exHTML);
        br = new BufferedReader(inputHTML);
        result = test.html_scape(br, "<span>");
        assertTrue("Error test tag: Incorrect output", result.isEmpty());
    }
    
}
