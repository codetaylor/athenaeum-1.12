package com.codetaylor.mc.athenaeum.animation;

import java.util.HashMap;
import java.util.Map;

public class Animation {

  public static final Animation NO_OP = new Animation();

  private Map<String, TimelineFloat> timelineFloatMap;
  private Map<String, TimelineTrigger> timelineTriggerMap;

  public Animation() {

    this.timelineFloatMap = new HashMap<>();
    this.timelineTriggerMap = new HashMap<>();
  }

  public void putTimeline(String name, TimelineFloat timeline) {

    this.timelineFloatMap.put(name, timeline);
  }

  public float getFloat(String name, float position, float defaultValue) {

    TimelineFloat timelineFloat = this.timelineFloatMap.get(name);

    if (timelineFloat != null) {
      return timelineFloat.getValue(position);
    }

    return defaultValue;
  }

  public void putTimeline(String name, TimelineTrigger timeline) {

    this.timelineTriggerMap.put(name, timeline);
  }

  public void updateTrigger(String name, float position) {

    TimelineTrigger timelineTrigger = this.timelineTriggerMap.get(name);

    if (timelineTrigger != null) {
      timelineTrigger.update(position);
    }
  }

  public void resetTrigger(String name) {

    TimelineTrigger timelineTrigger = this.timelineTriggerMap.get(name);

    if (timelineTrigger != null) {
      timelineTrigger.reset();
    }
  }
}
