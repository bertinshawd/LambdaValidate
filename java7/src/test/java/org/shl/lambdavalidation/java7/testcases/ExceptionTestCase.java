package org.shl.lambdavalidation.java7.testcases;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.shl.lambdavalidation.SelfValidating;
import org.shl.lambdavalidation.ValidationFunction;
import org.shl.lambdavalidation.ValidationService;
import org.shl.lambdavalidation.AbstractTestCase;
import org.shl.lambdavalidation.java7.ConstraintFunction;

public class ExceptionTestCase extends AbstractTestCase implements SelfValidating {

  public ExceptionTestCase(Integer start, Integer end) {
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
      throw new RuntimeException("thrown from validator");
    }
  };
}