
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;  
import java.util.HashSet;
import java.util.Set;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

public class OracleTest 
{

    private Set<String> read() { 
        Set<String> currency_codes = new HashSet<>();

        try{
            final BufferedReader br = new BufferedReader(new FileReader("./currency.csv"));
            String line = "";
            while ((line = br.readLine()) != null) {
                final String curreny_code = line.split(",")[0];
                currency_codes.add(curreny_code);
            }
            br.close();
        }
        catch(IOException ex) {
            System.out.println("Could not find file");
        }

        return currency_codes;
    }  

    /*
    @Test
    public void testRandomIndex() //testRandom 
    {
        final Oracle oracle = new Oracle();

        Set<Integer> actual = new HashSet<>();

        for(int i = 0; i < 10000; i++) {        
            actual.add(Util.random_index());
        }

        //System.out.println(actual.toString());

        assertEquals(actual.size(), read().size());
    }

    @Test
    public void testRandomElement() //testRandom 
    {
        final Oracle oracle = new Oracle();
    
        Set<String> actual = new HashSet<>();
    
        for(int i = 0; i < 10000; i++) {        
            actual.add(Util.random_code());
        }

        //System.out.println(actual);

        assertEquals(actual, read());
    }
    */

};
