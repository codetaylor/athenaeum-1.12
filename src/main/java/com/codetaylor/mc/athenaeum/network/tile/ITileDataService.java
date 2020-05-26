package com.codetaylor.mc.athenaeum.network.tile;

import com.codetaylor.mc.athenaeum.network.tile.spi.ITileData;
import com.codetaylor.mc.athenaeum.network.tile.spi.TileEntityDataContainerBase;

import javax.annotation.Nullable;

public interface ITileDataService {

  int getServiceId();

  @Nullable
  TileDataTracker getTracker(TileEntityDataContainerBase tile);

  void register(TileEntityDataContainerBase tile, ITileData[] data);

  void update();
}
