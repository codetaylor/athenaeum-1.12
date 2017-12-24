package com.codetaylor.mc.athenaeum.integration;

import com.codetaylor.mc.athenaeum.module.ModuleRegistry;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IntegrationPluginHandlerRegistry {

  private final String modId;
  private ModuleRegistry moduleRegistry;
  private Map<String, String> integrationHandlerPluginMap;
  private Map<String, IIntegrationPluginHandler> integrationPluginHandlerRegistryMap;

  public IntegrationPluginHandlerRegistry(
      String modId,
      ModuleRegistry moduleRegistry
  ) {

    this.modId = modId;
    this.moduleRegistry = moduleRegistry;
    this.integrationHandlerPluginMap = new HashMap<>();
    this.integrationPluginHandlerRegistryMap = new HashMap<>();
  }

  public void registerIntegrationHandler(String modId, String handler) {

    this.integrationHandlerPluginMap.put(modId, handler);
  }

  public void initializeIntegrationHandlers() {

    for (Map.Entry<String, String> entry : this.integrationHandlerPluginMap.entrySet()) {

      if (Loader.isModLoaded(entry.getKey())) {

        try {
          IIntegrationPluginHandler instance = (IIntegrationPluginHandler) Class.forName(entry.getValue())
              .newInstance();

          this.integrationPluginHandlerRegistryMap.put(
              entry.getKey(),
              instance
          );

          if (instance.getModuleClass() != null) {
            this.moduleRegistry.registerModules(instance.getModuleClass());
          }

          FMLLog.log.info("[" + this.modId + "] Loaded integration plugin handler: " + entry.getValue());

        } catch (Exception e) {
          FMLLog.log.error("[" + this.modId + "] Error loading integration plugin handler: " + entry.getValue(), e);
        }
      }
    }
  }

  public void registerIntegrationPlugins(Map<String, Set<String>> plugins) {

    for (Map.Entry<String, Set<String>> entry : plugins.entrySet()) {
      IIntegrationPluginHandler handler = this.integrationPluginHandlerRegistryMap.get(entry.getKey());

      if (handler != null) { // handler is null if required mod isn't loaded

        for (String plugin : entry.getValue()) {

          try {
            handler.execute(plugin);
            FMLLog.log.info("[" + this.modId + "] Executed integration plugin handler [" + handler.getClass()
                .getName() + "] for plugin [" + plugin + "]");

          } catch (Exception e) {
            FMLLog.log.error("[" + this.modId + "] Error executing integration plugin handler [" + handler.getClass()
                .getName() + "] for plugin [" + plugin + "]", e);
          }
        }
      }
    }
  }
}
