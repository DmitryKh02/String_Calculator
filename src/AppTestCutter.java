import org.junit.jupiter.api.*;

public class AppTestCutter {
    cutter test = new cutter("((46-(72+56))/(87*cos(25*(75-x))))*15");

    @AfterEach
    void setupThis() {
        test.set_i(0);
    }

    @Tag("DEV")
    @Test
    void testCorrect() {
        boolean result = test.correct();
        Assertions.assertEquals(true, result);
    }

    @Tag("DEV")
    @Test
    void testIsItExpression() {
        boolean result = test.isItExpression();
        Assertions.assertEquals(true, result);
    }

    @Tag("DEV")
    @Test
    void testIsItExpressionEntered() {
        String example = "12:48&14";
        boolean result = test.isItExpression(example);
        Assertions.assertEquals(false, result);
    }

    @Tag("DEV")
    @Test
    void testSubString() {
        String subStr = test.subStr();
        String result = "(46-(72+56))/(87*cos(25*(75-x)))";

        Assertions.assertEquals(subStr, result);
    }

    @Tag("DEV")
    @Test
    void testSubStringInd() {
        String subStr = test.subStr(14);
        String result = "87*cos(25*(75-x))";

        Assertions.assertEquals(subStr, result);
    }

    @Tag("DEV")
    @Test
    void testisOperator()
    {
        char example = '^';
        Assertions.assertEquals( true , test.isOperator(example));
    }

    @Tag("DEV")
    @Test
    void testComporator()
    {
        String example = "5/(-18.756238921)";
        cutter dop = new cutter(example);
        String result = "";
        for (int i = 0; i < 3; i++) {
            result = dop.comparator();
        }
        Assertions.assertEquals("-18.756238921" , result);
    }
}