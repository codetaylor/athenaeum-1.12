package com.codetaylor.mc.athenaeum.network.spi.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * Tile entities using the tile data network service should extend this.
 */
public abstract class TileDataContainerBase
    extends TileEntity
    implements ITileDataContainer {

  public TileDataContainerBase(TileEntityType<?> tileEntityType) {

    super(tileEntityType);
  }
}
