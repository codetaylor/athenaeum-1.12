package com.codetaylor.mc.athenaeum;

import net.minecraftforge.common.config.Config;

@Config(modid = ModAthenaeum.MOD_ID, name = ModAthenaeum.MOD_ID + "/" + ModAthenaeum.MOD_ID)
public class ModAthenaeumConfig {

  public static TileDataService TILE_DATA_SERVICE = new TileDataService();

  public static class TileDataService {

    @Config.Comment({
        "Set to true to enable client-side display of Athenaeum's tile data service.",
        "Used for monitoring TE updates from the server for any TE registered with",
        "the data service."
    })
    public boolean ENABLED = false;

    @Config.Comment({
        "Change the update rate for the display."
    })
    public short UPDATE_INTERVAL_TICKS = 20;

    @Config.Comment({
        "Set the value that will be tracked (the blue value) in the display. "
    })
    public int TRACKING_INDEX = 10;

  }

}
