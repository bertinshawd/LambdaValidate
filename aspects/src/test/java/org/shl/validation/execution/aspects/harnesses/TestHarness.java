package org.shl.validation.execution.aspects.harnesses;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.testng.Assert;

/*
 * I wanted to lift the test methods and the method under test here, 
 * but TestNG's annotations do not inherit and the hibernate validator 
 * doesn't allow overriding the constraints on a method, so unfortunately
 * I had to do some copy-paste coding.
 */
public abstract class TestHarness {

  protected void expectMsg(ConstraintViolationException cve, String expectMsg) {
    for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
      if (cv.getMessage().equals(expectMsg)) {
        throw cve;
      }
    }
    Assert.fail("cannot find message `%s` in constraint violations");
  }

  private boolean broken = false;

  public void resetBreakState() {
    broken = false;
  }

  protected void setBreakState() {
    broken = true;
  }

  protected boolean getBreakState() {
    return broken;
  }
  
}
