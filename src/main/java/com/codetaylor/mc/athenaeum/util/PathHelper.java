package com.codetaylor.mc.athenaeum.util;

import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class PathHelper {

  public Path getModConfigPath(String modId) {

    Path gamePath = FMLPaths.GAMEDIR.get();
    Path configPath = gamePath.resolve(FMLConfig.defaultConfigPath());
    return configPath.resolve(modId);
  }

  private PathHelper() {
    //
  }
}
