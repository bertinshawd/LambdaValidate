package org.shl.validation.functional.java7;

import javax.validation.ValidationException;

import org.shl.validation.functional.ThrowingValidationTestHarness;
import org.shl.validation.functional.java7.testcases.ExceptionTestCase;
import org.testng.annotations.Test;

public class ExceptionTest extends ThrowingValidationTestHarness<ExceptionTestCase> {

  public ExceptionTest() {
    super(ExceptionTestCase.class);
  }

  @Test(dataProvider = "positiveTestCases", expectedExceptions=ValidationException.class)
  public void exceptionTests(ExceptionTestCase testCase) {
    testValid(testCase);
  }

}
