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
package org.shl.validation.functional.scala

import org.shl.validation.functional.{ConstraintFunction => BaseConstraintFunction}
import org.shl.validation.functional.ValidationService
import javax.validation.ConstraintViolation
import org.shl.validation.functional.SelfValidating

/**
 * Private trait that mixes in the specific 
 */
sealed private[scala] trait ScalaSelfValidating extends SelfValidating  {
  protected type CVSet = java.util.Set[ConstraintViolation[Object]]
  protected val validator = ValidationService.instance().thrower
  
  override final def validate():CVSet = validator.validate(this)
  override final def validate(groups:Array[Class[_]]):CVSet = validator.validate(this, groups)
}

/**
 * Trait that implements self-validating behaviour for the throwing validation service.
 */
trait ThrowingSelfValidating extends ScalaSelfValidating {
}

/**
 * Trait that implements self-validating behaviour for the throwing validation service.
 */
trait ReturningSelfValidating extends ScalaSelfValidating{
  override val validator = ValidationService.instance().returning
}

/**
 * Trait that implements welds the scala function trait to the constraint validator interface.
 * Delegates to the apply() method.
 * 
 * The scala compiler will automatically coerce a lambda definition to ConstraintFunction, where the 
 * field is typed as such.
 */
trait ConstraintFunction extends BaseConstraintFunction with Function0[Boolean] {
 override final def validate() = apply() 
}
