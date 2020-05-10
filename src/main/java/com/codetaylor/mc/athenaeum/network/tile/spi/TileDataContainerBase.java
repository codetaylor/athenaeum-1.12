package com.codetaylor.mc.athenaeum.network.tile.spi;

import com.codetaylor.mc.athenaeum.spi.TileEntityBase;
import net.minecraft.tileentity.TileEntityType;

/**
 * Tile entities using the tile data network service should extend this.
 */
public abstract class TileDataContainerBase
    extends TileEntityBase
    implements ITileDataContainer {

  public TileDataContainerBase(TileEntityType<?> tileEntityType) {

    super(tileEntityType);
  }
}
