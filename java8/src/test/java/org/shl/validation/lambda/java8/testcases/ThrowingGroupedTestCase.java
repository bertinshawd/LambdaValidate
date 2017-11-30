package org.shl.validation.lambda.java8.testcases;

import org.shl.validation.lambda.core.ValidationFunction;
import org.shl.validation.lambda.core.testing.AbstractTestCase;
import org.shl.validation.lambda.core.testing.groups.NonStrict;
import org.shl.validation.lambda.core.testing.groups.Strict;
import org.shl.validation.lambda.java8.ConstraintFunction;
import org.shl.validation.lambda.java8.ThrowingSelfValidating;

public class ThrowingGroupedTestCase extends AbstractTestCase implements ThrowingSelfValidating {

  public ThrowingGroupedTestCase(Integer start, Integer end) {
    super(start, end);
  }

  @ValidationFunction(message = "start must strictly be less than end", groups = Strict.class)
  private final ConstraintFunction startLessThanEndStrict = () -> start < end;

  @ValidationFunction(message = "start must be less than or equal to end", groups = NonStrict.class)
  private final ConstraintFunction startLessThanEndNonStrict = () -> start <= end;
}