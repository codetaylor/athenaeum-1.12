package com.codetaylor.mc.athenaeum.module;

import com.codetaylor.mc.athenaeum.AthenaeumMod;

import javax.annotation.Nullable;

/* package */ class ModuleConstructor {

  @Nullable
    /* package */ ModuleBase constructModule(String modId, Class<? extends ModuleBase> moduleClass) {

    try {
      ModuleBase module = moduleClass.newInstance();
      AthenaeumMod.LOGGER.info("[" + modId + "] Loaded module: " + moduleClass.getName());
      return module;

    } catch (Exception e) {
      AthenaeumMod.LOGGER.error("[" + modId + "] Error loading module: " + moduleClass.getName(), e);
    }

    return null;
  }
}
