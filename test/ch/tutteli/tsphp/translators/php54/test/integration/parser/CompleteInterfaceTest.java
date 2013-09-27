package ch.tutteli.tsphp.translators.php54.test.integration.parser;

import ch.tutteli.tsphp.translators.php54.test.integration.testutils.ATranslatorParserTest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CompleteInterfaceTest extends ATranslatorParserTest
{

    public CompleteInterfaceTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                    {
                        "interface A extends B{\n"
                            +"const bool A=true,B=null,C=false;\n"
                            +"/*some comments \n"
                            + "*/\n"
                            +"public function __construct(int $number);\n"
                            +"// a comment\n"
                            + "function void set(int $b,cast float $c);\n"
                        + "}",
                        "interface A extends B {"
                        + "\n    const A = true, B = null, C = false;"
                        + "\n    public function __construct($number);"
                        + "\n    public function set($b, $c);"
                        + "\n}"
                    },
                    
                });

    }
}
