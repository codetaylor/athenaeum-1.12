package com.codetaylor.mc.athenaeum;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModAthenaeum.MOD_ID)
public class ConfigChangedEventHandler {

  @SubscribeEvent
  public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {

    if (event.getModID().equals(ModAthenaeum.MOD_ID)) {
      ConfigManager.sync(ModAthenaeum.MOD_ID, Config.Type.INSTANCE);
    }
  }
}
