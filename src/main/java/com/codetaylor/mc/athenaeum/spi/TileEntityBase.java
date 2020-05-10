package com.codetaylor.mc.athenaeum.spi;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityBase
    extends TileEntity {

  public TileEntityBase(TileEntityType<?> tileEntityType) {

    super(tileEntityType);
  }

  public void notifyBlockUpdate() {

    this.markDirty();

    if (this.world != null && !this.world.isRemote) {
      BlockState blockState = this.world.getBlockState(this.getPos());
      this.world.notifyBlockUpdate(this.getPos(), blockState, blockState, 3);
    }
  }

}
