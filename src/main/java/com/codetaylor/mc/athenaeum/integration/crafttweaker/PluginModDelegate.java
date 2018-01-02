package com.codetaylor.mc.athenaeum.integration.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* package */ class PluginModDelegate {

  private final List<IAction> removalList = new LinkedList<>();
  private final List<IAction> additionList = new LinkedList<>();
  private final List<Class<?>> zenClassList = new ArrayList<>();

  /* package */ void registerZenClass(Class<?> zenClass) {

    this.zenClassList.add(zenClass);
  }

  /* package */ void addAddition(IAction action) {

    this.additionList.add(action);
  }

  /* package */ void addRemoval(IAction action) {

    this.removalList.add(action);
  }

  /* package */ void init() {

    for (Class<?> zenClass : this.zenClassList) {
      CraftTweakerAPI.registerClass(zenClass);
    }
  }

  /* package */ void apply() {

    this.removalList.forEach(CraftTweakerAPI::apply);
    this.additionList.forEach(CraftTweakerAPI::apply);
  }
}
