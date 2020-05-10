package com.codetaylor.mc.athenaeum.module;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ModuleBase
    implements Comparable<ModuleBase> {

  private final String name;
  private final int priority;
  private final Map<String, Set<String>> integrationPluginMap;

  protected ModuleBase(int priority) {

    this.priority = priority;
    this.name = this.getClass().getSimpleName();
    this.integrationPluginMap = new HashMap<>();
  }

  // --------------------------------------------------------------------------

  public String getName() {

    return this.name;
  }

  public int getPriority() {

    return this.priority;
  }

  // --------------------------------------------------------------------------
  // - Integration
  // --------------------------------------------------------------------------

  protected void registerIntegrationPlugin(String modId, String plugin) {

    Set<String> list = this.integrationPluginMap.computeIfAbsent(modId, k -> new HashSet<>());
    list.add(plugin);
  }

  /* package */ Map<String, Set<String>> getIntegrationPluginMap() {

    return this.integrationPluginMap;
  }

  // --------------------------------------------------------------------------
  // - Comparator
  // --------------------------------------------------------------------------

  public int compareTo(@Nonnull ModuleBase otherModule) {

    return Integer.compare(otherModule.getPriority(), this.priority);
  }

}
