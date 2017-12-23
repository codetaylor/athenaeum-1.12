package com.codetaylor.mc.athenaeum.integration.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PluginDelegateCraftTweaker {

  public static final List<IAction> LATE_REMOVALS = new LinkedList<>();
  public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();

  private static final List<Class<?>> CLASS_LIST;

  static {
    CLASS_LIST = new ArrayList<>();
  }

  public static void registerZenClass(Class<?> zenClass) {

    CLASS_LIST.add(zenClass);
  }

  public static void init() {

    for (Class<?> zenClass : CLASS_LIST) {
      CraftTweakerAPI.registerClass(zenClass);
    }
  }

  public static void apply() {

    LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
    LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
  }
}
