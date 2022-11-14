import org.junit.jupiter.api.*;

public class AppTestTreeAndNode {
    static Tree test = new Tree();

    @Tag("DEV")
    @BeforeAll
    static void filing (){
        test.task("((46-(72+56))/(87*cos(25*(75-12))))*15");
    }

    @Tag("DEV")
    @Test
    void testFilingBranch(){
        Node exp = new Node();
        cutter comp = new cutter("(87*cos(25*(75-12))))*15");
        exp = exp.filingBranch(exp, comp, null);

        Assertions.assertEquals(-635.6081381536387, exp.calculate(exp, 0));
    }

    @Tag("DEV")
    @Test
    void testCalculate() {
        Node exp = new Node();
        cutter comp = new cutter("atan(13*45)-17");
        exp = exp.filingBranch(exp, comp, null);
        Assertions.assertEquals(-15.43091307324952, exp.calculate(exp, 0));
    }

    @Tag("DEV")
    @Test
    void testEstimation() {
        double result = 29.02731870865423;
        Assertions.assertEquals(test.get_result(), result);
    }
}
