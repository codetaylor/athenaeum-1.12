package com.codetaylor.mc.athenaeum.spi;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBase
    extends TileEntity {

  public void notifyBlockUpdate() {

    this.markDirty();

    if (this.world != null && !this.world.isRemote) {
      IBlockState blockState = this.world.getBlockState(this.getPos());
      this.world.notifyBlockUpdate(this.getPos(), blockState, blockState, 3);
    }
  }

}
