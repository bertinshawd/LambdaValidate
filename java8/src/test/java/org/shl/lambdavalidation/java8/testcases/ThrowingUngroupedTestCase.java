package org.shl.lambdavalidation.java8.testcases;

import org.shl.lambdavalidation.AbstractTestCase;
import org.shl.lambdavalidation.ValidationFunction;
import org.shl.lambdavalidation.java8.ConstraintFunction;
import org.shl.lambdavalidation.java8.ThrowingSelfValidating;

public class ThrowingUngroupedTestCase extends AbstractTestCase implements ThrowingSelfValidating {

  public ThrowingUngroupedTestCase(Integer start, Integer end) {
    super(start, end);
  }

  @ValidationFunction(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = () -> start < end;
  
}