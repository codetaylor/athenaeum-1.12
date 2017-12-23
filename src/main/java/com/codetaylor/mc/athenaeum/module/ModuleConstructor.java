package com.codetaylor.mc.athenaeum.module;

import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.Nullable;

/* package */ class ModuleConstructor {

  @Nullable
  /* package */ ModuleBase constructModule(String modId, Class<? extends ModuleBase> moduleClass) {

    try {
      ModuleBase module = moduleClass.newInstance();
      FMLLog.log.info("[" + modId + "] Loaded module: " + moduleClass.getName());
      return module;

    } catch (Exception e) {
      FMLLog.log.error("[" + modId + "] Error loading module: " + moduleClass.getName(), e);
    }

    return null;
  }

}
