package ch.tutteli.tsphp.translators.php54.test.integration.typechecker;

import ch.tutteli.tsphp.translators.php54.test.integration.testutils.ATranslatorTypeCheckerTest;
import ch.tutteli.tsphp.translators.php54.test.integration.testutils.AssignHelper;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class AssignmentTest extends ATranslatorTypeCheckerTest
{

    public AssignmentTest(String testString, String theExpectedResult) {
        super(testString, theExpectedResult);
    }

    @Test
    public void test() throws RecognitionException, IOException {
        translate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();

        AssignHelper.addCastingAssignment(collection, false);

        return collection;
    }
}
