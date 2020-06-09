
public class ReversibleAxiom implements Axiom {
    
    CurrencyCalculator calculator;

    String currencyA, currencyB; //a currency code
    double moneyA1, moneyA2, moneyB; //an amount of money

    public ReversibleAxiom(CurrencyCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public boolean check(double moneyA1, double moneyA2, String currencyA, String currencyB, String currencyC) {
        return check(moneyA1, currencyA, currencyB);
    }

    public boolean check(double moneyA1, String currencyA, String currencyB) {
        this.moneyA1 = moneyA1;
        this.currencyA = currencyA;
        this.currencyB = currencyB;

        //moneyA1 -> moneyB
        this.moneyB = calculator.calculate(moneyA1, currencyA, currencyB); 
        
        //moneyB -> moneyA2
        this.moneyA2 = calculator.calculate(moneyB, currencyB, currencyA);
    
        //moneyA1 == moneyA2
        return Util.near_equal(moneyA1, moneyA2, 0.01);
    }

    @Override
    public void print() {
        System.out.println(String.format("%s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA1), currencyB, Util.nearest_cent(moneyB)));
        System.out.println(String.format("%s:%s -> %s:%s", currencyB, Util.nearest_cent(moneyB), currencyA, Util.nearest_cent(moneyA2)));
        System.out.println(String.format("%s:%s != %s:%s", currencyA, Util.nearest_cent(moneyA1), currencyA, Util.nearest_cent(moneyA2)));
        System.out.println();
    }

}
