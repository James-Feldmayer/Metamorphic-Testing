
public class RatioAxiom implements Axiom {
    
    CurrencyCalculator calculator;

    String currencyA, currencyB;
    double moneyA1, moneyA2, moneyB1, moneyB2;
    double ratio1, ratio2;

    public RatioAxiom(CurrencyCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public boolean check(double moneyA1, double moneyA2, String currencyA, String currencyB, String currencyC) {
        return check(moneyA1, moneyA2, currencyA, currencyB);
    }

    public boolean check(double moneyA1, double moneyA2, String currencyA, String currencyB) {
        this.moneyA1 = moneyA1;
        this.moneyA2 = moneyA2;
        this.currencyA = currencyA;
        this.currencyB = currencyB;

        //moneyA1 -> moneyB1
        this.moneyB1 = calculator.calculate(moneyA1, currencyA, currencyB);
        
        //moneyA2 -> moneyB2
        this.moneyB2 = calculator.calculate(moneyA2, currencyA, currencyB);
    
        this.ratio1 = moneyA1/moneyB1;
        this.ratio2 = moneyA2/moneyB2; 
    
        //should be a % error not fixed rate?

        return Util.near_equal(ratio1, ratio2, 0.01);
    }

    @Override
    public void print() {
        System.out.println(String.format("%s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA1), currencyB, Util.nearest_cent(moneyB1)));
        System.out.println(String.format("%s / %s = %s", Util.nearest_cent(moneyA1), Util.nearest_cent(moneyB1), Util.nearest_cent(ratio1)));
        System.out.println();
        System.out.println(String.format("%s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA2), currencyB, Util.nearest_cent(moneyB2)));
        System.out.println(String.format("%s / %s = %s", Util.nearest_cent(moneyA2), Util.nearest_cent(moneyB2), Util.nearest_cent(ratio2)));
        System.out.println();
        System.out.println(String.format("%s != %s", Util.nearest_cent(ratio1), Util.nearest_cent(ratio2)));
        System.out.println();
    }
    
}
