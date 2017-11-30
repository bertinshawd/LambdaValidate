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
package org.shl.validation.lambda.scala

import org.shl.validation.lambda.core.testing.AbstractTestCase
import org.testng.annotations.Test
import javax.validation.ConstraintViolationException
import javax.validation.ValidationException
import scala.reflect.ClassTag
import scala.reflect.classTag
import org.shl.validation.lambda.core.ValidationFunction
import org.shl.validation.lambda.core.testing.groups.{Strict => ValStrict}
import org.shl.validation.lambda.core.testing.groups.{NonStrict => NonValStrict}

case class ExceptionTestCase(val s: Integer, val e: Integer) extends AbstractTestCase(s, e) with ThrowingSelfValidating {
  @ValidationFunction(message = "start must be less than end")
  val startLessThanEnd = ConstraintFunction { throw new RuntimeException() }
}

case class ThrowingUngroupedTestCase(val s: Integer, val e: Integer) extends AbstractTestCase(s, e) with ThrowingSelfValidating {
  @ValidationFunction(message = "start must be less than end")
  val startLessThanEnd = ConstraintFunction { start < end }
}

case class ThrowingGroupedTestCase(val s: Integer, val e: Integer) extends AbstractTestCase(s, e) with ThrowingSelfValidating {
  @ValidationFunction(message = "start must strictly be less than end", groups = Array(classOf[ValStrict]))
  val startLessThanEndValStrict: ConstraintFunction = () => { start < end }

  @ValidationFunction(message = "start must be less than or equal to end", groups = Array(classOf[NonValStrict]))
  val startLessThanEndNonValStrict = ConstraintFunction { start <= end };
}


case class ReturningUngroupedTestCase(val s: Integer, val e: Integer) extends AbstractTestCase(s, e) with ReturningSelfValidating {
  @ValidationFunction(message = "start must be less than end")
  val startLessThanEnd = ConstraintFunction { start < end }
}

case class ReturningGroupedTestCase(val s: Integer, val e: Integer) extends AbstractTestCase(s, e) with ReturningSelfValidating {
  @ValidationFunction(message = "start must strictly be less than end", groups = Array(classOf[ValStrict]))
  val startLessThanEndValStrict = ConstraintFunction { start < end }

  @ValidationFunction(message = "start must be less than or equal to end", groups = Array(classOf[NonValStrict]))
  val startLessThanEndNonValStrict = ConstraintFunction { start <= end };
}
