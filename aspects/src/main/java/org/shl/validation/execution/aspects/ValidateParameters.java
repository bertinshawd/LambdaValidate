package org.shl.validation.execution.aspects;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * ValidateOnExecute doesn't allow you to set validation groups.
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD, CONSTRUCTOR })
public @interface ValidateParameters {
  Class<?>[] groups() default { };
}
