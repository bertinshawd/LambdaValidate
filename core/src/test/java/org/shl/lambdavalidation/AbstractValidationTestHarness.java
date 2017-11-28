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


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.shl.lambdavalidation.ValidationService.Validator;
import org.testng.annotations.DataProvider;

public abstract class AbstractValidationTestHarness<T extends SelfValidating> {

  protected final class ConstraintViolationManipulationUtil {
    public ConstraintViolationManipulationUtil() {
    }

    @SuppressWarnings("unchecked")
    protected Set<ConstraintViolation<Object>> violations(final ConstraintViolationException e) {
      final Set<ConstraintViolation<Object>> cvCopy = new HashSet<>();
      for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
        cvCopy.add((ConstraintViolation<Object>) cv);
      }
      return cvCopy;
    }

    protected Set<String> violationMessages(final Set<ConstraintViolation<Object>> e) {
      Set<String> messages = new HashSet<>();
      for (ConstraintViolation<?> cv : e) {
        messages.add(cv.getMessage());
      }
      return messages;
    }
  }

  protected final class Assertions {
    private Assertions() {
    }

    @SuppressWarnings("unchecked")
    protected void sameViolationSet(final Set<ConstraintViolation<Object>> cvSet1, final Set<ConstraintViolation<Object>> cvSet2) {
      for (ConstraintViolation<Object> cv1 : cvSet1) {
        MatcherAssert.assertThat(cvSet2, Matchers.contains(cv1));
      }
      for (ConstraintViolation<Object> cv2 : cvSet2) {
        MatcherAssert.assertThat(cvSet1, Matchers.contains(cv2));
      }
    }

    protected void constraintsContainMessage(final ConstraintViolationException e, final String expectMsg) {
      constraintsContainMessage(manipulators.violations(e), expectMsg);
    }

    protected void constraintsContainMessage(final Set<ConstraintViolation<Object>> violations, final String expectMsg) {
      MatcherAssert.assertThat(manipulators.violationMessages(violations), Matchers.contains(expectMsg));
    }

    protected void violationsMatchThrown(final T object, final Class<?>[] groups) throws ConstraintViolationException, ValidationException {
      try {
        object.validate(groups);
      } catch (ConstraintViolationException e) {
        sameViolationSet(returner.validate(object, groups), manipulators.violations(e));
      }
    }

    protected void violationsMatchThrown(final T object) throws ConstraintViolationException, ValidationException {
      try {
        object.validate();
      } catch (ConstraintViolationException e) {
        sameViolationSet(returner.validate(object), manipulators.violations(e));
      }
    }

    protected void isValid(final SelfValidating object, final Class<?>[] groups) {
      assert object.validate(groups).isEmpty();
    }

    protected void isValid(final SelfValidating object) {
      assert object.validate().isEmpty();
    }
  }

  protected final Assertions assertions = new Assertions();
  protected final ConstraintViolationManipulationUtil manipulators = new ConstraintViolationManipulationUtil();
  protected final Validator returner = ValidationService.instance().returning;

  protected void testValid(final T object, final Class<?>[] groups) {
    assertions.isValid(object, groups);
  }

  protected void testValid(final T object) {
    assertions.isValid(object);
  }
  

  public AbstractValidationTestHarness(Class<T> testCaseClass) {
    super();
    this.testCaseClass = testCaseClass;
    this.constructor = getConstructor();
  }

  private final Class<T> testCaseClass;
  private final Constructor<T> constructor;

  private Constructor<T> getConstructor() {
    try {
      return testCaseClass.getConstructor(Integer.class, Integer.class);
    } catch (NoSuchMethodException | SecurityException e) {
      throw new RuntimeException(e);
    }
  }

  protected T newInstance(final Integer a, final Integer b) {
    try {
      return constructor.newInstance(a, b);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  // @formatter:off
  @DataProvider
  public Object[][] positiveTestCases() {
    return new Object[][] { 
      { newInstance(1, 2) }, 
      { newInstance(0, 1) }, 
      { newInstance(-1, 0) } 
    };
  }

  @DataProvider
  public Object[][] negativeTestCases() {
    return new Object[][] { 
      { newInstance(2, 1) }, 
      { newInstance(1, 0) }, 
      { newInstance(0, -1) }
    };
  }

  @DataProvider
  public Object[][] positiveEqualTestCases() {
    return new Object[][] { 
      { newInstance(1, 1) }, 
      { newInstance(0, 0) }, 
      { newInstance(-1, -1) },
      { newInstance(1, 2) }, 
      { newInstance(0, 1) }, 
      { newInstance(-1, 0) } 
    };
  }
  // @formatter:on

  abstract protected void testInvalid(String expectMsg, T object);

  abstract protected void testInvalid(String expectMsg, T object, Class<?>[] groups);
}
