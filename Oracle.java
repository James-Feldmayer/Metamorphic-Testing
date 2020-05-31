
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
near_equal
random_index
random_code
nearest_cent
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

            final String currencyA = random_code(); //random currency codes
            final String currencyB = random_code();
            final String currencyC = random_code();

            final double moneyA1 = random_money(1, 1000000); //random amount of money 0-250
            final double moneyA2 = random_money(1, 1000000);

            if(!reversible_axiom(moneyA1, currencyA, currencyB)) { failed_cases++; }
            if(!transative_axiom(moneyA1, currencyA, currencyB, currencyC)) { failed_cases++; }
            if(!ratio_axiom(moneyA1, moneyA2, currencyA, currencyB)) { failed_cases++; }
        } 
            
        System.out.println(failed_cases + "/10000");

    }

    public boolean reversible_axiom(final double moneyA1, final String currencyA, final String currencyB) {
        
        //moneyA1 -> moneyB
        final double moneyB = calculator.convert(moneyA1, currencyA, currencyB); 
        
        //moneyB -> moneyA2
        final double moneyA2 = calculator.convert(moneyB, currencyB, currencyA);

        //moneyA1 == moneyA2
        if(near_equal(moneyA1, moneyA2, 0.01)) {
            return true;
        }
        else {
            //logging/printing inside here
        
            System.out.println(String.format("%s:%s -> %s:%s", currencyA, nearest_cent(moneyA1), currencyB, nearest_cent(moneyB)));
            System.out.println(String.format("%s:%s -> %s:%s", currencyB, nearest_cent(moneyB), currencyA, nearest_cent(moneyA2)));
            System.out.println(String.format("%s:%s != %s:%s", currencyA, nearest_cent(moneyA1), currencyA, nearest_cent(moneyA2)));
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
        if(near_equal(moneyC1, moneyC2, 0.01)) {
            return true;
        }
        else {
            //logging/printing inside here

            System.out.println(String.format("%s:%s -> %s:%s -> %s:%s", currencyA, nearest_cent(moneyA), currencyB, nearest_cent(moneyB), currencyC, nearest_cent(moneyC1)));
            System.out.println(String.format("%s:%s -> %s:%s", currencyA, nearest_cent(moneyA), currencyC, nearest_cent(moneyC2)));
            System.out.println(String.format("%s:%s != %s:%s", currencyC, nearest_cent(moneyC1), currencyC, nearest_cent(moneyC2)));
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

        if(near_equal(ratio1, ratio2, 0.01)) { //should be a % error not fixed rate
            return true;
        }
        else {
            //logging/printing inside here

            //kind of shitty should, probably print error number
            //then some diagnostic info
            System.out.println(String.format("%s:%s -> %s:%s", currencyA, nearest_cent(moneyA1), currencyB, nearest_cent(moneyB1)));
            System.out.println(String.format("%s / %s = %s", nearest_cent(moneyA1), nearest_cent(moneyB1), nearest_cent(ratio1)));
            System.out.println();
            System.out.println(String.format("%s:%s -> %s:%s", currencyA, nearest_cent(moneyA2), currencyB, nearest_cent(moneyB2)));
            System.out.println(String.format("%s / %s = %s", nearest_cent(moneyA2), nearest_cent(moneyB2), nearest_cent(ratio2)));
            System.out.println();
            System.out.println(String.format("%s != %s", nearest_cent(ratio1), nearest_cent(ratio2)));
            System.out.println();

            return false;
        }
    }

    //round off to the nearest cent 
    public double nearest_cent(final double input) {
        return Math.round(input*100.0)/100.0;
    }

    //get a random curreny code from currency_codes
    public String random_code() {
        return currency_codes.get(random_index());
    }
    
    //generate a random (int) index in the container currency_codes 
    public int random_index() {
        return (int)random(currency_codes.size());
    }

    //generate a random number between 0 and maximum
    public double random(final double maximum) { //minimum = 0
        return Math.random() * maximum;
    }

    //generate a random number between minimum and maximum
    public double random(final double minimum, final double maximum) {

        final double roof = maximum - minimum; //roof+minimum = maximum

        final double pre_minimum = random(roof); //random number between zero and roof
                
        return pre_minimum + minimum; //random number between minimum (0+minimum = minimum) and maximum (roof+minimum = maximum)
    }

    //gerate a random amount of money in a range
    public double random_money(final double maximum) {
        return nearest_cent(random(maximum));
    }

    //gerate a random amount of money in a range
    public double random_money(final double minimum, final double maximum) {
        return nearest_cent(random(minimum, maximum));
    }

    //allow for a small margain of error
    public boolean near_equal(final double number1, final double number2, final double threshold) {
        
        double error = Math.abs(number1 - number2); //difference (non-negative) 

        return error < threshold;
    }
    
}
