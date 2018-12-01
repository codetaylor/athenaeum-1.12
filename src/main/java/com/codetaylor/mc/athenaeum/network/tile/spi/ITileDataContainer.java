package com.codetaylor.mc.athenaeum.network.tile.spi;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITileDataContainer {

  /**
   * Called on the client when a TE receives a data update packet.
   * <p>
   * You can check the dirty flag on the TE's data objects during this call.
   * All updated data will be flagged dirty for the duration of this call.
   */
  @SideOnly(Side.CLIENT)
  void onTileDataUpdate();
}
