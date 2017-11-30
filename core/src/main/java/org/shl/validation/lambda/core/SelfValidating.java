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
package org.shl.validation.lambda.core;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * This interface adds the ability for objects to self validate.  
 * This allows validatable objects to be detected and validated without passing 
 *      them to an external validator service.
 */
public interface SelfValidating {
    /**
     * Validate this object.  
     * Depending on the validation service in use, may return the violations 
     *      or throw a {@see ConstraintViolationException} 
     * 
     * @return If the return based validator is in use, returns The set of constraint violation, 
     *              or the empty set if the object is valid
     * @throws ConstraintViolationException If the throw based validator is in use, 
     *              throws a {@see ConstraintViolationException} with the constraint violations
     *              or returns null if the object is valid
     * @throws ValidationException If there is some error during validating the object
     */
	Set<ConstraintViolation<Object>> validate() throws ConstraintViolationException, ValidationException;

    /**
     * Validate this object against the given groups.  
     * Depending on the validation service in use, may return the violations 
     *     or throw a {@see ConstraintViolationException} 
     * 
     * @param groups The validation groups to apply.
     * @return If the return based validator is in use, returns The set of constraint violation, 
     *              or the empty set if the object is valid
     * @throws ConstraintViolationException If the throw based validator is in use, 
     *              throws a {@see ConstraintViolationException} with the constraint violations
     *              or returns null if the object is valid
     * @throws ValidationException If there is some error during validating the object
     */
	Set<ConstraintViolation<Object>> validate(final Class<?>[] groups) throws ConstraintViolationException, ValidationException;

}
