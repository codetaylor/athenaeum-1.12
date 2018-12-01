package com.codetaylor.mc.athenaeum.spi;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class TileEntityBase
    extends TileEntity {

  public void notifyBlockUpdate() {

    this.markDirty();

    if (this.world != null && !this.world.isRemote) {
      IBlockState blockState = this.world.getBlockState(this.getPos());
      this.world.notifyBlockUpdate(this.getPos(), blockState, blockState, 3);
    }
  }

  @Override
  public boolean shouldRefresh(
      World world,
      BlockPos pos,
      @Nonnull IBlockState oldState,
      @Nonnull IBlockState newState
  ) {

    if (oldState.getBlock() == newState.getBlock()) {
      return false;
    }

    return super.shouldRefresh(world, pos, oldState, newState);
  }

}
