package org.shl.validation.lambda.java7.testcases;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.shl.validation.lambda.java7.SelfValidating;
import org.shl.validation.lambda.core.ValidationFunction;
import org.shl.validation.lambda.core.ValidationService;
import org.shl.validation.lambda.java7.ConstraintFunction;
import org.shl.validation.lambda.core.AbstractTestCase;

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