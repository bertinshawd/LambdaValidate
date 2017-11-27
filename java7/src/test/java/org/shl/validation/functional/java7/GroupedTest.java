package org.shl.validation.functional.java7;

import javax.validation.ConstraintViolationException;

import org.shl.validation.functional.AbstractTestCase;
import org.shl.validation.functional.ThrowingValidationTestHarness;
import org.shl.validation.functional.java7.testcases.GroupedTestCase;
import org.shl.validation.functional.validationgroups.GroupConstants;
import org.shl.validation.functional.validationgroups.NonStrict;
import org.shl.validation.functional.validationgroups.Strict;
import org.testng.annotations.Test;

public class GroupedTest extends ThrowingValidationTestHarness<GroupedTestCase> {

  public GroupedTest() {
    super(GroupedTestCase.class);
  }
  
      
  @Test(dataProvider = "positiveTestCases")
  public void positiveTestsStrict(GroupedTestCase testCase) {
    testValid(testCase, GroupConstants.strict);
  }

  @Test(dataProvider = "negativeTestCases", expectedExceptions = ConstraintViolationException.class)
  public void negativeTestsStrict(GroupedTestCase testCase) {
    testInvalid("start must strictly be less than end", testCase, GroupConstants.strict);
  }
  

  @Test(dataProvider = "positiveEqualTestCases")
  public void positiveTestsNonStrict(GroupedTestCase testCase) {
    testValid(testCase, GroupConstants.nonstrict);
  }

  @Test(dataProvider = "negativeTestCases", expectedExceptions = ConstraintViolationException.class)
  public void negativeTestsNonStrict(GroupedTestCase testCase) throws Exception {
    testInvalid("start must be less than or equal to end", testCase, GroupConstants.nonstrict);
  }
}
