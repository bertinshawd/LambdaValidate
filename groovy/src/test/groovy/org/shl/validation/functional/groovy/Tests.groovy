/*
 * Copyright © Daniel Bertinshaw, 2017
 *
 * See the LICENSE file distributed with this work for additional
 * information regarding copyright ownership.  The ASF licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.shl.validation.functional.groovy

import javax.validation.ConstraintViolationException
import javax.validation.ValidationException

import org.shl.validation.functional.ReturningValidationTestHarness
import org.shl.validation.functional.ThrowingValidationTestHarness
import org.shl.validation.functional.validationgroups.GroupConstants
import org.testng.annotations.Test

class ExceptionTest extends ThrowingValidationTestHarness<ExceptionTestCase> {

  public ExceptionTest() {
    super(ExceptionTestCase.class)
  }

  @Test(dataProvider = "positiveTestCases", expectedExceptions=ValidationException.class)
  public void ExceptionTests(ExceptionTestCase testCase) {
    testValid(testCase)
  }
}

class ThrowingUngroupedTest extends ThrowingValidationTestHarness<ThrowingGroupedTestCase> {

  public ThrowingUngroupedTest() {
    super(ThrowingGroupedTestCase)
  }

  @Test(dataProvider = "positiveTestCases", enabled=true)
  public void positiveTests(ThrowingGroupedTestCase testCase) {
    testValid(testCase);
  }

  @Test(dataProvider = "negativeTestCases", expectedExceptions = ConstraintViolationException.class)
  public void negativeTests(ThrowingGroupedTestCase testCase) {
    testInvalid("start must be less than end", testCase)
  }
}

class ThrowingGroupedTest extends ThrowingValidationTestHarness<ThrowingGroupedTestCase> {

  public ThrowingGroupedTest() {
    super(ThrowingGroupedTestCase)
  }

  @Test(dataProvider = "positiveTestCases", enabled=false)
  public void positiveTestsStrict(ThrowingGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.strict);
  }

  @Test(dataProvider = "negativeTestCases", expectedExceptions = ConstraintViolationException.class)
  public void negativeTestsStrict(ThrowingGroupedTestCase testCase) {
    testInvalid("start must strictly be less than end", testCase, GroupConstants.strict);
  }

  @Test(dataProvider = "positiveEqualTestCases")
  public void positiveTestsNonStrict(ThrowingGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.nonstrict);
  }

  @Test(dataProvider = "negativeTestCases", expectedExceptions = ConstraintViolationException.class)
  public void negativeTestsNonStrict(ThrowingGroupedTestCase testCase) {
    testInvalid("start must be less than or equal to end", testCase, GroupConstants.nonstrict);
  }
}

class ReturningUngroupedTest extends ReturningValidationTestHarness<ReturningUngroupedTestCase> {

  public ReturningUngroupedTest() {
    super(ReturningUngroupedTestCase)
  }

  @Test(dataProvider = "positiveTestCases")
  public void positiveTests(ReturningUngroupedTestCase testCase) {
    testValid(testCase);
  }

  @Test(dataProvider = "negativeTestCases")
  public void negativeTests(ReturningUngroupedTestCase testCase) {
    testInvalid("start must be less than end", testCase)
  }
}


class ReturningGroupedTest extends ReturningValidationTestHarness<ReturningGroupedTestCase> {

  public ReturningGroupedTest() {
    super(ReturningGroupedTestCase)
  }

  @Test(dataProvider = "positiveTestCases")
  public void positiveTestsStrict(ReturningGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.strict);
  }

  @Test(dataProvider = "positiveEqualTestCases")
  public void positiveTestsNonStrict(ReturningGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.nonstrict);
  }

  @Test(dataProvider = "negativeTestCases")
  public void negativeTestsNonStrict(ReturningGroupedTestCase testCase) {
    testInvalid("start must be less than or equal to end", testCase, GroupConstants.nonstrict);
  }

  @Test(dataProvider = "negativeTestCases")
  public void negativeTestsStrict(ReturningGroupedTestCase testCase) {
    testInvalid("start must strictly be less than end", testCase, GroupConstants.strict);
  }
}

