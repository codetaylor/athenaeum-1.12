package com.codetaylor.mc.athenaeum.animation;

import com.sudoplay.math.Tween;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TimelineFloat {

  private NavigableMap<Float, KeyFrameFloat> itemOffsetYKeyFrameMap;

  public TimelineFloat() {

    this.itemOffsetYKeyFrameMap = new TreeMap<>();
  }

  public TimelineFloat addKeyFrame(float position, float value) {

    this.addKeyFrame(position, value, Tween.LINEAR);
    return this;
  }

  public TimelineFloat addKeyFrame(float position, float value, Tween tween) {

    this.itemOffsetYKeyFrameMap.put(position, new KeyFrameFloat(value, tween));
    return this;
  }

  public float getValue(float position) {

    Map.Entry<Float, KeyFrameFloat> prev = this.itemOffsetYKeyFrameMap.floorEntry(position);
    Map.Entry<Float, KeyFrameFloat> next = this.itemOffsetYKeyFrameMap.higherEntry(position);

    if (prev == null && next == null) {
      throw new IllegalStateException("Null timeline");

    } else if (prev == null) {
      return next.getValue().getValue();

    } else if (next == null) {
      return prev.getValue().getValue();

    } else if (prev == next) {
      return prev.getValue().getValue();
    }

    float prevTime = prev.getKey();
    float nextTime = next.getKey();
    float prevValue = prev.getValue().getValue();
    float nextValue = next.getValue().getValue();
    Tween tween = prev.getValue().getTween();

    return tween.tween(position - prevTime, prevValue, nextValue - prevValue, nextTime - prevTime);
  }
}
