
import java.util.ArrayList;

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

    //public Oracle(CurrencyCalculator calculator) {
        //this.calculator = calculator;
    //}
    
    public void start() {

        int failed_cases = 0;

        for(int i = 0; i < 30; i++) {

            final String currencyA = Util.random_currency(); //random currency codes
            final String currencyB = Util.random_currency();
            final String currencyC = Util.random_currency();

            final double moneyA1 = Util.random_money(1, 1000000); //random amount of money 0-250
            final double moneyA2 = Util.random_money(1, 1000000);

            ArrayList<Axiom> axioms = new ArrayList<Axiom>();
            axioms.add(new RatioAxiom(new CalculateNet()));
            axioms.add(new TransativeAxiom(new CalculateNet()));
            axioms.add(new ReversibleAxiom(new CalculateNet()));

            for(Axiom a : axioms) {
                System.out.println(i+"/30");

                if(!a.check(moneyA1, moneyA2, currencyA, currencyB, currencyC)) {
                    System.out.println(i);
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
