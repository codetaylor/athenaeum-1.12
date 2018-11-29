package com.codetaylor.mc.athenaeum.network.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface ITileDataContainer {

  @SideOnly(Side.CLIENT)
  void onTileDataUpdate(List<ITileData> data);
}
