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
