package org.shl.validation.functional.java8.testcases;

import org.shl.validation.functional.AbstractTestCase;
import org.shl.validation.functional.ValidationFunction;
import org.shl.validation.functional.java8.ConstraintFunction;
import org.shl.validation.functional.java8.ReturningSelfValidating;
import org.shl.validation.functional.validationgroups.NonStrict;
import org.shl.validation.functional.validationgroups.Strict;

public class ReturningGroupedTestCase extends AbstractTestCase implements ReturningSelfValidating {

  public ReturningGroupedTestCase(Integer start, Integer end) {
    super(start, end);
  }

  @ValidationFunction(message = "start must strictly be less than end", groups = Strict.class)
  private final ConstraintFunction startLessThanEndStrict = () -> start < end;

  @ValidationFunction(message = "start must be less than or equal to end", groups = NonStrict.class)
  private final ConstraintFunction startLessThanEndNonStrict = () -> start <= end;
}