package org.shl.validation.execution.aspects;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.shl.validation.execution.aspects.harnesses.TestHarness;
import org.shl.validation.execution.aspects.supportclasses.CreatePhase;
import org.shl.validation.execution.aspects.supportclasses.NotAConstraint;
import org.shl.validation.execution.aspects.supportclasses.UpdatePhase;
import org.testng.annotations.Test;

public class ObjectGroupTests extends TestHarness{

  public static class GroupsTestObject {
    @Null(groups = {CreatePhase.class})
    @NotNull(groups = {UpdatePhase.class})
    @NotAConstraint
    private Long id = null;

    public GroupsTestObject(Long id) {
      this.id = id;
    }
    
    @Override
    public String toString() {
      return String.format("[%d]", id);
    }
  }
  
  @Valid
  @ValidateReturnValue(groups = {CreatePhase.class})
  private GroupsTestObject createPhaseTest(Long l) {
    return new GroupsTestObject(l);
  }

  @Valid
  @ValidateReturnValue(groups = {UpdatePhase.class})
  private GroupsTestObject updatePhaseTest(Long l) {
    return new GroupsTestObject(l);
  }
 
  @Test
  public void testMethodReturnPassCreate() {
    createPhaseTest(null);
  }
 
  @Test
  public void testMethodReturnPassUpdate() {
    updatePhaseTest(1l);
  }
  
  @Test(expectedExceptions = {ConstraintViolationException.class})
  public void testInvalidData() {
    try {
      updatePhaseTest(null);
    }catch(ConstraintViolationException e) {
      expectMsg(e, "must not be null");
    }
  }
}
