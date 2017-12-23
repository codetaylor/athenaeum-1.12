package com.codetaylor.mc.athenaeum.integration;

import com.codetaylor.mc.athenaeum.module.ModuleBase;

import javax.annotation.Nullable;

public interface IIntegrationPluginHandler {

  void execute(String plugin) throws Exception;

  default boolean hasModule() {

    return false;
  }

  @Nullable
  default Class<? extends ModuleBase> getModuleClass() {

    return null;
  }

}
