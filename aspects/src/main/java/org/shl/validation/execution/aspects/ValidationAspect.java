package org.shl.validation.execution.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
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
  protected static final ExecutableValidator validator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();

  @Pointcut("call(* *.*(..))")
  protected void methodPC() {}

  @Pointcut("call(*.new(..))")
  protected void constructorPC() {}

  @Pointcut("@annotation(ValidateParameters)")
  protected void validateParam(){}
  
  @Pointcut("@annotation(ValidateReturnValue)")
  protected void validateRetVal(){}
 

  protected boolean constraintExists(Annotation[] annotations) {
    for (Annotation annotation : annotations) {
      System.out.println(annotation);
      for (Annotation annotationOfAnnotation : annotation.annotationType().getAnnotations()) {
        System.out.println(annotationOfAnnotation);
        System.out.println(annotationOfAnnotation.annotationType().equals(Constraint.class));
        if (annotationOfAnnotation.annotationType().equals(Constraint.class)) {
          return true;
        }
      }
    }
    return false;
  }

  protected boolean constraintExists(Annotation[][] annotationLists) {
    for (Annotation[] annotations : annotationLists) {
      if (constraintExists(annotations)) {
        return true;
      }
    }
    return false;
  }

  protected Annotation[] returnAnnotations(Constructor<?> e) {
    return e.getAnnotations();
  }
  
  protected Annotation[] returnAnnotations(Method e) {
    return e.getAnnotations();
  }
  
  
  protected Annotation[][] paramAnnotations(Constructor<?> e) {
    return e.getParameterAnnotations();
  }

  protected Annotation[][] paramAnnotations(Method e) {
    return e.getParameterAnnotations();
  }

  @SuppressWarnings("unchecked")
  protected Method methodFor(Signature sig) throws NoSuchMethodException, SecurityException {
      return sig.getDeclaringType().getMethod(sig.getName(), ((MethodSignature) sig).getParameterTypes());
  }
  
  @SuppressWarnings("unchecked")
  protected Constructor<?> constructorFor(Signature sig) throws NoSuchMethodException, SecurityException {
    return sig.getDeclaringType().getConstructor(((ConstructorSignature) sig).getParameterTypes());
  }
  
  


  @Before("methodPC() && ValidateParameters()")
  public void validateMethodParameters(JoinPoint jp) throws NoSuchMethodException, SecurityException {
    System.out.println("method parameter validation");
    Method method = methodFor(jp.getSignature());
    System.out.println(jp);
    System.out.println(method);
    System.out.println(jp.getThis());
    System.out.println(jp.getTarget());
//    validator.validateParameters(object, method, parameterValues, groups)
  }
  
  @AfterReturning(value = "methodPC() && validateRetVal()", returning = "returnValue")
  public void validateMethodReturnValue(JoinPoint jp, Object returnValue) throws NoSuchMethodException, SecurityException {
//    if (checkForConstraints(returnAnnotations(jp.getSignature()))) {
      System.out.println(returnValue);
      System.out.println("method return");
      System.out.println(jp);
//    }
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
