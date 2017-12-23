package com.codetaylor.mc.athenaeum.integration.jei;

import com.codetaylor.mc.athenaeum.integration.IIntegrationPluginHandler;
import mezz.jei.api.IModPlugin;

public class IntegrationPluginHandlerJEI
    implements IIntegrationPluginHandler {

  @Override
  public void execute(String pluginClass) throws Exception {

    IModPlugin plugin = (IModPlugin) Class.forName(pluginClass).newInstance();
    PluginDelegateJEI.registerPlugin(plugin);
  }
}
