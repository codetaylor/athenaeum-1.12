package com.codetaylor.mc.athenaeum.integration.crafttweaker;

import com.codetaylor.mc.athenaeum.AthenaeumMod;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

public class ModuleIntegration
    extends ModuleBase {

  public ModuleIntegration() {

    super(Integer.MAX_VALUE, AthenaeumMod.ID);

    // We're going to try initializing in the constructor here to give the
    // plugin the opportunity to inject its zen classes into the CrT registry.
    PluginDelegate.init();
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    PluginDelegate.apply();
  }
}
