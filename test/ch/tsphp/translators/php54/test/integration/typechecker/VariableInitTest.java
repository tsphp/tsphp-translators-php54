package ch.tsphp.translators.php54.test.integration.typechecker;

import ch.tsphp.translators.php54.test.integration.testutils.ATranslatorTypeCheckerTest;
import ch.tsphp.translators.php54.test.integration.testutils.AssignHelper;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class VariableInitTest extends ATranslatorTypeCheckerTest
{

    public VariableInitTest(String testString, String theExpectedResult) {
        super(testString, theExpectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();

        AssignHelper.addCastingAssignment(collection, true);

        return collection;
    }
}