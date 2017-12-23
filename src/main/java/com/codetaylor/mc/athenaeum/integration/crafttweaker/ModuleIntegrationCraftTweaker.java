package com.codetaylor.mc.athenaeum.integration.crafttweaker;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModuleIntegrationCraftTweaker
    extends ModuleBase {

  public ModuleIntegrationCraftTweaker() {

    super(Integer.MAX_VALUE);
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    PluginDelegateCraftTweaker.init();
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    PluginDelegateCraftTweaker.apply();
  }
}
