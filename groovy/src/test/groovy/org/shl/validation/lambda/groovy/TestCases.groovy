/*
 * Copyright © Daniel Bertinshaw, 2017
 *
 * See the LICENSE file distributed with this work for additional
 * information regarding copyright ownership.  The ASF licenses
 * this file to you under the Apache License, Version 2.0 (the
 * 'License'); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * 'AS IS' BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.shl.validation.lambda.groovy

import org.shl.validation.lambda.core.testing.AbstractTestCase
import org.shl.validation.lambda.core.ValidationFunction
import org.shl.validation.lambda.core.testing.groups.NonStrict
import org.shl.validation.lambda.core.testing.groups.Strict

class TestException extends RuntimeException {
}

class ExceptionTestCase extends AbstractTestCase implements ThrowingSelfValidating, ConstraintFunctionBuilder {
  ExceptionTestCase(Integer start, Integer end) {
    super(start, end)
  }

  @ValidationFunction(message = 'start must be less than end')
  private final ConstraintFunction startLessThanEnd = constraintFunction { throw new TestException() }
}

class ThrowingGroupedTestCase extends AbstractTestCase implements ThrowingSelfValidating, ConstraintFunctionBuilder {
  ThrowingGroupedTestCase(Integer start, Integer end) {
    super(start, end)
  }
  @ValidationFunction(message = 'start must strictly be less than end', groups = Strict)
  private final ConstraintFunction startLessThanEndStrict = constraintFunction { start < end }

  @ValidationFunction(message = 'start must be less than or equal to end', groups = NonStrict)
  private final ConstraintFunction startLessThanEndNonStrict = constraintFunction { start <= end }
}

class ThrowingUngroupedTestCase extends AbstractTestCase implements ThrowingSelfValidating, ConstraintFunctionBuilder {
  ThrowingUngroupedTestCase(Integer start, Integer end) {
    super(start, end)
  }

  @ValidationFunction(message = 'start must be less than end')
  private final ConstraintFunction startLessThanEnd = constraintFunction { start < end }
}

class ReturningGroupedTestCase extends AbstractTestCase implements ReturningSelfValidating, ConstraintFunctionBuilder {
  ReturningGroupedTestCase(Integer start, Integer end) {
    super(start, end)
  }
  @ValidationFunction(message = 'start must strictly be less than end', groups = Strict)
  private final ConstraintFunction startLessThanEndStrict = constraintFunction { start < end }

  @ValidationFunction(message = 'start must be less than or equal to end', groups = NonStrict)
  private final ConstraintFunction startLessThanEndNonStrict = constraintFunction { start <= end }
}

class ReturningUngroupedTestCase extends AbstractTestCase implements ReturningSelfValidating, ConstraintFunctionBuilder {
  ReturningUngroupedTestCase(Integer start, Integer end) {
    super(start, end)
  }

  @ValidationFunction(message = 'start must be less than end')
  private final ConstraintFunction startLessThanEnd = constraintFunction { start < end }
}
