package com.codetaylor.mc.athenaeum.network.tile.spi;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface ITileDataContainer {

  /**
   * Called on the client when a TE receives a data update packet. The given
   * list contains only the data that were updated.
   *
   * @param data the list of data updated
   */
  @SideOnly(Side.CLIENT)
  void onTileDataUpdate(List<ITileData> data);
}
