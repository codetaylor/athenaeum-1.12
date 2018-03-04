package com.codetaylor.mc.athenaeum.animation;

import java.util.HashMap;
import java.util.Map;

public class Animation {

  private Map<String, TimelineFloat> timelineFloatMap;

  public Animation() {

    this.timelineFloatMap = new HashMap<>();
  }

  public void putTimeline(String name, TimelineFloat timeline) {

    this.timelineFloatMap.put(name, timeline);
  }

  public float getFloat(String name, float position) {

    return this.timelineFloatMap.get(name).getValue(position);
  }
}
