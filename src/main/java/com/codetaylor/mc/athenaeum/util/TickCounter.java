package com.codetaylor.mc.athenaeum.util;

public class TickCounter {

  private final int max;
  private int count;

  public TickCounter(int max) {

    this(max, 0);
  }

  public TickCounter(int max, int count) {

    this.max = max;
    this.count = count;
  }

  public boolean increment() {

    return this.increment(1);
  }

  private boolean increment(int ticks) {

    this.count += ticks;

    if (this.count >= this.max) {
      this.count = 0;
      return true;
    }

    return false;
  }
}