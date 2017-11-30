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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

/**
 * Validator for constraint functions.
 * 
 */
public class ConstraintFunctionValidator implements ConstraintValidator<ValidationFunction, ConstraintFunction> {
   @SuppressWarnings("unused")
   private ValidationFunction constraintAnnotation;
   
   @Override
   public void initialize(final ValidationFunction constraintAnnotation) {
      this.constraintAnnotation = constraintAnnotation;
   }
   
   /**
    * Calls the constraint function's validate() method.
    */
   @Override
   public boolean isValid(final ConstraintFunction function, final ConstraintValidatorContext context) {
      try {
         return function.validate();
      }catch(Exception e) {
         throw new ValidationException(e);
      }
   }
}
