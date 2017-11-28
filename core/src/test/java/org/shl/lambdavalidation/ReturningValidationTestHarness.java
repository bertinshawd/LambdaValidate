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
package org.shl.lambdavalidation;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.testng.Assert;

public abstract class ReturningValidationTestHarness<T extends SelfValidating> extends AbstractValidationTestHarness<T> {
  public ReturningValidationTestHarness(Class<T> testCaseClass) {
    super(testCaseClass);
  }

  @Override
  protected void testInvalid(final String expectMsg, final T object) {
    Set<ConstraintViolation<Object>> violations = object.validate();
    Assert.assertFalse(violations.isEmpty(), "Expected negative test case %s passed validation");
    assertions.constraintsContainMessage(violations, expectMsg);
  }

  @Override
  protected void testInvalid(final String expectMsg, final T object, Class<?>[] groups) {
    Set<ConstraintViolation<Object>> violations = object.validate(groups);
    Assert.assertFalse(violations.isEmpty(), "Expected negative test case %s passed validation");
    assertions.constraintsContainMessage(violations, expectMsg);
  }
}
