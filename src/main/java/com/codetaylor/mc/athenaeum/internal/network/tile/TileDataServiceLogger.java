package com.codetaylor.mc.athenaeum.internal.network.tile;

import com.codetaylor.mc.athenaeum.AthenaeumMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class TileDataServiceLogger {

  public static final Logger LOGGER = LogManager.getLogger(AthenaeumMod.ID + "." + TileDataService.class.getSimpleName());

  private TileDataServiceLogger() {
    //
  }
}
