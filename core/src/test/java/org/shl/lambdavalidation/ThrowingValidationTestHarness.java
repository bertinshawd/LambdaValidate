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

import javax.validation.ConstraintViolationException;

import org.testng.Assert;

public abstract class ThrowingValidationTestHarness<T extends SelfValidating> extends AbstractValidationTestHarness<T> {
  public ThrowingValidationTestHarness(Class<T> testCaseClass) {
    super(testCaseClass);
  }

  @Override
  protected void testInvalid(final String expectMsg, final T object) {
    try {
      object.validate();
      Assert.fail(String.format("Expected negative test case %s passed validation", object));
    } catch (ConstraintViolationException cve) {
      assertions.violationsMatchThrown(object);
      assertions.constraintsContainMessage(cve, expectMsg);
      throw cve;
    }
  }

  @Override
  protected void testInvalid(final String expectMsg, final T object, Class<?>[] groups) {
    try {
      object.validate(groups);
      Assert.fail(String.format("Expected negative test case %s passed validation", object));
    } catch (ConstraintViolationException cve) {
      assertions.violationsMatchThrown(object, groups);
      assertions.constraintsContainMessage(cve, expectMsg);
      throw cve;
    }
  }
}
