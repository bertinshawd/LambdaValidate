package org.shl.validation.functional.java8;

import org.shl.validation.functional.ReturningValidationTestHarness;
import org.shl.validation.functional.java8.testcases.ReturningGroupedTestCase;
import org.shl.validation.functional.validationgroups.GroupConstants;
import org.testng.annotations.Test;

public class ReturningGroupedTest extends ReturningValidationTestHarness<ReturningGroupedTestCase> {

  public ReturningGroupedTest() {
    super(ReturningGroupedTestCase.class);
  }
      
  @Test(dataProvider = "positiveTestCases", enabled=false)
  public void positiveTestsStrict(ReturningGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.strict);
  }

  @Test(dataProvider = "negativeTestCases", enabled=false)
  public void negativeTestsStrict(ReturningGroupedTestCase testCase) {
    testInvalid("start must strictly be less than end", testCase, GroupConstants.strict);
  }
  

  @Test(dataProvider = "positiveEqualTestCases", enabled=false)
  public void positiveTestsNonStrict(ReturningGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.nonstrict);
  }

  @Test(dataProvider = "negativeTestCases", enabled=false)
  public void negativeTestsNonStrict(ReturningGroupedTestCase testCase) {
    testInvalid("start must be less than or equal to end", testCase, GroupConstants.nonstrict);
  }
}
