package com.codetaylor.mc.athenaeum.integration;

import com.codetaylor.mc.athenaeum.module.ModuleBase;

import javax.annotation.Nullable;

public interface IIntegrationPluginHandler {

  void execute(String plugin) throws Exception;

  /**
   * Override and return a module class here if this integration handler requires a module
   * to be registered. Providing a module can allow an integration handler to hook into
   * FML lifecycle events.
   *
   * @return module class or null
   */
  @Nullable
  default Class<? extends ModuleBase> getModuleClass() {

    return null;
  }

}
