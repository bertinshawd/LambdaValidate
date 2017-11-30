package org.shl.validation.execution.aspects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.executable.ExecutableValidator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class ValidationAspect {
  /**
   * The executable validator.
   */
  private static final ExecutableValidator validator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();

  private static final String RETURNVALUE = "return value";
  private static final String PARAMETERS = "parameters";

  /**
   * Pointcut for method calls
   */
  @Pointcut("call(* *.*(..))")
  private void methodPC() {
  }

  /**
   * Pointcut for constructor calls
   */
  @Pointcut("call(*.new(..))")
  private void constructorPC() {
  }

  /**
   * Pointcut for methods and constructors decorated with
   * {@link ValidateParameters}
   */
  @Pointcut("@annotation(ValidateParameters)")
  private void validateParam() {
  }

  /**
   * Pointcut for methods and constructors decorated with
   * {@link ValidateReturnValue}
   */
  @Pointcut("@annotation(ValidateReturnValue)")
  private void validateRetVal() {
  }

  /**
   * Get the underlying Method for a Signature, to pass to the ExecutionValidator
   * 
   * @param sig The AspectJ signature of the method
   * @return The Java method matching sig
   * @Throws ValidationException All exceptions are wrapped in ValidationException here.
   */
  @SuppressWarnings("unchecked")
  private Method methodFor(Signature sig) throws ValidationException {
    try {
      return sig.getDeclaringType().getDeclaredMethod(sig.getName(), ((MethodSignature) sig).getParameterTypes());
    } catch (NoSuchMethodException | SecurityException | ClassCastException e) {
      throw new ValidationException(e);
    }
  }

  /**
   * Get the underlying Constructor for a Signature, to pass to the
   * ExecutionValidator
   * 
   * @param sig The AspectJ signature of the constructor
   * @return The Java constructor matching sig
   * @Throws ValidationException All exceptions are wrapped in ValidationException here.
   */
  @SuppressWarnings("unchecked")
  private Constructor<?> constructorFor(Signature sig) throws ValidationException {
    try {
      return sig.getDeclaringType().getConstructor(((ConstructorSignature) sig).getParameterTypes());
    } catch (NoSuchMethodException | SecurityException | ClassCastException e) {
      throw new ValidationException(e);
    }
  }

  /**
   * Get the validation groups for the return value
   * 
   * @param e The executable who's return value is to be validated
   * @return The array of Validation Group Classes
   */
  private Class<?>[] returnValidationGroups(Executable e) {
    return e.getAnnotation(ValidateReturnValue.class).groups();
  }

  /**
   * Get the validation groups for the parameters
   * 
   * @param e The executable who's parameter values is to be validated
   * @return The array of Validation Group Classes
   */
  private Class<?>[] paramValidationGroups(Executable e) {
    return e.getAnnotation(ValidateParameters.class).groups();
  }

  /**
   * Throws a {@link ConstraintViolationException} for non empty sets of 
   * 
   * @param type The display type in the exception message, either "return value"
   *          or "parameters"
   * @param ex The executable being validated
   * @param violations The (possibly empty) set of results from validation.
   * @throws ConstraintViolationException If violations is non-empty
   */
  private void throwWhenNonEmpty(String type, Executable ex, Set<ConstraintViolation<Object>> violations) throws ConstraintViolationException {
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(String.format("Validation of %s failed at %s", type, ex), violations);
    }
  }

  /**
   * Advice that validates the parameters of a method call.
   * 
   * @param jp the join point for the pointcut 
   */
  @Before("methodPC() && validateParam()")
  public void validateMethodParameters(JoinPoint jp)  {
    Method method = methodFor(jp.getSignature());
    Class<?>[] groups = paramValidationGroups(method);
    Set<ConstraintViolation<Object>> violations = validator.validateParameters(jp.getTarget(), method, jp.getArgs(), groups);
    throwWhenNonEmpty(PARAMETERS, method, violations);
  }

  /**
   * Advice that validates the return value of a method call.
   * 
   * @param jp the joinpoint for this pointcut
   * @param returnValue  The called method's return value
   */
  @AfterReturning(value = "methodPC() && validateRetVal()", returning = "returnValue")
  public void validateMethodReturnValue(JoinPoint jp, Object returnValue) {
    Method method = methodFor(jp.getSignature());
    Class<?>[] groups = returnValidationGroups(method);
    Set<ConstraintViolation<Object>> violations = validator.validateReturnValue(jp.getTarget(), method, returnValue, groups);
    throwWhenNonEmpty(RETURNVALUE, method, violations);
  }

  /**
   * Advice that validates the parameters of a constructor call.
   * 
   * @param jp the join point for the pointcut 
   */
  @Before("constructorPC() && validateParam()")
  public void validateConstructorParameters(JoinPoint jp) {
    Constructor<?> constructor = constructorFor(jp.getSignature());
    Class<?>[] groups = paramValidationGroups(constructor);
    Set<ConstraintViolation<Object>> violations = validator.validateConstructorParameters(constructor, jp.getArgs(), groups);
    throwWhenNonEmpty(PARAMETERS, constructor, violations);
  }

  /**
   * Advice that validates the return value of a constructor call.
   * 
   * @param jp the joinpoint for this pointcut
   * @param returnValue The called constructor's return value
   */
  @AfterReturning(value = "constructorPC() && validateRetVal()", returning = "returnValue")
  public void validateConstructorReturnValue(JoinPoint jp, Object returnValue) {
    Constructor<?> constructor = constructorFor(jp.getSignature());
    Class<?>[] groups = returnValidationGroups(constructor);
    Set<ConstraintViolation<Object>> violations = validator.validateConstructorReturnValue(constructor, returnValue, groups);
    throwWhenNonEmpty(RETURNVALUE, constructor, violations);
  }
}
