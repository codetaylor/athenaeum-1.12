package com.codetaylor.mc.athenaeum;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod(AthenaeumMod.ID)
public class AthenaeumMod {

  public static final String ID = "athenaeum";

  public static final Logger LOGGER = LogManager.getLogger();

  public AthenaeumMod() {

    ModLoadingContext modLoadingContext = ModLoadingContext.get();
    modLoadingContext.registerConfig(ModConfig.Type.CLIENT, AthenaeumConfig.CLIENT_CONFIG);

    Path configPath = FMLPaths.CONFIGDIR.get().resolve(AthenaeumMod.ID);

    try {
      Files.createDirectories(configPath);

    } catch (IOException ignore) {
      //
    }

    AthenaeumConfig.load(AthenaeumConfig.CLIENT_CONFIG, configPath.resolve("athenaeum.client.toml"));
  }
}
