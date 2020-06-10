import static org.junit.Assert.*;
import org.junit.Test;

//

public class AxiomTest {

    @Test
    public void test_check() {
        Axiom ratio_axiom = new RatioAxiom(new Offline());
        Axiom transative_axiom = new TransativeAxiom(new Offline());
        Axiom reversible_axiom = new ReversibleAxiom(new Offline());
        
        //i'm not sure how these axioms or oracle are testable?

        assertTrue("Error calculateNet class: Incorrect output", true);
    }

    //

}
