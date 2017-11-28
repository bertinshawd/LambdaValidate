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
package org.shl.lambdavalidation.groovy

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

import org.shl.lambdavalidation.SelfValidating
import org.shl.lambdavalidation.ValidationService

/**
 * Trait that implements self-validating behaviour for the returning validation service.
 */
trait ThrowingSelfValidating implements SelfValidating{
   @Override
   public Set<ConstraintViolation<Object>> validate() throws ConstraintViolationException {
     return ValidationService.instance().thrower.validate(this);
   }
   
   @Override
   public Set<ConstraintViolation<Object>> validate(Class<?>[] groups) throws ConstraintViolationException {
     return ValidationService.instance().thrower.validate(this, groups);
   }
}

/**
 * Trait that implements self-validating behaviour for the throwing validation service.
 */
trait ReturningSelfValidating implements SelfValidating{
  @Override
  public Set<ConstraintViolation<Object>> validate() throws ConstraintViolationException {
    return ValidationService.instance().returning.validate(this);
  }
  
  @Override
  public Set<ConstraintViolation<Object>> validate(Class<?>[] groups) throws ConstraintViolationException {
    return ValidationService.instance().returning.validate(this, groups);
  }
}
