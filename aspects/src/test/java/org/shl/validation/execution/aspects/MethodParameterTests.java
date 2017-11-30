package org.shl.validation.execution.aspects;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import org.shl.validation.execution.aspects.harnesses.TestHarness;
import org.shl.validation.execution.aspects.supportclasses.NotAConstraint;
import org.testng.annotations.Test;

public class MethodParameterTests extends TestHarness{

  @ValidateParameters
  private String _method(@NotAConstraint @NotNull String a) {
    return a.toUpperCase();
  }
 
  @Test
  public void testValidData() {
    _method("foo");
  }
  
  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void testInvalidData() {
    try {
      _method(null);     
    }catch(ConstraintViolationException e) {
      expectMsg(e, "must not be null");
    }
  }
}
