package org.shl.lambdavalidation.java8;

import org.shl.lambdavalidation.ReturningValidationTestHarness;
import org.shl.lambdavalidation.java8.testcases.ReturningGroupedTestCase;
import org.shl.lambdavalidation.validationgroups.GroupConstants;
import org.testng.annotations.Test;

public class ReturningGroupedTest extends ReturningValidationTestHarness<ReturningGroupedTestCase> {

  public ReturningGroupedTest() {
    super(ReturningGroupedTestCase.class);
  }
      
  @Test(dataProvider = "positiveTestCases")
  public void positiveTestsStrict(ReturningGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.strict());
  }

  @Test(dataProvider = "negativeTestCases")
  public void negativeTestsStrict(ReturningGroupedTestCase testCase) {
    testInvalid("start must strictly be less than end", testCase, GroupConstants.strict());
  }
  

  @Test(dataProvider = "positiveEqualTestCases")
  public void positiveTestsNonStrict(ReturningGroupedTestCase testCase) {
    testValid(testCase, GroupConstants.nonstrict());
  }

  @Test(dataProvider = "negativeTestCases")
  public void negativeTestsNonStrict(ReturningGroupedTestCase testCase) {
    testInvalid("start must be less than or equal to end", testCase, GroupConstants.nonstrict());
  }
}
