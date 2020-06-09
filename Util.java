
/*
void test_random_currency()
void test_nearest_cent()
void test_random_single()
void test_random_range()
void test_near_equal()
void test_to_int()
*/

/* 
String random_currency()
double nearest_cent(double)
double random(double)
double random(double, double)
boolean near_equal(double, double, double)
int to_int()
*/

public class Util {

    //pick 
    public static String random_currency() {
        String[] currency_array = { "GBP", "EUR", "JPY", "CHF", "USD", "ARS", "AED", "AUD" };

        int random_index = to_int(random(currency_array.length));

        return currency_array[random_index];
    } 

    //round off to the nearest cent 
    public static double nearest_cent(final double input) {
        return Math.round(input*100.0)/100.0;
    }

    //convert double to an int
    public static int to_int(final double input) {
        return (int)input;
    }

    //generate a random number between 0 and maximum
    public static double random(final double maximum) { //c
        return Math.random() * maximum;
    }

    //generate a random number between minimum and maximum
    public static double random(final double minimum, final double maximum) {
        final double roof = maximum - minimum; //roof+minimum = maximum

        final double pre_minimum = random(roof); //random number between zero and roof
                
        return pre_minimum + minimum; //random number between minimum (0+minimum = minimum) and maximum (roof+minimum = maximum)
    }

    //allow for a small margain of error
    public static boolean near_equal(final double number1, final double number2, final double threshold) {
        double error = Math.abs(number1 - number2); 

        return error <= threshold;
    }

    //might even need a new version using magnetude 

}
