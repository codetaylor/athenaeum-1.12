package com.codetaylor.mc.athenaeum.network.tile;

import com.codetaylor.mc.athenaeum.ModAthenaeum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class TileDataServiceLogger {

  public static final Logger LOGGER = LogManager.getLogger(ModAthenaeum.MOD_ID + "." + TileDataService.class.getSimpleName());

  private TileDataServiceLogger() {
    //
  }
}
