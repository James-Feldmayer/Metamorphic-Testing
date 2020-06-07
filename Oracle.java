
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

//should be a list of all the functions
/*
Oracle()
start
transative_axiom
ratio_axiom
reversible_axiom
Util.near_equal
random_index
random_code
Util.nearest_cent
*/

public class Oracle 
{
    ArrayList<String> currency_codes = new ArrayList<>();
    Calculator calculator = new Calculator();

    public Oracle() 
    {
        try{
            final BufferedReader br = new BufferedReader(new FileReader("./currency.csv")); //open file currency.csv
            String line = "";
    
            while ((line = br.readLine()) != null) { //read in each line from the file
                final String curreny_code = line.split(",")[0];
                currency_codes.add(curreny_code); //append currency_code
            }

            br.close(); //close file currency.csv
        }
        catch(IOException ex) {
            System.out.println("Could not find file");
        }
    }

    //jamie has the outside interaction worked out?

    //next big task is to add some more unit tests

    //for better diagnostics
    //we can decouple the axioms 
    //rather than passing thing dumb shitty
    //should also change printing and return double instead

    //also better for TDD

    public void start() {

        int failed_cases = 0;

        for(int i = 0; i < 10000; i++) {

            final String currencyA = Util.randomCurrency(); //random currency codes
            final String currencyB = Util.randomCurrency();
            final String currencyC = Util.randomCurrency();

            final double moneyA1 = Util.random_money(1, 1000000); //random amount of money 0-250
            final double moneyA2 = Util.random_money(1, 1000000);

            if(!reversible_axiom(moneyA1, currencyA, currencyB)) { failed_cases++; }
            if(!transative_axiom(moneyA1, currencyA, currencyB, currencyC)) { failed_cases++; }
            if(!ratio_axiom(moneyA1, moneyA2, currencyA, currencyB)) { failed_cases++; }
        } 
        
        if(failed_cases > 0) {
            System.out.println("Some errors occured" + failed_cases);
        }
        else {
            System.out.println("All tests passed");
        }

    }

    public boolean reversible_axiom(final double moneyA1, final String currencyA, final String currencyB) {
        
        //moneyA1 -> moneyB
        final double moneyB = calculator.convert(moneyA1, currencyA, currencyB); 
        
        //moneyB -> moneyA2
        final double moneyA2 = calculator.convert(moneyB, currencyB, currencyA);

        //moneyA1 == moneyA2
        if(Util.near_equal(moneyA1, moneyA2, 0.01)) {
            return true;
        }
        else {
            //logging/printing inside here
        
            System.out.println(String.format("%s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA1), currencyB, Util.nearest_cent(moneyB)));
            System.out.println(String.format("%s:%s -> %s:%s", currencyB, Util.nearest_cent(moneyB), currencyA, Util.nearest_cent(moneyA2)));
            System.out.println(String.format("%s:%s != %s:%s", currencyA, Util.nearest_cent(moneyA1), currencyA, Util.nearest_cent(moneyA2)));
            System.out.println();
            
            return false;
        }
    }

    public boolean transative_axiom(final double moneyA, final String currencyA, final String currencyB, final String currencyC) {

        //moneyA -> moneyB -> moneyC1
        final double moneyB = calculator.convert(moneyA, currencyA, currencyB); 
        final double moneyC1 = calculator.convert(moneyB, currencyB, currencyC);
        
        //moneyA -> moneyC2
        final double moneyC2 = calculator.convert(moneyA, currencyA, currencyC); 

        //moneyC1 == moneyC2
        if(Util.near_equal(moneyC1, moneyC2, 0.01)) {
            return true;
        }
        else {
            //logging/printing inside here

            System.out.println(String.format("%s:%s -> %s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA), currencyB, Util.nearest_cent(moneyB), currencyC, Util.nearest_cent(moneyC1)));
            System.out.println(String.format("%s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA), currencyC, Util.nearest_cent(moneyC2)));
            System.out.println(String.format("%s:%s != %s:%s", currencyC, Util.nearest_cent(moneyC1), currencyC, Util.nearest_cent(moneyC2)));
            System.out.println();

            return false;
        }
    }

    public boolean ratio_axiom(final double moneyA1, final double moneyA2, final String currencyA, final String currencyB) {

        //moneyA1 -> moneyB1
        final double moneyB1 = calculator.convert(moneyA1, currencyA, currencyB);
        
        //moneyA2 -> moneyB2
        final double moneyB2 = calculator.convert(moneyA2, currencyA, currencyB);

        final double ratio1 = moneyA1/moneyB1;
        final double ratio2 = moneyA2/moneyB2; 

        if(Util.near_equal(ratio1, ratio2, 0.01)) { //should be a % error not fixed rate
            return true;
        }
        else {
            //logging/printing inside here

            //kind of shitty should, probably print error number
            //then some diagnostic info
            System.out.println(String.format("%s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA1), currencyB, Util.nearest_cent(moneyB1)));
            System.out.println(String.format("%s / %s = %s", Util.nearest_cent(moneyA1), Util.nearest_cent(moneyB1), Util.nearest_cent(ratio1)));
            System.out.println();
            System.out.println(String.format("%s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA2), currencyB, Util.nearest_cent(moneyB2)));
            System.out.println(String.format("%s / %s = %s", Util.nearest_cent(moneyA2), Util.nearest_cent(moneyB2), Util.nearest_cent(ratio2)));
            System.out.println();
            System.out.println(String.format("%s != %s", Util.nearest_cent(ratio1), Util.nearest_cent(ratio2)));
            System.out.println();

            return false;
        }
    }
    
}
