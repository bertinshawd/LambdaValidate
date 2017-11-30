package org.shl.validation.execution.aspects;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import org.shl.validation.execution.aspects.harnesses.TestHarness;
import org.testng.annotations.Test;

public class ObjectConstructorParamsTest extends TestHarness{

  public static class TestObject {
    private Long id = null;

    @ValidateParameters
    public TestObject(@NotNull Long id) {
      this.id = id;
    }
    
    @Override
    public String toString() {
      return String.format("[%d]", id);
    }
  }
  
 
  @Test
  public void testMethodReturnPassCreate() {
    new TestObject(1l);
  }
  
  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void testInvalidData() {
    try {
      new TestObject(null);
    }catch(ConstraintViolationException e) {
      expectMsg(e, "must not be null");
    }
  }
}
