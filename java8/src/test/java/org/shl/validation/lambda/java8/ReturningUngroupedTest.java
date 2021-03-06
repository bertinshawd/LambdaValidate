package org.shl.validation.lambda.java8;

import org.shl.validation.lambda.core.testing.ReturningValidationTestHarness;
import org.shl.validation.lambda.java8.testcases.ReturningUngroupedTestCase;
import org.testng.annotations.Test;

public class ReturningUngroupedTest extends ReturningValidationTestHarness<ReturningUngroupedTestCase> {

  public ReturningUngroupedTest() {
    super(ReturningUngroupedTestCase.class);
  }

  @Test(dataProvider = "positiveTestCases")
  public void positiveTests(ReturningUngroupedTestCase testCase) {
    testValid(testCase);
  }

  @Test(dataProvider = "negativeTestCases")
  public void negativeTests(ReturningUngroupedTestCase testCase) {
    testInvalid("start must be less than end", testCase);
  }
}
