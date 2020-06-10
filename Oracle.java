
import java.util.ArrayList;

/*
Oracle()
void start()
*/

public class Oracle 
{
    //CurrencyCalculator calculator;

    //public Oracle(CurrencyCalculator calculator) {
        //this.calculator = calculator;
    //}
    
    //this class is stateless and only has one function

    public void start() {

        int failed_cases = 0;

        for(int i = 0; i < 30; i++) {

            final String currencyA = Util.random_currency(); //random currency codes
            final String currencyB = Util.random_currency();
            final String currencyC = Util.random_currency();

            final double moneyA1 = Util.nearest_cent(Util.random(1, 1000000)); //random_money
            final double moneyA2 = Util.nearest_cent(Util.random(1, 1000000));

            //we should probably hard coded edge cases?

            ArrayList<Axiom> axioms = new ArrayList<Axiom>(); //operations
            axioms.add(new RatioAxiom(new Offline()));
            axioms.add(new TransativeAxiom(new Offline()));
            axioms.add(new ReversibleAxiom(new Offline()));

            System.out.println(i+1+"/30"); //current iteration

            for(Axiom a : axioms) { //most of the work
                if(!a.check(moneyA1, moneyA2, currencyA, currencyB, currencyC)) {
                    failed_cases++;
                    a.print();
                }
            }

        } 
        
        if(failed_cases > 0) {
            System.out.println("Some errors occured, failed cases: " + failed_cases);
        }
        else {
            System.out.println("All tests passed");
        }

    }
}
