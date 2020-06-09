
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.lang.Math;

public class Calculator implements Currency
{
    HashMap<String, Double> MAP_USD_to = new HashMap<>();

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

    public Calculator() 
    {
        /*
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
        }*/

        MAP_USD_to.put("USD", 1.0);
        MAP_USD_to.put("GBP", 0.79);
        MAP_USD_to.put("EUR", 0.89);
        MAP_USD_to.put("JPY", 76.37);
        MAP_USD_to.put("CHF", 0.96);
        MAP_USD_to.put("ARS", 69.05);
        MAP_USD_to.put("AED", 3.67);
        MAP_USD_to.put("AUD", 1.43);
        
    }

    public double calculate(final double value, final String from, final String to) {
        return value * conversion_ratio(from, to); //convert currency X to currency Y
    }

}
