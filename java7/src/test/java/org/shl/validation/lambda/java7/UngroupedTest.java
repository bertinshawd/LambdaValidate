package org.shl.validation.lambda.java7;

import javax.validation.ConstraintViolationException;

import org.shl.validation.lambda.core.testing.ThrowingValidationTestHarness;
import org.shl.validation.lambda.java7.testcases.UngroupedTestCase;
import org.testng.annotations.Test;

public class UngroupedTest extends ThrowingValidationTestHarness<UngroupedTestCase> {

  public UngroupedTest() {
    super(UngroupedTestCase.class);
  }

  @Test(dataProvider = "positiveTestCases")
  public void positiveTests(UngroupedTestCase testCase) {
    testValid(testCase);
  }

  @Test(dataProvider = "negativeTestCases", expectedExceptions = ConstraintViolationException.class)
  public void negativeTests(UngroupedTestCase testCase) {
    testInvalid("start must be less than end", testCase);
  }
}
