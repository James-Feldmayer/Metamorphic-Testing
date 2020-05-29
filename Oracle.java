
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

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

    public void start() {
    
        int failed_cases = 0;

        for(int i = 0; i < 10000; i++) {

            final String currencyA = randomCode();
            final String currencyB = randomCode();
            final String currencyC = randomCode();
            final double inital = round(Math.random() * 1000); //random

            if(!reversible(inital, currencyA, currencyB))  //oracle 1
            {
                failed_cases++;
            }

            if(!transative(inital, currencyA, currencyB, currencyC)) //oracle 2 
            {   
                failed_cases++;
            }

            if(!ratio()) { //oracle 3
                failed_cases++;
            }
        } 
            
        System.out.println(failed_cases + "/10000");

        //return 0;
    }

    //need a few test cases for each and every function

    //need some test cases
    public boolean reversible(final double inital, final String currencyA, final String currencyB) { //oracle 1
        
        //input -> output
        final double ouput = calculator.convert(inital, currencyA, currencyB); 
        
        //ouput -> reverse
        final double reverse = calculator.convert(ouput, currencyB, currencyA);

        //input == reverse
        if(inital == round(reverse)) {
            return true;
        }
        else {
            System.out.println(); //logging/printing inside here
            
            //probably still suffering form rounding error
            //need to do some logging to determine

            return false;
        }
    }

    //need some test cases
    public boolean transative(final double A1, final String currencyA, final String currencyB, final String currencyC) { //oracle 2

        //A1 -> B1 -> C1
        final double B1 = calculator.convert(A1, currencyA, currencyB); 
        final double C1 = calculator.convert(B1, currencyB, currencyC);
        
        //A1 -> C2
        final double C2 = calculator.convert(A1, currencyA, currencyC); 

        //C1 == C2
        if(round(C1) == round(C2)) {
            return true;
        }
        else {
            System.out.println(); //logging/printing inside here

            return false;
        }
    }

    public boolean ratio() { //oracle 3
        /*
        //final double inital, final String currencyA, final String currencyB
        final double ouput = calculator.convert(inital, currencyA, currencyB); 
        final double reverse = calculator.convert(ouput, currencyB, currencyA);
        return inital == round(reverse);
        */

        final boolean ans = true;

        if(ans) {
            return true;
        }
        else {
            System.out.println(); //logging/printing inside here

            return false;
        }
    }

    public double round(final double input) {
        return Math.round(input*100.0)/100.0;
    }

    public String randomCode() {
        final String random_code = currency_codes.get(randomIndex());
        return random_code;
    }
    
    public int randomIndex() {
        final int index = (int)(Math.random() * currency_codes.size());
        return index;
    }

    /*
    generate first then generate second based off that

    accross sites?
    */

    //USD_to(currency)
}
