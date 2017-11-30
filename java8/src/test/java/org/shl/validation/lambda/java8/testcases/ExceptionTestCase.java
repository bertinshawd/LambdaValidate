package org.shl.validation.lambda.java8.testcases;

import org.shl.validation.lambda.core.ValidationFunction;
import org.shl.validation.lambda.core.testing.AbstractTestCase;
import org.shl.validation.lambda.java8.ConstraintFunction;
import org.shl.validation.lambda.java8.ThrowingSelfValidating;

public class ExceptionTestCase extends AbstractTestCase implements ThrowingSelfValidating {

  public ExceptionTestCase(Integer start, Integer end) {
    super(start, end);
  }

  @ValidationFunction(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = () -> { throw new RuntimeException("thrown from validator"); };
  
}