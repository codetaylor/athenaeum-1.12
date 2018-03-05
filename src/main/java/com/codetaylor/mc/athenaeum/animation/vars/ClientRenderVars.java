package com.codetaylor.mc.athenaeum.animation.vars;

import com.codetaylor.mc.athenaeum.animation.Animation;

import java.util.HashMap;
import java.util.Map;

public class ClientRenderVars {

  private Map<String, IntVar> intVarMap;
  private Map<String, FloatVar> floatVarMap;
  private Map<String, Animation> animationMap;

  public ClientRenderVars() {

    this.intVarMap = new HashMap<>();
    this.floatVarMap = new HashMap<>();
    this.animationMap = new HashMap<>();
  }

  public int getInt(String name, int defaultValue) {

    IntVar intVar = this.intVarMap.get(name);

    if (intVar == null) {
      intVar = new IntVar(defaultValue);
      this.intVarMap.put(name, intVar);
    }

    return intVar.getValue();
  }

  public void setInt(String name, int value) {

    IntVar intVar = this.intVarMap.get(name);

    if (intVar == null) {
      intVar = new IntVar(value);
      this.intVarMap.put(name, intVar);

    } else {
      intVar.setValue(value);
    }
  }

  public float getFloat(String name, float defaultValue) {

    FloatVar floatVar = this.floatVarMap.get(name);

    if (floatVar == null) {
      floatVar = new FloatVar(defaultValue);
      this.floatVarMap.put(name, floatVar);
    }

    return floatVar.getValue();
  }

  public void setFloat(String name, float value) {

    FloatVar floatVar = this.floatVarMap.get(name);

    if (floatVar == null) {
      floatVar = new FloatVar(value);
      this.floatVarMap.put(name, floatVar);

    } else {
      floatVar.setValue(value);
    }
  }

  public Animation getAnimation(String name, Animation defaultValue) {

    return this.animationMap.computeIfAbsent(name, k -> defaultValue);
  }

  public void setAnimation(String name, Animation value) {

    this.animationMap.put(name, value);
  }
}
