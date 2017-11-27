package org.shl.validation.functional.java8;

import java.util.function.BooleanSupplier;

/**
 * Facade interface for BooleanSupplier
 */
@FunctionalInterface
public interface ConstraintFunction extends BooleanSupplier, org.shl.validation.functional.ConstraintFunction {
  /**
   * Delegates to getAsBoolean() as speficied in BooleanSupplier
   */
  @Override
  default public boolean validate() {
    return getAsBoolean();
  }
}
