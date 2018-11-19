package com.codetaylor.mc.athenaeum.module;

import com.codetaylor.mc.athenaeum.integration.IntegrationPluginHandlerRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLStateEvent;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

  private final String modId;
  private List<ModuleBase> moduleList;
  private ModuleRegistry moduleRegistry;
  private ModuleEventRouter moduleEventRouter;
  private IntegrationPluginHandlerRegistry integrationPluginHandlerRegistry;

  public ModuleManager(String modId) {

    this.modId = modId;
    this.moduleList = new ArrayList<>();
    this.moduleRegistry = new ModuleRegistry(this.moduleList, new ModuleConstructor());
    this.moduleEventRouter = new ModuleEventRouter(this.moduleList);
    this.integrationPluginHandlerRegistry = new IntegrationPluginHandlerRegistry(modId, this.moduleRegistry);

    MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);

    this.registerIntegrationHandler(
        "jei",
        "com.codetaylor.mc.athenaeum.integration.jei.IntegrationPluginHandler"
    );

    this.registerIntegrationHandler(
        "crafttweaker",
        "com.codetaylor.mc.athenaeum.integration.crafttweaker.IntegrationPluginHandler"
    );

    this.registerIntegrationHandler(
        "gamestages",
        "com.codetaylor.mc.athenaeum.integration.SimplePluginHandler"
    );

    this.registerIntegrationHandler(
        "dropt",
        "com.codetaylor.mc.athenaeum.integration.SimplePluginHandler"
    );
  }

  @SafeVarargs
  public final void registerModules(Class<? extends ModuleBase>... moduleClassArray) {

    this.moduleRegistry.registerModules(moduleClassArray);
  }

  public void registerIntegrationHandler(String modId, String handler) {

    this.integrationPluginHandlerRegistry.registerIntegrationHandler(modId, handler);
  }

  public void onConstructionEvent() {

    // Initialize integration handlers.
    this.integrationPluginHandlerRegistry.initializeIntegrationHandlers();

    // Initialize modules.
    this.moduleRegistry.initializeModules(this.modId);

    // Register integration plugins using loaded handlers.
    for (ModuleBase module : this.moduleList) {
      this.integrationPluginHandlerRegistry.registerIntegrationPlugins(module.getIntegrationPluginMap());
    }
  }

  public void routeFMLStateEvent(FMLStateEvent event) {

    this.moduleEventRouter.routeFMLStateEvent(event);
  }
}
