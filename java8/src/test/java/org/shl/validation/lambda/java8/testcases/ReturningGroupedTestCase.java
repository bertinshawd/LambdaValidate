package org.shl.validation.lambda.java8.testcases;

import org.shl.validation.lambda.core.ValidationFunction;
import org.shl.validation.lambda.core.testing.AbstractTestCase;
import org.shl.validation.lambda.core.testing.groups.NonStrictGroup;
import org.shl.validation.lambda.core.testing.groups.StrictGroup;
import org.shl.validation.lambda.java8.ConstraintFunction;
import org.shl.validation.lambda.java8.ReturningSelfValidating;

public class ReturningGroupedTestCase extends AbstractTestCase implements ReturningSelfValidating {

  public ReturningGroupedTestCase(Integer start, Integer end) {
    super(start, end);
  }

  @ValidationFunction(message = "start must strictly be less than end", groups = StrictGroup.class)
  private final ConstraintFunction startLessThanEndStrict = () -> start < end;

  @ValidationFunction(message = "start must be less than or equal to end", groups = NonStrictGroup.class)
  private final ConstraintFunction startLessThanEndNonStrict = () -> start <= end;
}