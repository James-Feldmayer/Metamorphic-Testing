
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.lang.Math;

public class Calculator implements Currency
{
    HashMap<String, Double> MAP_USD_to = new HashMap<>();

    //private
    private double USD_to(final String currency) {
        final boolean currency_exists = MAP_USD_to.containsKey(currency);

        if (currency_exists) {
            return MAP_USD_to.get(currency); //ratio of USD to currency
        } else {
            throw new IllegalArgumentException("Currency " + currency + " does not exist");
        }
    }

    private double to_USD(final String currency) {
        return Math.pow(USD_to(currency), -1); //ratio of currency to USD
    }

    private double conversion_ratio(final String from, final String to) {
        return to_USD(from) * USD_to(to); //ratio for converting currency X to currency Y
    }

    //maybe expand calculator a little
    //add some basic functionality for interacting with a user's currency
    //this would be probably be helpful
    private double nearest_cent(final double input) {
        return Math.round(input*100.0)/100.0;
    }

    //public
    public Calculator() 
    {
        try{
            final BufferedReader br = new BufferedReader(new FileReader("./currency.csv")); //open file currency.csv
            
            String line = "";
    
            while ((line = br.readLine()) != null) { //read in each line from the file
                final String[] currency = line.split(",");
                final String currency_code = currency[0];
                final Double currency_ratio = Double.valueOf(currency[1]); //ratio of USD to currency
                MAP_USD_to.put(currency_code, currency_ratio);
            }

            br.close(); //close file currency.csv
        }
        catch(IOException ex) {
            System.out.println("Could not find file");
        }
    }

    public double calculate(final double value, final String from, final String to) {
        return value * conversion_ratio(from, to); //convert currency X to currency Y
    }

}
