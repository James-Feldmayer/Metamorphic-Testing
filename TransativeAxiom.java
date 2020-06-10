
public class TransativeAxiom implements Axiom {

    CurrencyCalculator calculator;

    String currencyA, currencyB, currencyC; //a currency code
    double moneyA, moneyB, moneyC1, moneyC2; //an amount of money

    public TransativeAxiom(CurrencyCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public boolean check(double moneyA1, double moneyA2, String currencyA, String currencyB, String currencyC) {
        return check(moneyA, currencyA, currencyB, currencyC);
    }

    public boolean check(double moneyA, String currencyA, String currencyB, String currencyC) {
        this.moneyA = moneyA;
        this.currencyA = currencyA;
        this.currencyB = currencyB;
        this.currencyC = currencyC;

        //moneyA -> moneyB -> moneyC1
        this.moneyB = calculator.calculate(moneyA, currencyA, currencyB); 
        this.moneyC1 = calculator.calculate(moneyB, currencyB, currencyC);
        
        //moneyA -> moneyC2
        this.moneyC2 = calculator.calculate(moneyA, currencyA, currencyC); 
    
        //moneyC1 == moneyC2
        return Util.near_equal(moneyC1, moneyC2, 0.01);
    }
    
    @Override
    public void print() {
        System.out.println("Failed TransativeAxiom");
        System.out.println(String.format("%s:%s -> %s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA), currencyB, Util.nearest_cent(moneyB), currencyC, Util.nearest_cent(moneyC1)));
        System.out.println(String.format("%s:%s -> %s:%s", currencyA, Util.nearest_cent(moneyA), currencyC, Util.nearest_cent(moneyC2)));
        System.out.println(String.format("%s:%s != %s:%s", currencyC, Util.nearest_cent(moneyC1), currencyC, Util.nearest_cent(moneyC2)));
        System.out.println();
    }
}
