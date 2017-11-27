package org.shl.validation.functional.java8;

import javax.validation.ConstraintViolationException;

import org.shl.validation.functional.ThrowingValidationTestHarness;
import org.shl.validation.functional.java8.testcases.ThrowingGroupedTestCase;
import org.shl.validation.functional.validationgroups.GroupConstants;
import org.testng.annotations.Test;

public class ThrowingGroupedTest extends ThrowingValidationTestHarness<ThrowingGroupedTestCase> {

  public ThrowingGroupedTest() {
    super(ThrowingGroupedTestCase.class);
  }
      
  @Test(dataProvider = "positiveTestCases", enabled=false)
  public void positiveTestsStrict(ThrowingGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.strict);
  }

  @Test(dataProvider = "negativeTestCases", expectedExceptions = ConstraintViolationException.class)
  public void negativeTestsStrict(ThrowingGroupedTestCase testCase) {
    testInvalid("start must strictly be less than end", testCase, GroupConstants.strict);
  }
  

  @Test(dataProvider = "positiveEqualTestCases", enabled=false)
  public void positiveTestsNonStrict(ThrowingGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.nonstrict);
  }

  @Test(dataProvider = "negativeTestCases", expectedExceptions = ConstraintViolationException.class, enabled=false)
  public void negativeTestsNonStrict(ThrowingGroupedTestCase testCase) {
    testInvalid("start must be less than or equal to end", testCase, GroupConstants.nonstrict);
  }
}
