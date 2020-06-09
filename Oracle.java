
/*
Oracle()
start
transative_axiom
ratio_axiom
reversible_axiom
*/

public class Oracle 
{
    CurrencyCalculator calculator;

    public Oracle(CurrencyCalculator calculator) {
        this.calculator = calculator;
    }
    
    public void start() {

        int failed_cases = 0;

        for(int i = 0; i < 30; i++) {

            final String currencyA = Util.random_currency(); //random currency codes
            final String currencyB = Util.random_currency();
            final String currencyC = Util.random_currency();

            final double moneyA1 = Util.random_money(1, 1000000); //random amount of money 0-250
            final double moneyA2 = Util.random_money(1, 1000000);

            //should I try better organize the printing of errors?

            if(!reversible_axiom(moneyA1, currencyA, currencyB)) { failed_cases++; }
            if(!transative_axiom(moneyA1, currencyA, currencyB, currencyC)) { failed_cases++; }
            if(!ratio_axiom(moneyA1, moneyA2, currencyA, currencyB)) { failed_cases++; }
        } 
        
        if(failed_cases > 0) {
            System.out.println("Some errors occured, failed cases: " + failed_cases);
        }
        else {
            System.out.println("All tests passed");
        }

    }

    public boolean reversible_axiom(final double moneyA1, final String currencyA, final String currencyB) {
        
        //moneyA1 -> moneyB
        final double moneyB = calculator.calculate(moneyA1, currencyA, currencyB); 
        
        //moneyB -> moneyA2
        final double moneyA2 = calculator.calculate(moneyB, currencyB, currencyA);

        //moneyA1 == moneyA2
        if(Util.near_equal(moneyA1, moneyA2, 0.01)) {
            return true;
        }
        else {
            //logging inside here is easier
        
            System.out.println(String.format("%s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA1), currencyB, Util.nearest_cent(moneyB)));
            System.out.println(String.format("%s:%s -> %s:%s", currencyB, Util.nearest_cent(moneyB), currencyA, Util.nearest_cent(moneyA2)));
            System.out.println(String.format("%s:%s != %s:%s", currencyA, Util.nearest_cent(moneyA1), currencyA, Util.nearest_cent(moneyA2)));
            System.out.println();
            
            return false;
        }
    }

    public boolean transative_axiom(final double moneyA, final String currencyA, final String currencyB, final String currencyC) {

        //moneyA -> moneyB -> moneyC1
        final double moneyB = calculator.calculate(moneyA, currencyA, currencyB); 
        final double moneyC1 = calculator.calculate(moneyB, currencyB, currencyC);
        
        //moneyA -> moneyC2
        final double moneyC2 = calculator.calculate(moneyA, currencyA, currencyC); 

        //moneyC1 == moneyC2
        if(Util.near_equal(moneyC1, moneyC2, 0.01)) {
            return true;
        }
        else {
            //logging inside here is easier

            System.out.println(String.format("%s:%s -> %s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA), currencyB, Util.nearest_cent(moneyB), currencyC, Util.nearest_cent(moneyC1)));
            System.out.println(String.format("%s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA), currencyC, Util.nearest_cent(moneyC2)));
            System.out.println(String.format("%s:%s != %s:%s", currencyC, Util.nearest_cent(moneyC1), currencyC, Util.nearest_cent(moneyC2)));
            System.out.println();

            return false;
        }
    }

    public boolean ratio_axiom(final double moneyA1, final double moneyA2, final String currencyA, final String currencyB) {

        //moneyA1 -> moneyB1
        final double moneyB1 = calculator.calculate(moneyA1, currencyA, currencyB);
        
        //moneyA2 -> moneyB2
        final double moneyB2 = calculator.calculate(moneyA2, currencyA, currencyB);

        final double ratio1 = moneyA1/moneyB1;
        final double ratio2 = moneyA2/moneyB2; 

        if(Util.near_equal(ratio1, ratio2, 0.01)) { //should be a % error not fixed rate
            return true;
        }
        else {
            //logging inside here is easier

            //kind of shitty
            //maybe print error code and some diagnostic info?
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
