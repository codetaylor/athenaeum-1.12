package com.codetaylor.mc.athenaeum;

import net.minecraftforge.fml.common.Mod;

@Mod(
    modid = ModAthenaeum.MOD_ID,
    version = ModAthenaeum.VERSION,
    name = ModAthenaeum.NAME,
    dependencies = ModAthenaeum.DEPENDENCIES
)
public class ModAthenaeum {

  public static final String MOD_ID = "athenaeum";
  public static final String VERSION = "@@VERSION@@";
  public static final String NAME = "Athenaeum";
  public static final String DEPENDENCIES = "after:crafttweaker;after:jei;";

}
