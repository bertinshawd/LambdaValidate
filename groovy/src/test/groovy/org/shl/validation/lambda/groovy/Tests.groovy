/*
 * Copyright © Daniel Bertinshaw, 2017
 *
 * See the LICENSE file distributed with this work for additional
 * information regarding copyright ownership.  The ASF licenses
 * this file to you under the Apache License, Version 2.0 (the
 * 'License'); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * 'AS IS' BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.shl.validation.lambda.groovy

import javax.validation.ConstraintViolationException
import javax.validation.ValidationException

import org.shl.validation.lambda.core.ReturningValidationTestHarness
import org.shl.validation.lambda.core.ThrowingValidationTestHarness
import org.shl.validation.lambda.core.testgroups.GroupConstants
import org.testng.annotations.Test

class ExceptionTest extends ThrowingValidationTestHarness<ExceptionTestCase> {

  ExceptionTest() {
    super(ExceptionTestCase)
  }

  @Test(dataProvider = 'positiveTestCases', expectedExceptions=ValidationException)
  void exceptionTests(ExceptionTestCase testCase) {
    testValid(testCase)
  }
}

class ThrowingUngroupedTest extends ThrowingValidationTestHarness<ThrowingUngroupedTestCase> {

  ThrowingUngroupedTest() {
    super(ThrowingUngroupedTestCase)
  }

  @Test(dataProvider = 'positiveTestCases')
  void positiveTests(ThrowingUngroupedTestCase testCase) {
    testValid(testCase)
  }

  @Test(dataProvider = 'negativeTestCases', expectedExceptions = ConstraintViolationException)
  void negativeTests(ThrowingUngroupedTestCase testCase) {
    testInvalid('start must be less than end', testCase)
  }
}

class ThrowingGroupedTest extends ThrowingValidationTestHarness<ThrowingGroupedTestCase> {

  ThrowingGroupedTest() {
    super(ThrowingGroupedTestCase)
  }

  @Test(dataProvider = 'positiveTestCases')
  void positiveTestsStrict(ThrowingGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.strict())
  }

  @Test(dataProvider = 'negativeTestCases', expectedExceptions = ConstraintViolationException)
  void negativeTestsStrict(ThrowingGroupedTestCase testCase) {
    testInvalid('start must strictly be less than end', testCase, GroupConstants.strict())
  }

  @Test(dataProvider = 'positiveEqualTestCases')
  void positiveTestsNonStrict(ThrowingGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.nonstrict())
  }

  @Test(dataProvider = 'negativeTestCases', expectedExceptions = ConstraintViolationException)
  void negativeTestsNonStrict(ThrowingGroupedTestCase testCase) {
    testInvalid('start must be less than or equal to end', testCase, GroupConstants.nonstrict())
  }
}

class ReturningUngroupedTest extends ReturningValidationTestHarness<ReturningUngroupedTestCase> {

  ReturningUngroupedTest() {
    super(ReturningUngroupedTestCase)
  }

  @Test(dataProvider = 'positiveTestCases')
  void positiveTests(ReturningUngroupedTestCase testCase) {
    testValid(testCase)
  }

  @Test(dataProvider = 'negativeTestCases')
  void negativeTests(ReturningUngroupedTestCase testCase) {
    testInvalid('start must be less than end', testCase)
  }
}

class ReturningGroupedTest extends ReturningValidationTestHarness<ReturningGroupedTestCase> {

  ReturningGroupedTest() {
    super(ReturningGroupedTestCase)
  }

  @Test(dataProvider = 'positiveTestCases')
  void positiveTestsStrict(ReturningGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.strict())
  }

  @Test(dataProvider = 'positiveEqualTestCases')
  void positiveTestsNonStrict(ReturningGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.nonstrict())
  }

  @Test(dataProvider = 'negativeTestCases')
  void negativeTestsNonStrict(ReturningGroupedTestCase testCase) {
    testInvalid('start must be less than or equal to end', testCase, GroupConstants.nonstrict())
  }

  @Test(dataProvider = 'negativeTestCases')
  void negativeTestsStrict(ReturningGroupedTestCase testCase) {
    testInvalid('start must strictly be less than end', testCase, GroupConstants.strict())
  }
}

