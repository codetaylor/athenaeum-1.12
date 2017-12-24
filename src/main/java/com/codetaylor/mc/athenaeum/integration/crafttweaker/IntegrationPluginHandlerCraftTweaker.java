package com.codetaylor.mc.athenaeum.integration.crafttweaker;

import com.codetaylor.mc.athenaeum.integration.IIntegrationPluginHandler;
import com.codetaylor.mc.athenaeum.module.ModuleBase;

import javax.annotation.Nullable;

public class IntegrationPluginHandlerCraftTweaker
    implements IIntegrationPluginHandler {

  @Override
  public void execute(String plugin) throws Exception {

    PluginDelegateCraftTweaker.registerZenClass(Class.forName(plugin));
  }

  @Nullable
  @Override
  public Class<? extends ModuleBase> getModuleClass() {

    return ModuleIntegrationCraftTweaker.class;
  }
}
