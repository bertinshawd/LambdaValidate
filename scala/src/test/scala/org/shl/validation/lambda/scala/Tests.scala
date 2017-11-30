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
package org.shl.validation.lambda.scala

import org.testng.annotations.Test
import javax.validation.ConstraintViolationException
import javax.validation.ValidationException
import scala.reflect.ClassTag
import scala.reflect.classTag
import org.shl.validation.lambda.core.ValidationFunction
import org.shl.validation.lambda.core.AbstractTestCase
import org.shl.validation.lambda.core.{ThrowingValidationTestHarness => ThrowTestHarness}
import org.shl.validation.lambda.core.{ReturningValidationTestHarness => ReturnTestHarness}
import org.shl.validation.lambda.core.testgroups.NonStrict
import org.shl.validation.lambda.core.testgroups.Strict
import org.shl.validation.lambda.core.testgroups.GroupConstants

abstract class ThrowingValidationTestHarness[T <: AbstractTestCase :ClassTag]
  extends ThrowTestHarness[T](classTag[T].runtimeClass.asInstanceOf[Class[T]])


abstract class ReturningValidationTestHarness[T <: AbstractTestCase :ClassTag]
  extends ReturnTestHarness[T](classTag[T].runtimeClass.asInstanceOf[Class[T]])


class ExceptionTest extends ThrowingValidationTestHarness[ExceptionTestCase] {
  @Test(dataProvider = "positiveTestCases", expectedExceptions = Array(classOf[ValidationException]))
  def positiveTests(testCase: ExceptionTestCase) = testValid(testCase)
}


class ThrowingUngroupedTest extends ThrowingValidationTestHarness[ThrowingUngroupedTestCase] {
  @Test(dataProvider = "positiveTestCases")
  def positiveTests(testCase: ThrowingUngroupedTestCase) = testValid(testCase)

  @Test(dataProvider = "negativeTestCases", expectedExceptions = Array(classOf[ConstraintViolationException]))
  def negativeTests(testCase: ThrowingUngroupedTestCase) = testInvalid("start must be less than end", testCase)
}


class ThrowingGroupedTest extends ThrowingValidationTestHarness[ThrowingGroupedTestCase] {

  @Test(dataProvider = "positiveTestCases")
  def positiveTestsStrict(testCase: ThrowingGroupedTestCase) = testValid(testCase, GroupConstants.strict)

  @Test(dataProvider = "negativeTestCases", expectedExceptions = Array(classOf[ConstraintViolationException]))
  def negativeTestsStrict(testCase: ThrowingGroupedTestCase) = testInvalid("start must strictly be less than end", testCase, GroupConstants.strict)

  @Test(dataProvider = "positiveEqualTestCases")
  def positiveTestsNonStrict(testCase: ThrowingGroupedTestCase) = testValid(testCase, GroupConstants.nonstrict)

  @Test(dataProvider = "negativeTestCases", expectedExceptions = Array(classOf[ConstraintViolationException]))
  def negativeTestsNonStrict(testCase: ThrowingGroupedTestCase) = testInvalid("start must be less than or equal to end", testCase, GroupConstants.nonstrict)

}


class ReturningUngroupedTest extends ReturningValidationTestHarness[ReturningUngroupedTestCase] {
  @Test(dataProvider = "positiveTestCases")
  def positiveTests(testCase: ReturningUngroupedTestCase) = testValid(testCase)

  @Test(dataProvider = "negativeTestCases")
  def negativeTests(testCase: ReturningUngroupedTestCase) = testInvalid("start must be less than end", testCase)
}


class ReturningGroupedTest extends ReturningValidationTestHarness[ReturningGroupedTestCase] {

  @Test(dataProvider = "positiveTestCases")
  def positiveTestsStrict(testCase: ReturningGroupedTestCase) = testValid(testCase, GroupConstants.strict)

  @Test(dataProvider = "negativeTestCases")
  def negativeTestsStrict(testCase: ReturningGroupedTestCase) = testInvalid("start must strictly be less than end", testCase, GroupConstants.strict)

  @Test(dataProvider = "positiveEqualTestCases")
  def positiveTestsNonStrict(testCase: ReturningGroupedTestCase) = testValid(testCase, GroupConstants.nonstrict)

  @Test(dataProvider = "negativeTestCases")
  def negativeTestsNonStrict(testCase: ReturningGroupedTestCase) = testInvalid("start must be less than or equal to end", testCase, GroupConstants.nonstrict)
}
