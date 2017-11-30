package org.shl.validation.lambda.java8;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.shl.validation.lambda.core.SelfValidating;
import org.shl.validation.lambda.core.ValidationService;

/**
 * Interface that uses java8 default methods to implement self-validating behaviour 
 * for the throwing validation service.
 */
public interface ThrowingSelfValidating extends SelfValidating{
    @Override
    default public Set<ConstraintViolation<Object>> validate() throws ConstraintViolationException {
       return ValidationService.instance().thrower.validate(this);
    }
    
    @Override
    default Set<ConstraintViolation<Object>> validate(Class<?>[] groups) throws ConstraintViolationException, ValidationException {
      return ValidationService.instance().thrower.validate(this, groups);
    }
}
