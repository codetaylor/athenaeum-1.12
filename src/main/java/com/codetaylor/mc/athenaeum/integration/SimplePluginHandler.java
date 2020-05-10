package com.codetaylor.mc.athenaeum.integration;

/**
 * The simple plugin handler just instantiates the plugin.
 * <p>
 * This is used to instantiate plugins that register themselves on
 * the event bus in their constructor.
 * <p>
 * This is currently used by the GameStages and Dropt plugin.
 */
public class SimplePluginHandler
    implements IIntegrationPluginHandler {

  @Override
  public void execute(String plugin) throws Exception {

    Class.forName(plugin).newInstance();
  }
}
