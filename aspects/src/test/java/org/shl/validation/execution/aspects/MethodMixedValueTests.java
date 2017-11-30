package org.shl.validation.execution.aspects;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import org.shl.validation.execution.aspects.harnesses.TestHarness;
import org.shl.validation.execution.aspects.supportclasses.NotAConstraint;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MethodMixedValueTests extends TestHarness {

  @NotNull
  @NotAConstraint
  @ValidateReturnValue
  @ValidateParameters
  protected String _method(@NotAConstraint @NotNull String a) {
    if (getBreakState()) {
      return null;
    }
    return a.toUpperCase();
  }

  @BeforeClass
  @BeforeMethod
  public void resetBreakState() {
    super.resetBreakState();
  }

  @Test
  public void testValidData() {
    _method("foo");
  }

  @Test(expectedExceptions = { ConstraintViolationException.class })
  public void testInvalidReturnData() {
    setBreakState();
    try {
      _method(null);
    } catch (ConstraintViolationException e) {
      expectMsg(e, "must not be null");
    }
  }

  @Test(expectedExceptions = { ConstraintViolationException.class })
  public void testInvalidParamData() {
    try {
      _method(null);
    } catch (ConstraintViolationException e) {
      expectMsg(e, "must not be null");
    }
  }
}
