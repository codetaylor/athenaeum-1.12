package com.codetaylor.mc.athenaeum.integration.crafttweaker;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModuleIntegration
    extends ModuleBase {

  public ModuleIntegration() {

    super(Integer.MAX_VALUE);
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    PluginDelegate.init();
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    PluginDelegate.apply();
  }
}