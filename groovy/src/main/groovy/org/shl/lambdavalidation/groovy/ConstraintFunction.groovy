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

import groovy.lang.MetaMethod

/**
 * A constraint function that delegates to a groovy closure.
 * 
 * Since groovy closures are a class, not an interface, and since 
 * type coercion/casting is more preferable, this has 
 * been implemented as a delegating facade to a closure instance.
 * 
 * The means that additional behaviors can still be extended/mixed in.
 */
public class ConstraintFunction implements org.shl.lambdavalidation.ConstraintFunction{

  /**
   * This method is intended to be called during application loading to add a cast 
   * to the groovy MetaClass for Closure, but does not reliably work.  It works 
   * in experimental scripts, but not in test suites.
   * 
   * @deprecated Unreliable 
   */
  @Deprecated
  public static void enableClosureCasting() {
    System.err.println("enabling closure casting")
    ExpandoMetaClass.enableGlobally()

    MetaMethod oldAsType = Closure.metaClass.getMetaMethod("asType", [Class] as Class[])
    System.err.println(oldAsType)
    Closure.metaClass.asType = { Class k ->
      System.err.println("casting type %s".format(k))
      if (k == ConstraintFunction) {
        ConstraintFunction.of delegate
      } else {
        oldAsType.invoke(delegate, [k] as Class[])
      }
    }
  }

  /** 
   * The groovy closure that does the validation.
   */
  private final Closure<Boolean> delegateFunction

  /**
   * Construct a ConstraintFunction out of a groovy closure
   * 
   * @param delegateFunction The groovy closure that does the validation.
   */
  public ConstraintFunction(Closure<Boolean> delegateFunction) {
    this.delegateFunction = delegateFunction
  }

  /**
   * Delegates to the groovy closure
   */
  @Override
  public boolean validate() throws Exception {
    delegateFunction()
  }

  /**
   * Static construction helper, since adding an asType is not a simple solution.
   * 
   * @param closure The delgate Closure that does the validation.
   * @return A new ConstraintFunction instance for the delegate Closure
   */
  public static ConstraintFunction of(Closure<Boolean> closure) {
    return new ConstraintFunction(closure)
  }
}

trait ConstraintFunctionBuilder {
  public static constraintFunction(Closure<Boolean> closure) {
    ConstraintFunction.of(closure)
  }
}

