/*
 * Copyright 2012 Robert Stoll <rstoll@tutteli.ch>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.tutteli.tsphp.translators.php54.test.testutils;

import org.junit.Assert;
import org.junit.Ignore;

/**
 *
 * @author Robert Stoll <rstoll@tutteli.ch>
 */
@Ignore
public abstract class ATranslatorTest extends ATest
{

    public ATranslatorTest(String theTestString, String theExpectedResult) {
        super(theTestString, theExpectedResult);
    }

    @Override
    public void check() {
        Assert.assertFalse(testString + " failed. found exception(s). See output.", translator.hasFoundError());
        
        Assert.assertEquals(testString + " failed.", expectedResult,
                result.getTemplate().toString().replaceAll("\r", ""));
    }
}
