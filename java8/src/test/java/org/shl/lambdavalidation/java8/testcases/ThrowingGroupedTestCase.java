package org.shl.lambdavalidation.java8.testcases;

import org.shl.lambdavalidation.AbstractTestCase;
import org.shl.lambdavalidation.ValidationFunction;
import org.shl.lambdavalidation.java8.ConstraintFunction;
import org.shl.lambdavalidation.java8.ThrowingSelfValidating;
import org.shl.lambdavalidation.validationgroups.NonStrict;
import org.shl.lambdavalidation.validationgroups.Strict;

public class ThrowingGroupedTestCase extends AbstractTestCase implements ThrowingSelfValidating {

  public ThrowingGroupedTestCase(Integer start, Integer end) {
    super(start, end);
  }

  @ValidationFunction(message = "start must strictly be less than end", groups = Strict.class)
  private final ConstraintFunction startLessThanEndStrict = () -> start < end;

  @ValidationFunction(message = "start must be less than or equal to end", groups = NonStrict.class)
  private final ConstraintFunction startLessThanEndNonStrict = () -> start <= end;
}