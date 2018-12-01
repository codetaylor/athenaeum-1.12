package com.codetaylor.mc.athenaeum.network.tile;

import com.codetaylor.mc.athenaeum.network.tile.spi.ITileData;
import com.codetaylor.mc.athenaeum.network.tile.spi.TileDataContainerBase;

import javax.annotation.Nullable;

public interface ITileDataService {

  int getServiceId();

  @Nullable
  TileDataTracker getTracker(TileDataContainerBase tile);

  void register(TileDataContainerBase tile, ITileData[] data);

  void update();
}
