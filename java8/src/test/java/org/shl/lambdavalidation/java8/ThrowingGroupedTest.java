package org.shl.lambdavalidation.java8;

import javax.validation.ConstraintViolationException;

import org.shl.lambdavalidation.ThrowingValidationTestHarness;
import org.shl.lambdavalidation.java8.testcases.ThrowingGroupedTestCase;
import org.shl.lambdavalidation.validationgroups.GroupConstants;
import org.testng.annotations.Test;

public class ThrowingGroupedTest extends ThrowingValidationTestHarness<ThrowingGroupedTestCase> {

  public ThrowingGroupedTest() {
    super(ThrowingGroupedTestCase.class);
  }
      
  @Test(dataProvider = "positiveTestCases")
  public void positiveTestsStrict(ThrowingGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.strict());
  }

  @Test(dataProvider = "negativeTestCases", expectedExceptions = ConstraintViolationException.class)
  public void negativeTestsStrict(ThrowingGroupedTestCase testCase) {
    testInvalid("start must strictly be less than end", testCase, GroupConstants.strict());
  }
  

  @Test(dataProvider = "positiveEqualTestCases")
  public void positiveTestsNonStrict(ThrowingGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.nonstrict());
  }

  @Test(dataProvider = "negativeTestCases", expectedExceptions = ConstraintViolationException.class)
  public void negativeTestsNonStrict(ThrowingGroupedTestCase testCase) {
    testInvalid("start must be less than or equal to end", testCase, GroupConstants.nonstrict());
  }
}
