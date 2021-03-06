/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.translators.php54.test.integration.testutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatementHelper
{
    private StatementHelper() {
    }

    public static List<Object[]> getStatements(String prefix, String appendix,
            String prefixExpected, String indent, String appendixExpected) {
        List<Object[]> collection = new ArrayList<>();

        collection.addAll(getControlStructures(prefix, "int $a;", appendix,
                prefixExpected, indent, "$a;", appendixExpected));

        collection.addAll(Arrays.asList(new Object[][]{
                {prefix + "int $a;" + appendix, prefixExpected + "\n" + indent + "$a;" + appendixExpected},
                {prefix + ";" + appendix, prefixExpected + appendixExpected},
                {prefix + "return;" + appendix, prefixExpected + "\n" + indent + "return;" + appendixExpected},
                {
                        prefix + "throw new Exception();" + appendix,
                        prefixExpected + "\n" + indent + "throw new Exception();" + appendixExpected
                },
                {prefix + "break;" + appendix, prefixExpected + "\n" + indent + "break;" + appendixExpected},
                {prefix + "continue;" + appendix, prefixExpected + "\n" + indent + "continue;" + appendixExpected},
                {
                        prefix + "echo 'hello';" + appendix,
                        prefixExpected + "\n" + indent + "echo 'hello';" + appendixExpected
                },
                {
                        prefix + "const int a=1;" + appendix,
                        prefixExpected + "\n" + indent + "const a = 1;" + appendixExpected
                },
                {
                        prefix + "class A{}" + appendix,
                        prefixExpected + "\n" + indent + "class A {}" + appendixExpected
                },
                {
                        prefix + "function void foo(){}" + appendix,
                        prefixExpected + "\n" + indent + "function foo() {\n" + indent + "}" + appendixExpected
                },
                {
                        prefix + "interface A{}" + appendix,
                        prefixExpected + "\n" + indent + "interface A {}" + appendixExpected
                },
                {
                        prefix + "use \\A;" + appendix,
                        prefixExpected + "\n" + indent + "use \\A as A;" + appendixExpected
                }
        }));

        List<String[]> expressions = ExpressionHelper.getExpressions();
        for (String[] expression : expressions) {
            collection.add(new Object[]{
                    prefix + expression[0] + ";" + appendix,
                    prefixExpected + "\n" + indent + expression[1] + ";" + appendixExpected
            });
        }
        return collection;
    }


    public static List<Object[]> getControlStructures(String prefix, String instruction, String appendix,
            String prefixExpected, String indent, String instructionExpected, String appendixExpected) {
        return Arrays.asList(new Object[][]{
                {
                        prefix + instruction + appendix,
                        prefixExpected + "\n" + indent + instructionExpected + appendixExpected
                },
                {
                        prefix + "{" + instruction + "}" + appendix,
                        prefixExpected + "\n" + indent + instructionExpected + appendixExpected
                },
                {
                        prefix + "{ {" + instruction + "} }" + appendix,
                        prefixExpected + "\n" + indent + instructionExpected + appendixExpected
                },
                {
                        prefix + "for(;;) " + instruction + appendix,
                        prefixExpected + "\n" + indent
                                + "for (; ; ) {\n" + indent + "    " + instructionExpected + "\n" + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "for(;;){ " + instruction + "}" + appendix,
                        prefixExpected + "\n" + indent
                                + "for (; ; ) {\n" + indent + "    " + instructionExpected + "\n" + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "foreach([] as int $k)" + instruction + appendix,
                        prefixExpected + "\n" + indent
                                + "foreach ([] as $k) {\n" + indent + "    " + instructionExpected + "\n" + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "foreach([] as int $k){" + instruction + "}" + appendix,
                        prefixExpected + "\n" + indent
                                + "foreach ([] as $k) {\n" + indent + "    " + instructionExpected + "\n" + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "while(true)" + instruction + appendix,
                        prefixExpected + "\n" + indent
                                + "while (true) {\n" + indent + "    " + instructionExpected + "\n" + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "while(true){" + instruction + "}" + appendix,
                        prefixExpected + "\n" + indent
                                + "while (true) {\n" + indent + "    " + instructionExpected + "\n" + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "do " + instruction + " while(true);" + appendix,
                        prefixExpected + "\n" + indent
                                + "do {\n" + indent + "    " + instructionExpected + "\n" + indent + "} while (true);"
                                + appendixExpected
                },
                {
                        prefix + "do{ " + instruction + "}while(true);" + appendix,
                        prefixExpected + "\n" + indent
                                + "do {\n" + indent + "    " + instructionExpected + "\n" + indent + "} while (true);"
                                + appendixExpected
                },
                {
                        prefix + "switch($a){case 1: " + instruction + "}" + appendix,
                        prefixExpected + "\n" + indent
                                + "switch ($a) {\n"
                                + indent + "    case 1:\n"
                                + indent + "        " + instructionExpected + "\n"
                                + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "switch($a){"
                                + "    case 1: $a=1; " + instruction
                                + "    default: $a=2; " + instruction
                                + "}" + appendix,
                        prefixExpected + "\n" + indent
                                + "switch ($a) {\n"
                                + indent + "    case 1:\n"
                                + indent + "        $a = 1;\n"
                                + indent + "        " + instructionExpected + "\n"
                                + indent + "    default:\n"
                                + indent + "        $a = 2;\n"
                                + indent + "        " + instructionExpected + "\n"
                                + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "switch($a){"
                                + "    case 1:{ $a=1; " + instruction + "}"
                                + "    default: $a=2; { " + instruction + "} "
                                + "}" + appendix,
                        prefixExpected + "\n" + indent
                                + "switch ($a) {\n"
                                + indent + "    case 1:\n"
                                + indent + "        $a = 1;\n"
                                + indent + "        " + instructionExpected + "\n"
                                + indent + "    default:\n"
                                + indent + "        $a = 2;\n"
                                + indent + "        " + instructionExpected + "\n"
                                + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "if($a)" + instruction + appendix,
                        prefixExpected + "\n" + indent
                                + "if ($a) {\n" + indent + "    " + instructionExpected + "\n" + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "if($a){" + instruction + "}" + appendix,
                        prefixExpected + "\n" + indent
                                + "if ($a) {\n" + indent + "    " + instructionExpected + "\n" + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "if($a) $a=1; else " + instruction + appendix,
                        prefixExpected + "\n" + indent
                                + "if ($a) {\n"
                                + indent + "    $a = 1;\n"
                                + indent + "} else {\n"
                                + indent + "    " + instructionExpected + "\n"
                                + indent + "}"
                                + appendixExpected
                },

                {
                        prefix + "if($a){$a=1;}else{" + instruction + "}" + appendix,
                        prefixExpected + "\n" + indent
                                + "if ($a) {\n"
                                + indent + "    $a = 1;\n"
                                + indent + "} else {\n"
                                + indent + "    " + instructionExpected + "\n"
                                + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "try{" + instruction + "}catch(\\Exception $e){}" + appendix,
                        prefixExpected + "\n" + indent
                                + "try {\n"
                                + indent + "    " + instructionExpected + "\n"
                                + indent + "} catch (\\Exception $e) {\n"
                                + indent + "}"
                                + appendixExpected
                },
                {
                        prefix + "try{$a=1;}catch(\\Exception $e){" + instruction + "}" + appendix,
                        prefixExpected + "\n" + indent
                                + "try {\n"
                                + indent + "    $a = 1;\n"
                                + indent + "} catch (\\Exception $e) {\n"
                                + indent + "    " + instructionExpected + "\n"
                                + indent + "}"
                                + appendixExpected
                }
        });
    }
}
