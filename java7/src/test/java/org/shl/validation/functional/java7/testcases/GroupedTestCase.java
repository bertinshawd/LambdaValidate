package org.shl.validation.functional.java7.testcases;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.shl.validation.functional.AbstractTestCase;
import org.shl.validation.functional.SelfValidating;
import org.shl.validation.functional.ValidationFunction;
import org.shl.validation.functional.ValidationService;
import org.shl.validation.functional.java7.ConstraintFunction;
import org.shl.validation.functional.validationgroups.NonStrict;
import org.shl.validation.functional.validationgroups.Strict;

/*
 * Since java7 requires explicit inclusion of the particular service instance, there's no
 * need for a returning test case (because it's not testing anything that isn't tested in 
 * other projects).
 */
public class GroupedTestCase extends AbstractTestCase implements SelfValidating {

  public GroupedTestCase(Integer start, Integer end) {
    super(start, end);
  }

  @Override
  public Set<ConstraintViolation<Object>> validate() throws ConstraintViolationException {
    return ValidationService.instance().thrower.validate(this);
  }

  @Override
  public Set<ConstraintViolation<Object>> validate(Class<?>[] groups) throws ConstraintViolationException, ValidationException {
    return ValidationService.instance().thrower.validate(this, groups);
  }

  @ValidationFunction(message = "start must strictly be less than end", groups = Strict.class)
  private final ConstraintFunction startLessThanEndStrict = new ConstraintFunction() {
    @Override
    public boolean validate() {
      return start < end;
    }
  };

  @ValidationFunction(message = "start must be less than or equal to end", groups = NonStrict.class)
  private final ConstraintFunction startLessThanEndNonStrict = new ConstraintFunction() {
    @Override
    public boolean validate() {
      return start <= end;
    }
  };
}