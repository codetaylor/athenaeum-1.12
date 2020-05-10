package com.codetaylor.mc.athenaeum.network.spi.tiledata.service;

import com.codetaylor.mc.athenaeum.network.spi.tiledata.ITileData;
import com.codetaylor.mc.athenaeum.network.spi.tiledata.TileDataContainerBase;
import com.codetaylor.mc.athenaeum.network.tile.TileDataTracker;

import javax.annotation.Nullable;

public interface ITileDataService {

  int getServiceId();

  @Nullable
  TileDataTracker getTracker(TileDataContainerBase tile);

  void register(TileDataContainerBase tile, ITileData[] data);

  void update();
}
