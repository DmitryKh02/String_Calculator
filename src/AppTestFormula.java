import org.junit.jupiter.api.*;

public class AppTestFormula {
    Formula test = new Formula("acos");

    @Tag("DEV")
    @Test
    void testIsItFormula() {
        Assertions.assertEquals(true, test.isItFormula());
    }

    @Tag("DEV")
    @Test
    void testGetEst() {
        test.set_meaning(0);
        test.set_meaning(-12.5);
        test.set_meaning(0);
        test.set_meaning(13.6);
        test.set_meaning(1);
        Assertions.assertEquals(0, test.get_est());
    }
}
