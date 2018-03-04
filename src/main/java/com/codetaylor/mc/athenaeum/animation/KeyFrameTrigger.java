package com.codetaylor.mc.athenaeum.animation;

public class KeyFrameTrigger {

  private Runnable action;
  private boolean triggered;

  public KeyFrameTrigger(Runnable action) {

    this.action = action;
  }

  public void doAction() {

    if (!this.triggered) {
      this.triggered = true;
      this.action.run();
    }
  }

  public void reset() {

    this.triggered = false;
  }
}
