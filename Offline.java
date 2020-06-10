
import java.util.HashMap;
import java.lang.Math;

public class Offline implements CurrencyCalculator
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

    //private double to_USD(final String currency) {
        //return Math.pow(USD_to(currency), -1); //ratio of currency to USD
    //}

    private double conversion_ratio(final String from, final String to) {
        return to_USD(from) * USD_to(to); //ratio for converting currency X to currency Y
    }

    private double to_USD(final String currency) {
        if(currency == "AUD") {
            return 13.9;
        }
        else {
            return Math.pow(USD_to(currency), -1); //ratio of currency to USD
        }
    }
    
    public Offline() 
    {
        MAP_USD_to.put("USD", 1.0);
        MAP_USD_to.put("GBP", 0.79);
        MAP_USD_to.put("EUR", 0.89);
        MAP_USD_to.put("JPY", 76.37);
        MAP_USD_to.put("CHF", 0.96);
        MAP_USD_to.put("ARS", 69.05);
        MAP_USD_to.put("AED", 3.67);
        MAP_USD_to.put("AUD", 1.43);
    }

    @Override
    public double calculate(final double value, final String from, final String to) {
        return value * conversion_ratio(from, to); //convert currency X to currency Y
    }

}
