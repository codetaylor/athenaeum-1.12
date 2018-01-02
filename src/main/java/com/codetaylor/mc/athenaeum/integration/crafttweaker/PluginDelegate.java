package com.codetaylor.mc.athenaeum.integration.crafttweaker;

import crafttweaker.IAction;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * 2018.1.2:
 * This class has been refactored to provide a delegate for each mod that requires one.
 * This ensures that when each mod calls the init and apply methods, only the delegate
 * for the calling mod will be executed. This prevents a problem with the previous
 * implementation which caused each mod to execute this delegate, causing recipes and
 * zen classes to be registered multiple times.
 *
 * @author codetaylor
 */
public class PluginDelegate {

  private static final Map<String, PluginModDelegate> DELEGATE_MAP;

  static {
    DELEGATE_MAP = new HashMap<>();
  }

  /* package */ static void registerZenClass(Class<?> zenClass) {

    PluginDelegate.getPluginModDelegate(PluginDelegate.getModId()).registerZenClass(zenClass);
  }

  public static void addAddition(String modId, IAction action) {

    // The mod id has to be supplied externally here because when this is called,
    // the active mod container belongs to "crafttweaker".

    PluginDelegate.getPluginModDelegate(modId).addAddition(action);
  }

  public static void addRemoval(String modId, IAction action) {

    // The mod id has to be supplied externally here because when this is called,
    // the active mod container belongs to "crafttweaker".

    PluginDelegate.getPluginModDelegate(modId).addRemoval(action);
  }

  /* package */ static void init() {

    PluginModDelegate delegate = DELEGATE_MAP.get(PluginDelegate.getModId());

    if (delegate != null) {
      delegate.init();
    }
  }

  /* package */ static void apply() {

    PluginModDelegate delegate = DELEGATE_MAP.get(PluginDelegate.getModId());

    if (delegate != null) {
      delegate.apply();
    }
  }

  private static PluginModDelegate getPluginModDelegate(String modId) {

    return DELEGATE_MAP.computeIfAbsent(modId, s -> new PluginModDelegate());
  }

  private static String getModId() {

    ModContainer modContainer = Loader.instance().activeModContainer();

    if (modContainer == null) {
      throw new RuntimeException("Active mod container is null");
    }

    return modContainer.getModId();
  }

}
