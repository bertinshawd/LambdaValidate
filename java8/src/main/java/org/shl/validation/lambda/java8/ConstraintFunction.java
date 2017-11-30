package org.shl.validation.lambda.java8;

import java.util.function.BooleanSupplier;

/**
 * Facade interface to allow BooleanSupplier to act as a ConstraintFunction
 */
@FunctionalInterface
public interface ConstraintFunction extends BooleanSupplier, org.shl.validation.lambda.core.ConstraintFunction {
  /**
   * Delegates to getAsBoolean() as speficied in BooleanSupplier
   */
  @Override
  default public boolean validate() {
    return getAsBoolean();
  }
}
