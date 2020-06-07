
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;

public class Client 
{
    public static void main(String[] args) 
    {   

        //String.format("https://www.x-rates.com/calculator/?amount=%s&from=%s&to=%s", "60.06", "AUD", "GBP")
        //String.format("https://www.calculator.net/currency-calculator.html?eamount=%s&efrom=%s&eto=%s&type=1&x=0&y=0", "60.06", "AUD", "GBP")

        System.out.println(String.format("https://www.xe.com/currencyconverter/convert/?Amount=%s&From=%s&To=%s", "60.06", "AUD", "GBP")); 

        //Oracle oracle = new Oracle();
        //oracle.start();
    }    
}

/*
Calculator - SUT
Client - Main method
Oracle - Our solution
OracleTest - TDD (junit) our solution
*/ 
