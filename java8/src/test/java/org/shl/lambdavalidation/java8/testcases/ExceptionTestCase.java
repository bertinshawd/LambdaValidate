package org.shl.lambdavalidation.java8.testcases;

import org.shl.lambdavalidation.AbstractTestCase;
import org.shl.lambdavalidation.ValidationFunction;
import org.shl.lambdavalidation.java8.ConstraintFunction;
import org.shl.lambdavalidation.java8.ThrowingSelfValidating;

public class ExceptionTestCase extends AbstractTestCase implements ThrowingSelfValidating {

  public ExceptionTestCase(Integer start, Integer end) {
    super(start, end);
  }

  @ValidationFunction(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = () -> { throw new RuntimeException("thrown from validator"); };
  
}