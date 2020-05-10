package com.codetaylor.mc.athenaeum.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.actions.IAction;
import com.codetaylor.mc.athenaeum.AthenaeumMod;
import com.codetaylor.mc.athenaeum.util.ReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* package */ class PluginModDelegate {

  private static final MethodHandle craftTweakerRegistry$ZEN_CLASSES_Getter;

  static {
    craftTweakerRegistry$ZEN_CLASSES_Getter = ReflectionHelper.findStaticGetter(
        CraftTweakerRegistry.class, "ZEN_CLASSES", List.class, AthenaeumMod.LOGGER);
  }

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

    try {

      //noinspection unchecked
      List<Class> zenClasses = (List<Class>) craftTweakerRegistry$ZEN_CLASSES_Getter.invokeExact();
      zenClasses.addAll(this.zenClassList);

    } catch (Throwable t) {
      AthenaeumMod.LOGGER.error(String.format("Error accessing getter for static field: %s", "ZEN_CLASSES"), t);
    }
  }

  /* package */ void apply() {

    this.removalList.forEach(CraftTweakerAPI::apply);
    this.additionList.forEach(CraftTweakerAPI::apply);
  }
}
