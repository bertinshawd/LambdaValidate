package org.shl.validation.execution.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.validation.Constraint;
import javax.validation.Validation;
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
  private static final ExecutableValidator executableValidator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();

  @Pointcut("call(* *.*(..))")
  public void callPC() {
  }

  @Pointcut("call(*.new(..))")
  public void constructorPC() {
  }

  @Pointcut("@annotation(ValidateParameters)")
  public void validateParam(){}
  
  @Pointcut("@annotation(ValidateReturnValue)")
  public void validateRetVal(){}
 

  private boolean checkForConstraints(Annotation[] annotations) {
    for (Annotation annotation : annotations) {
      System.out.println(annotation);
      for (Annotation annotationsAnnotation : annotation.annotationType().getAnnotations()) {
        System.out.println(annotationsAnnotation);
        System.out.println(annotationsAnnotation.annotationType().equals(Constraint.class));
        if (annotationsAnnotation.annotationType().equals(Constraint.class)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkForConstraints(Annotation[][] annotationLists) {
    for (Annotation[] annotations : annotationLists) {
      if (checkForConstraints(annotations)) {
        return true;
      }
    }
    return false;
  }

  private Annotation[] returnAnnotations(Signature sig) throws NoSuchMethodException, SecurityException {
    Class<?> declaringType = sig.getDeclaringType();
    if (sig instanceof MethodSignature) {
      return declaringType.getMethod(sig.getName(), ((MethodSignature) sig).getParameterTypes()).getAnnotations();
    }
    if (sig instanceof ConstructorSignature) {
      return declaringType.getConstructor(((ConstructorSignature) sig).getParameterTypes()).getAnnotations();
    } // TODO: falloff exceptions are bad.
    throw new IllegalArgumentException(String.format("Signature `%s` is not a method or constructor signature", sig));
  }

  @SuppressWarnings("unchecked")
  private Method methodFor(Signature sig) throws NoSuchMethodException, SecurityException {
      return sig.getDeclaringType().getMethod(sig.getName(), ((MethodSignature) sig).getParameterTypes());
  }
  
  @SuppressWarnings("unchecked")
  private Method constructorFor(Signature sig) throws NoSuchMethodException, SecurityException {
    return sig.getDeclaringType().getMethod(sig.getName(), ((ConstructorSignature) sig).getParameterTypes());
  }
  
  
  private Class<?>[] noGroups = {};

  @Before(value = "callPC() && ValidateParameters()")
  public void validateMethodParameters(JoinPoint jp) throws NoSuchMethodException, SecurityException {
    Method method = methodFor(jp.getSignature());
    if (checkForConstraints(method.getParameterAnnotations())) {
      executableValidator.validateParameters(jp.getThis(), method, jp.getArgs(), noGroups);
    }
  }

  @AfterReturning(value = "callPC() && validateRetVal()", returning = "returnValue")
  public void validateMethodReturnValue(JoinPoint jp, Object returnValue) throws NoSuchMethodException, SecurityException {
    if (checkForConstraints(returnAnnotations(jp.getSignature()))) {
      System.out.println(returnValue);
      System.out.println("method return");
      System.out.println(jp);
    }
  }

  @Before("constructorPC() && ValidateParameters()")
  public void validateConstructorParameters(JoinPoint jp) {
    System.out.println("constructor parameters");
    System.out.println(jp);
  }

  @AfterReturning(value = "constructorPC() && validateRetVal()", returning = "returnValue")
  public void validateConstructorReturnValue(JoinPoint jp, Object returnValue) {
    System.out.println(returnValue);
    System.out.println("constructor return");
    System.out.println(jp);
  }
}
