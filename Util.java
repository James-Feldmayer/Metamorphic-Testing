
public class Util {

    public static String randomCurrency() {
        String[] currencies = {"GBP", "EUR", "JPY", "CHF", "USD", "ARS", "AED", "AUD"};

        int random_index = (int)Math.random() * currencies.length;

        return currencies[random_index];
    } 

//
    //needs to have some state :O
    /*
    //get a random curreny code from currency_codes
    public String random_code() {
        return currency_codes.get(random_index());
    }

    //generate a random (int) index in the container currency_codes 
    public static int random_index() {
        return (int)random(currency_codes.size());
    }
    */

//
    //round off to the nearest cent 
    public static double nearest_cent(final double input) {
        return Math.round(input*100.0)/100.0;
    }

    //generate a random number between 0 and maximum
    public static double random(final double maximum) { //minimum = 0
        return Math.random() * maximum;
    }

    //generate a random number between minimum and maximum
    public static double random(final double minimum, final double maximum) {

        final double roof = maximum - minimum; //roof+minimum = maximum

        final double pre_minimum = random(roof); //random number between zero and roof
                
        return pre_minimum + minimum; //random number between minimum (0+minimum = minimum) and maximum (roof+minimum = maximum)
    }

    //gerate a random amount of money in a range
    public static double random_money(final double maximum) {
        return nearest_cent(random(maximum));
    }

    //gerate a random amount of money in a range
    public static double random_money(final double minimum, final double maximum) {
        return nearest_cent(random(minimum, maximum));
    }

    //allow for a small margain of error
    public static boolean near_equal(final double number1, final double number2, final double threshold) {
        
        double error = Math.abs(number1 - number2); //difference (non-negative) 

        return error < threshold;
    }

}
