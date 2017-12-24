package com.codetaylor.mc.athenaeum.integration.jei;

import com.codetaylor.mc.athenaeum.integration.IIntegrationPluginHandler;
import mezz.jei.api.IModPlugin;

public class IntegrationPluginHandler
    implements IIntegrationPluginHandler {

  @Override
  public void execute(String pluginClass) throws Exception {

    IModPlugin plugin = (IModPlugin) Class.forName(pluginClass).newInstance();
    PluginDelegate.registerPlugin(plugin);
  }
}
