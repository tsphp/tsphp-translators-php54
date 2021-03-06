/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.translators.php54.test.integration.testutils;

import ch.tsphp.common.AstHelper;
import ch.tsphp.common.AstHelperRegistry;
import ch.tsphp.common.IErrorLogger;
import ch.tsphp.common.ITSPHPAst;
import ch.tsphp.common.ITSPHPAstAdaptor;
import ch.tsphp.common.TSPHPAstAdaptor;
import ch.tsphp.common.exceptions.TSPHPException;
import ch.tsphp.parser.antlr.TSPHPParser;
import ch.tsphp.parser.antlrmod.ANTLRNoCaseStringStream;
import ch.tsphp.parser.antlrmod.ErrorReportingTSPHPLexer;
import ch.tsphp.parser.antlrmod.ErrorReportingTSPHPParser;
import ch.tsphp.translators.php54.PHP54PrecedenceHelper;
import ch.tsphp.translators.php54.TempVariableHelper;
import ch.tsphp.translators.php54.antlrmod.ErrorReportingPHP54TranslatorWalker;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.junit.Assert;
import org.junit.Ignore;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

@Ignore
public abstract class ATest implements IErrorLogger
{

    protected String testString;
    protected String expectedResult;
    protected ITSPHPAst ast;
    protected CommonTreeNodeStream commonTreeNodeStream;
    protected ErrorReportingPHP54TranslatorWalker translator;
    protected TreeRuleReturnScope result;
    protected ITSPHPAstAdaptor astAdaptor;

    public ATest(String theTestString, String theExpectedResult) {
        testString = theTestString;
        expectedResult = theExpectedResult;
    }

    public void check() {
        Assert.assertFalse(testString + " failed. found translator exception(s). See output.",
                translator.hasFoundError());

        Assert.assertEquals(testString + " failed.", expectedResult,
                result.getTemplate().toString().replaceAll("\r", ""));
    }

    @Override
    public void log(TSPHPException exception) {
        System.err.println(exception.getMessage());
    }

    public void parse() throws RecognitionException {

        astAdaptor = new TSPHPAstAdaptor();
        AstHelperRegistry.set(new AstHelper(astAdaptor));

        CharStream stream = new ANTLRNoCaseStringStream(testString);
        ErrorReportingTSPHPLexer lexer = new ErrorReportingTSPHPLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        ErrorReportingTSPHPParser parser = new ErrorReportingTSPHPParser(tokens);
        parser.setTreeAdaptor(astAdaptor);
        parser.registerErrorLogger(new WriteExceptionToConsole());

        ParserRuleReturnScope parserResult = parserRun(parser);
        ast = (ITSPHPAst) parserResult.getTree();

        Assert.assertFalse(testString.replaceAll("\n", " ") + " failed - lexer throw exception", lexer.hasFoundError());
        Assert.assertFalse(testString.replaceAll("\n", " ") + " failed - parser throw exception",
                parser.hasFoundError());

        commonTreeNodeStream = new CommonTreeNodeStream(astAdaptor, ast);
        commonTreeNodeStream.setTokenStream(parser.getTokenStream());
    }

    protected void typeCheck() {
    }

    public void translate() throws IOException, RecognitionException {
        parse();

        typeCheck();

        // LOAD TEMPLATES (via classpath)
        URL url = ClassLoader.getSystemResource("PHP54.stg");
        FileReader fr = new FileReader(url.getFile());
        StringTemplateGroup templates = new StringTemplateGroup(fr);
        fr.close();

        translator = new ErrorReportingPHP54TranslatorWalker(commonTreeNodeStream, new PHP54PrecedenceHelper(),
                new TempVariableHelper(astAdaptor));
        translator.registerErrorLogger(this);
        translator.setTemplateLib(templates);

        run();

        check();
    }

    protected ParserRuleReturnScope parserRun(TSPHPParser parser) throws RecognitionException {
        return parser.statement();
    }

    protected void run() throws RecognitionException {
        result = translator.statement();
    }
}
