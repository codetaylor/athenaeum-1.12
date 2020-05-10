package com.codetaylor.mc.athenaeum.util;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class TileHelper {

  /**
   * Marks the given tile entity dirty and calls {@link World#notifyBlockUpdate(BlockPos, BlockState, BlockState, int)}.
   * <p>
   * Only works when called on the server.
   *
   * @param tileEntity the tile entity to update
   */
  public void notifyBlockUpdate(TileEntity tileEntity) {

    tileEntity.markDirty();
    World world = tileEntity.getWorld();

    if (world != null && !world.isRemote) {
      BlockState blockState = world.getBlockState(tileEntity.getPos());
      world.notifyBlockUpdate(tileEntity.getPos(), blockState, blockState, 3);
    }
  }

  private TileHelper() {
    //
  }
}
