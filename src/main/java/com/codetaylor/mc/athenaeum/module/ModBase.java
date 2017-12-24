package com.codetaylor.mc.athenaeum.module;

import com.codetaylor.mc.athenaeum.integration.IntegrationPluginHandlerRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ModBase {

  private final String modId;
  private List<ModuleBase> moduleList;
  private ModuleRegistry moduleRegistry;
  private ModuleEventRouter moduleEventRouter;
  private IntegrationPluginHandlerRegistry integrationPluginHandlerRegistry;

  public ModBase(String modId) {

    this.modId = modId;
    this.moduleList = new ArrayList<>();
    this.moduleRegistry = new ModuleRegistry(this.moduleList, new ModuleConstructor());
    this.moduleEventRouter = new ModuleEventRouter(this.moduleList);
    this.integrationPluginHandlerRegistry = new IntegrationPluginHandlerRegistry(modId, this.moduleRegistry);

    MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);

    this.registerIntegrationHandler(
        "jei",
        "com.codetaylor.mc.athenaeum.integration.jei.IntegrationPluginHandlerJEI"
    );

    this.registerIntegrationHandler(
        "crafttweaker",
        "com.codetaylor.mc.athenaeum.lib.integration.crafttweaker.IntegrationPluginHandlerCraftTweaker"
    );

  }

  public String getModId() {

    return this.modId;
  }

  @SafeVarargs
  protected final void registerModules(Class<? extends ModuleBase>... moduleClassArray) {

    this.moduleRegistry.registerModules(moduleClassArray);
  }

  protected void registerIntegrationHandler(String modId, String handler) {

    this.integrationPluginHandlerRegistry.registerIntegrationHandler(modId, handler);
  }

  public abstract void onConstructionEvent(FMLConstructionEvent event);

  protected void _onConstructionEvent(FMLConstructionEvent event) {

    // Initialize integration handlers.
    this.integrationPluginHandlerRegistry.initializeIntegrationHandlers();

    // Initialize modules.
    this.moduleRegistry.initializeModules(this.getModId());

    // Register integration plugins using loaded handlers.
    for (ModuleBase module : this.moduleList) {
      this.integrationPluginHandlerRegistry.registerIntegrationPlugins(module.getIntegrationPluginMap());
    }

    // Fire all the modules' construction events.
    this.moduleEventRouter.onConstructionEvent(event);
  }

  public abstract void onPreInitializationEvent(FMLPreInitializationEvent event);

  protected void _onPreInitializationEvent(FMLPreInitializationEvent event) {

    this.moduleEventRouter.onPreInitializationEvent(event);
  }

  public abstract void onInitializationEvent(FMLInitializationEvent event);

  protected void _onInitializationEvent(FMLInitializationEvent event) {

    this.moduleEventRouter.onInitializationEvent(event);
  }

  public abstract void onPostInitializationEvent(FMLPostInitializationEvent event);

  protected void _onPostInitializationEvent(FMLPostInitializationEvent event) {

    this.moduleEventRouter.onPostInitializationEvent(event);
  }

  public abstract void onLoadCompleteEvent(FMLLoadCompleteEvent event);

  protected void _onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    this.moduleEventRouter.onLoadCompleteEvent(event);
  }

  public abstract void onServerAboutToStartEvent(FMLServerAboutToStartEvent event);

  protected void _onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {

    this.moduleEventRouter.onServerAboutToStartEvent(event);
  }

  public abstract void onServerStartingEvent(FMLServerStartingEvent event);

  protected void _onServerStartingEvent(FMLServerStartingEvent event) {

    this.moduleEventRouter.onServerStartingEvent(event);
  }

  public abstract void onServerStartedEvent(FMLServerStartedEvent event);

  protected void _onServerStartedEvent(FMLServerStartedEvent event) {

    this.moduleEventRouter.onServerStartedEvent(event);
  }

  public abstract void onServerStoppingEvent(FMLServerStoppingEvent event);

  protected void _onServerStoppingEvent(FMLServerStoppingEvent event) {

    this.moduleEventRouter.onServerStoppingEvent(event);
  }

  public abstract void onServerStoppedEvent(FMLServerStoppedEvent event);

  protected void _onServerStoppedEvent(FMLServerStoppedEvent event) {

    this.moduleEventRouter.onServerStoppedEvent(event);
  }
}
