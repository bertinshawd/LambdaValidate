package org.shl.validation.lambda.java7.testcases;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.shl.validation.lambda.core.AbstractTestCase;
import org.shl.validation.lambda.core.SelfValidating;
import org.shl.validation.lambda.core.ValidationFunction;
import org.shl.validation.lambda.core.ValidationService;
import org.shl.validation.lambda.java7.ConstraintFunction;

/*
 * Since java7 requires explicit inclusion of the particular service instance, there's no
 * need for a returning test case (because it's not testing anything that isn't tested in 
 * other projects).
 */
public class UngroupedTestCase extends AbstractTestCase implements SelfValidating {

  public UngroupedTestCase(Integer start, Integer end) {
    super(start, end);
  }

  @Override
  public Set<ConstraintViolation<Object>> validate() throws ConstraintViolationException {
    return ValidationService.instance().thrower.validate(this);
  }

  @Override
  public Set<ConstraintViolation<Object>> validate(Class<?>[] groups) throws ConstraintViolationException, ValidationException {
    return ValidationService.instance().thrower.validate(this);
  }

  @ValidationFunction(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = new ConstraintFunction() {
    @Override
    public boolean validate() {
      return start < end;
    }
  };
}