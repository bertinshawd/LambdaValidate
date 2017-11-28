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

import org.shl.lambdavalidation.AbstractTestCase
import org.shl.lambdavalidation.ValidationFunction
import org.shl.lambdavalidation.validationgroups.NonStrict
import org.shl.lambdavalidation.validationgroups.Strict

public class ExceptionTestCase extends AbstractTestCase implements ThrowingSelfValidating, ConstraintFunctionBuilder {
  public ExceptionTestCase(Integer start, Integer end) {
    super(start, end)
  }

  @ValidationFunction(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = constraintFunction { throw new RuntimeException() }
}


public class ThrowingGroupedTestCase extends AbstractTestCase implements ThrowingSelfValidating, ConstraintFunctionBuilder {
  public ThrowingGroupedTestCase(Integer start, Integer end) {
    super(start, end)
  }
  @ValidationFunction(message = "start must strictly be less than end", groups = Strict.class)
  private final ConstraintFunction startLessThanEndStrict = constraintFunction { start < end }
  
  @ValidationFunction(message = "start must be less than or equal to end", groups = NonStrict.class)
  private final ConstraintFunction startLessThanEndNonStrict = constraintFunction { start <= end }
}


public class ThrowingUngroupedTestCase extends AbstractTestCase implements ThrowingSelfValidating, ConstraintFunctionBuilder {
  public ThrowingUngroupedTestCase(Integer start, Integer end) {
    super(start, end)
  }

  @ValidationFunction(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = constraintFunction { start < end }
}

public class ReturningGroupedTestCase extends AbstractTestCase implements ReturningSelfValidating, ConstraintFunctionBuilder {
  public ReturningGroupedTestCase(Integer start, Integer end) {
    super(start, end)
  }
  @ValidationFunction(message = "start must strictly be less than end", groups = Strict.class)
  private final ConstraintFunction startLessThanEndStrict = constraintFunction { start < end }
  
  @ValidationFunction(message = "start must be less than or equal to end", groups = NonStrict.class)
  private final ConstraintFunction startLessThanEndNonStrict = constraintFunction { start <= end }
}


public class ReturningUngroupedTestCase extends AbstractTestCase implements ReturningSelfValidating, ConstraintFunctionBuilder {
  public ReturningUngroupedTestCase(Integer start, Integer end) {
    super(start, end)
  }

  @ValidationFunction(message = "start must be less than end")
  private final ConstraintFunction startLessThanEnd = constraintFunction { start < end }
}