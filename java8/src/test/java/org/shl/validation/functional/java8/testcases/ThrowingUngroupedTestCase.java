package org.shl.validation.functional.java8.testcases;

import org.shl.validation.functional.AbstractTestCase;
import org.shl.validation.functional.ValidationFunction;
import org.shl.validation.functional.java8.ConstraintFunction;
import org.shl.validation.functional.java8.ThrowingSelfValidating;

public class ThrowingUngroupedTestCase extends AbstractTestCase implements ThrowingSelfValidating {

  public ThrowingUngroupedTestCase(Integer start, Integer end) {
    super(start, end);
  }

  @ValidationFunction(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = () -> start < end;
  
}