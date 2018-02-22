package com.codetaylor.mc.athenaeum.integration.crafttweaker;

import com.codetaylor.mc.athenaeum.ModAthenaeum;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModuleIntegration
    extends ModuleBase {

  public ModuleIntegration() {

    super(Integer.MAX_VALUE, ModAthenaeum.MOD_ID);
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    PluginDelegate.init();
  }

  @Override
  public void onPostInitializationEvent(FMLPostInitializationEvent event) {

    PluginDelegate.apply();
  }
}
