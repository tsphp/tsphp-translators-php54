/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.translators.php54.test.integration.testutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionHelper
{
    /**
     * @param offset defines how many characters will be on the left of the expression
     *               is used for the casting operator, TempVariableHelper respectively (getCharPositionInLine)
     * @return The expressions
     */
    public static List<String[]> getAllExpressions(int offset) {
        List<String[]> list = new ArrayList<>();
        list.addAll(getCastToTypeExpressions(offset));
        list.addAll(getExpressions());
        return list;
    }

    /**
     * @param offset defines how many characters will be on the left of the expression
     *               is used for the casting operator, TempVariableHelper respectively (getCharPositionInLine)
     * @return The cast expressions
     */
    public static List<String[]> getCastToTypeExpressions(int offset) {
        String $a = "($a !== null ? ($a instanceof MyClass ? $a : "
                + "\\trigger_error('Cast failed, the evaluation type of $a must be MyClass', \\E_RECOVERABLE_ERROR)) "
                + ": null)";
        return Arrays.asList(new String[][]{
                {"(MyClass) $a", $a},
                {
                        "(Type) (MyClass) $a",
                        "(($_t1_" + (offset + 7) + " = " + $a + ") !== null ? "
                                + "($_t1_" + (offset + 7) + " instanceof Type ? "
                                + "$_t1_" + (offset + 7) + " "
                                + ": \\trigger_error('Cast failed, the evaluation type of "
                                + $a + " must be Type', \\E_RECOVERABLE_ERROR)" +
                                ") "
                                + ": null"
                                + ")"
                },
                {"~~$a", "~~$a"},
                {"@@$a", "@@$a"},
                {
                        "@(Type) ~$a",
                        "@(($_t1_" + (offset + 8) + " = ~$a) !== null ? "
                                + "($_t1_" + (offset + 8) + " instanceof Type ? "
                                + "$_t1_" + (offset + 8) + " "
                                + ": \\trigger_error('Cast failed, the evaluation type of "
                                + "~$a must be Type', \\E_RECOVERABLE_ERROR)" +
                                ") "
                                + ": null"
                                + ")"
                },
        });
    }


    public static List<String[]> getExpressions() {
        List<String[]> list = new ArrayList<>();
        list.addAll(getExpressionsWithoutUnaryPrimary());
        list.addAll(ExpressionHelper.getUnaryPrimaryExpressions());
        return list;
    }

    public static List<String[]> getExpressionsWithoutUnaryPrimary() {


        return Arrays.asList(new String[][]{
                {"$a or $b", "$a or $b"},
                {"$a or $b or $c", "$a or $b or $c"},
                {"$a xor $b", "$a xor $b"},
                {"$a xor $b xor $c", "$a xor $b xor $c"},
                {"$a and $b", "$a and $b"},
                {"$a and $b and $c", "$a and $b and $c"},
                {"$a and $b or $c xor $d", "$a and $b or $c xor $d"},
                {"$a or $b and $c xor $d", "$a or $b and $c xor $d"},

                {"$a = $b", "$a = $b"},
                {"$a += $b", "$a = $a + $b"},
                {"$a -= $b", "$a = $a - $b"},
                {"$a *= $b", "$a = $a * $b"},
                {"$a /= $b", "$a = $a / $b"},
                {"$a &= $b", "$a = $a & $b"},
                {"$a |= $b", "$a = $a | $b"},
                {"$a ^= $b", "$a = $a ^ $b"},
                {"$a %= $b", "$a = $a % $b"},
                {"$a .= $b", "$a = $a . $b"},
                {"$a <<= $b", "$a = $a << $b"},
                {"$a >>= $b", "$a = $a >> $b"},
                {
                        "$a = $b += $c -= $d *= $e /= $f &= $g |= $h ^= $i %= $j .= $k <<= $l >>= $m",
                        "$a = $b = $b + ($c = $c - ($d = $d * ($e = $e / ($f = $f & ($g = $g | ($h = $h ^ "
                                + "($i = $i % ($j = $j . ($k = $k << ($l = $l >> $m))))))))))"
                },

                {"true ? $a : $b", "true ? $a : $b"},
                {"true ? ($a ? $b : $c) : $d", "true ? $a ? $b : $c : $d"},
                {"true ? $a : ($b ? $c : $d)", "true ? $a : $b ? $c : $d"},
                {"$a = true ? $c += $d : ($e -= $f)", "$a = true ? ($c = $c + $d) : ($e = $e - $f)"},
                {
                        "($a *= true) ? $c /= $d ? $e &= $f : ($g |= $h) : ($i ^= $j)",
                        "($a = $a * true) ? ($c = $c / ($d ? ($e = $e & $f) : ($g = $g | $h))) : ($i = $i ^ $j)"
                },
                {
                        "$a %= true ? $c .= $d ? $e <<= $f : ($g >>= $h) : ($i = $j)",
                        "$a = $a % (true ? ($c = $c . ($d ? ($e = $e << $f) : ($g = $g >> $h))) : ($i = $j))"
                },
                // = has lower precedence than ternary
                // and the following is equal to $a = (true ? $a : $b) = 1
                {"$a = true ? $a : $b = 1", "$a = true ? $a : $b = 1"},
                {"$a || $b", "$a || $b"},
                {"$a || $b || $c", "$a || $b || $c"},
                {"$a && $b", "$a && $b"},
                {"$a && $b && $c", "$a && $b && $c"},
                {"$a && $b || $c", "$a && $b || $c"},
                {"$a || $b && $c", "$a || $b && $c"},
                {"$a || $b && $c ? $d : $e", "$a || $b && $c ? $d : $e"},
                {"$a | $b", "$a | $b"},
                {"$a | $b | $c", "$a | $b | $c"},
                {"$a ^ $b", "$a ^ $b"},
                {"$a ^ $b ^ $c", "$a ^ $b ^ $c"},
                {"$a & $b", "$a & $b"},
                {"$a & $b & $c", "$a & $b & $c"},
                {"$a & $b | $c ^ $d", "$a & $b | $c ^ $d"},
                {"($a | $b) & $c ^ $d", "($a | $b) & $c ^ $d"},
                {"$a == $b", "$a == $b"},
                {"$a === $b", "$a === $b"},
                {"$a != $b", "$a != $b"},
                {"$a !== $b", "$a !== $b"},
                {"$a < $b", "$a < $b"},
                {"$a <= $b", "$a <= $b"},
                {"$a > $b", "$a > $b"},
                {"$a >= $b", "$a >= $b"},
                {
                        "$a == $b | $c < $d & $e ? $f != $g : $h === $i",
                        "$a == $b | $c < $d & $e ? $f != $g : $h === $i"
                },
                {"1 << 2", "1 << 2"},
                {"1 >> 2", "1 >> 2"},
                {"1 >> 2 << 3 >> 5", "1 >> 2 << 3 >> 5"},
                {"1 + 2", "1 + 2"},
                {"1 - 2", "1 - 2"},
                {"$a . $b", "$a . $b"},
                {"$a << $b >> $c + $d * $e - $f", "$a << $b >> $c + $d * $e - $f"},

                {"$a instanceof MyClass", "$a instanceof MyClass"},
                {"$a instanceof $b", "$a instanceof $b"},
        });

    }

    public static List<String[]> getUnaryPrimaryExpressions() {
        return Arrays.asList(new String[][]{
                {
                        "(Type) $a",
                        "($a !== null ? ("
                                + "$a instanceof Type ? $a "
                                + ": \\trigger_error('Cast failed, the evaluation type of $a must be Type', " +
                                "\\E_RECOVERABLE_ERROR)"
                                + ") : null)"
                },
                {"(int) $a", "(int) $a"},
                {"(int?) $a", "($a !== null ? (int) $a : null)"},
                {"~$a", "~$a"},
                {"@$a", "@$a"},
                {"!$a", "!$a"},
                {"!!$a", "!!$a"},
                {"!!! $a", "!!!$a"},
                {"+$a", "+$a"},
                {"+1", "+1"},
                {"-$a", "-$a"},
                {"-2", "-2"},
                {"+$a + $b", "+$a + $b"},
                {"-$a - $b", "-$a - $b"},
                {"clone $a", "clone $a"},
                {"clone $a->a", "clone $a->a"},
                {"new Type", "new Type()"},
                {"new Type()", "new Type()"},
                {"new Type(1,$a,'hello')", "new Type(1, $a, 'hello')"},
                {"exit", "exit"},
                {"exit(1)", "exit(1)"},
                {"($a)", "$a"},
                {"$a++", "$a++"},
                {"$a--", "$a--"},
                {"++$a", "++$a"},
                {"--$a", "--$a"},
                {"$a", "$a"},
                {"$a[0]", "$a[0]"},
                {"$a->a", "$a->a"},
                {"$a->foo()", "$a->foo()"},
                {"$a->foo(true || false,123*9)", "$a->foo(true || false, 123 * 9)"},
                {"foo()", "foo()"},
                {"\\foo(1,1+2,3)", "\\foo(1, 1 + 2, 3)"},
                {"\\a\\foo()", "\\a\\foo()"},
                {"foo()", "foo()"},
                {"self::foo()", "self::foo()"},
                {"parent::foo()", "parent::foo()"},
                {"Foo::foo()", "Foo::foo()"},
                {"self::$a", "self::$a"},
                {"parent::$a", "parent::$a"},
                {"Foo::$a", "Foo::$a"},
                {"self::a", "self::a"},
                {"parent::a", "parent::a"},
                {"Foo::a", "Foo::a"},
                {"a\\Foo::a", "a\\Foo::a"},
                {"true", "true"},
                {"false", "false"},
                {"null", "null"},
                {"a\\b", "a\\b"},
                {"1", "1"},
                {"2.123", "2.123"},
                {"'a'", "'a'"},
                {"\"asdf\"", "\"asdf\""},
                {"[1,2,'a'=>3]", "[1, 2, 'a' => 3]"},
                {"(int) clone $a + $b", "(int) clone $a + $b"},
                {"(-$a + $b) * $c", "(-$a + $b) * $c"},
                {
                        "!($a instanceof Type) || $a < $b+$c == ~(1 | 3 & 12)",
                        "!($a instanceof Type) || $a < $b + $c == ~(1 | 3 & 12)"
                }
        });
    }
}
