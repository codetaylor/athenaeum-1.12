package com.codetaylor.mc.athenaeum.animation;

import com.sudoplay.math.Tween;

public class KeyFrameFloat {

  private float value;
  private Tween tween;

  public KeyFrameFloat(float value, Tween tween) {

    this.value = value;
    this.tween = tween;
  }

  public float getValue() {

    return this.value;
  }

  public Tween getTween() {

    return this.tween;
  }
}
