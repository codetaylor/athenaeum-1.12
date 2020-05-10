package com.codetaylor.mc.athenaeum.network.spi.tile.data.service;

import com.codetaylor.mc.athenaeum.network.spi.tile.ITileData;
import com.codetaylor.mc.athenaeum.network.spi.tile.TileDataContainerBase;
import com.codetaylor.mc.athenaeum.internal.network.tile.TileDataTracker;

import javax.annotation.Nullable;

public interface ITileDataService {

  int getServiceId();

  @Nullable
  TileDataTracker getTracker(TileDataContainerBase tile);

  void register(TileDataContainerBase tile, ITileData[] data);

  void update();
}
