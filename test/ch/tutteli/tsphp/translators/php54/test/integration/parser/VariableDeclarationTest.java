package ch.tutteli.tsphp.translators.php54.test.integration.parser;

import ch.tutteli.tsphp.translators.php54.test.integration.testutils.ATranslatorParserTest;
import ch.tutteli.tsphp.translators.php54.test.integration.testutils.ExpressionHelper;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class VariableDeclarationTest extends ATranslatorParserTest
{

    public VariableDeclarationTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        collection.add(new Object[]{"int $a;", "$a;"});
        collection.add(new Object[]{"int $a, $b=1;", "$a;\n$b = 1;"});
        collection.add(new Object[]{"int $a=60*60, $b;", "$a = 60 * 60;\n$b;"});

        List<String[]> expressions = ExpressionHelper.getExpressions();
        for (String[] expression : expressions) {
            collection.add(new Object[]{
                "int $a=" + expression[0] + ";",
                "$a = " + expression[1] + ";"
            });
            collection.add(new Object[]{
                "int $a=()" + expression[0] + ";",
                "$a = (int) " + expression[1] + ";"
            });
        }
        return collection;
    }
}
