/*
 * Copyright © Daniel Bertinshaw, 2017
 * 
 * See the LICENSE file distributed with this work for additional
 * information regarding copyright ownership.  The ASF licenses 
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.shl.validation.functional;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidationException;

/**
 * A basic implementation of the JSR303 validator, which is provider agnostic.
 * 
 * Contains a throwing and returning implementation.
 */
public final class ValidationService {

  /**
   * An interface to embody validation.
   */
  public interface Validator {
    /**
     * Validate this object. Depending on the validation service in use, may return
     * the violations or throw a {@see ConstraintViolationException}
     * 
     * @param object The object to validate.
     * @return If the return based validator is in use, returns The set of
     *         constraint violation, or the empty set if the object is valid
     * @throws ConstraintViolationException
     *           If the throw based validator is in use, throws a
     *           {@see ConstraintViolationException} with the constraint violations
     *           or returns null if the object is valid
     * @throws ValidationException
     *           If there is some error during validating the object
     */
    Set<ConstraintViolation<Object>> validate(final Object object) throws ConstraintViolationException, ValidationException;

    /**
     * Validate this object against the given groups.  
     * Depending on the validation service in use, may return the violations 
     *     or throw a {@see ConstraintViolationException} 
     * 
     * @param object The object to validate.
     * @param groups The validation groups to apply.
     * @return If the return based validator is in use, returns The set of constraint violation, 
     *              or the empty set if the object is valid
     * @throws ConstraintViolationException If the throw based validator is in use, 
     *              throws a {@see ConstraintViolationException} with the constraint violations
     *              or returns null if the object is valid
     * @throws ValidationException If there is some error during validating the object
     */
    Set<ConstraintViolation<Object>> validate(final Object object, final Class<?>[] groups) throws ConstraintViolationException, ValidationException;
  }

  /**
   * Hidden constructor, since this is a singleton service
   */
  private ValidationService() {
  }

  /**
   * The singleton instance.
   */
  private static final ValidationService instance = new ValidationService();

  /**
   * @return The singleton instance of this class.
   */
  public static ValidationService instance() {
    return instance;
  }

  /**
   * The JSR303 validator.
   */
  private final javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  /**
   * Throw an exception if the constraint set is non-empty
   * 
   * @param violations A set of constraint violations.
   * @return The empty set of violations, if it is empty
   * @throws ConstraintViolationException An exception containing the constraint violations, 
   *            if the constraint set is non-empty
   */
  private Set<ConstraintViolation<Object>> throwWhenNonEmpty(final Set<ConstraintViolation<Object>> violations) throws ConstraintViolationException {
    if (violations.isEmpty()) {
      return violations;
    }
    throw new ConstraintViolationException(violations);
  }

  /**
   * The implementation of {@link Validator} that throws exceptions.
   */
  public final Validator thrower = new Validator() {
    @Override
    public Set<ConstraintViolation<Object>> validate(final Object object) throws ConstraintViolationException, ValidationException {
      return throwWhenNonEmpty(validator.validate(object));
    }

    @Override
    public Set<ConstraintViolation<Object>> validate(final Object object, final Class<?>[] groups) throws ConstraintViolationException, ValidationException {
      return throwWhenNonEmpty(validator.validate(object, groups));
    }
  };

  /**
   * The implementation of {@link Validator} that returns a Constraint Violation Set.
   */
  public final Validator returning = new Validator() {
    @Override
    public Set<ConstraintViolation<Object>> validate(final Object object) throws ConstraintViolationException, ValidationException {
      return validator.validate(object);
    }

    @Override
    public Set<ConstraintViolation<Object>> validate(final Object object, final Class<?>[] groups) throws ConstraintViolationException, ValidationException {
      return validator.validate(object, groups);
    }
  };
}