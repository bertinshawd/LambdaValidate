package org.shl.validation.lambda.java8;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.shl.validation.lambda.core.SelfValidating;
import org.shl.validation.lambda.core.ValidationService;

/**
 * Interface that uses java8 default methods to implement self-validating behaviour 
 * for the returning validation service.
 */
public interface ReturningSelfValidating extends SelfValidating{
    @Override
    default public Set<ConstraintViolation<Object>> validate() throws ConstraintViolationException {
       return ValidationService.instance().returning.validate(this);
    }
    
    @Override
    default Set<ConstraintViolation<Object>> validate(Class<?>[] groups) throws ConstraintViolationException, ValidationException {
      return ValidationService.instance().returning.validate(this, groups);
    }
}
