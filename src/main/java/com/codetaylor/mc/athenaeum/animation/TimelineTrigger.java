package com.codetaylor.mc.athenaeum.animation;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TimelineTrigger {

  private NavigableMap<Float, KeyFrameTrigger> keyFrameMap;

  public TimelineTrigger() {

    this.keyFrameMap = new TreeMap<>();
  }

  public TimelineTrigger addKeyFrame(float position, Runnable action) {

    this.keyFrameMap.put(position, new KeyFrameTrigger(action));
    return this;
  }

  public void reset() {

    for (KeyFrameTrigger keyFrameTrigger : this.keyFrameMap.values()) {
      keyFrameTrigger.reset();
    }
  }

  public void update(float position) {

    Map.Entry<Float, KeyFrameTrigger> prev = this.keyFrameMap.floorEntry(position);

    if (prev != null) {
      prev.getValue().doAction();
    }
  }

}
