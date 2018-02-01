package com.codetaylor.mc.athenaeum.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHelper {

  public static void notifyBlockUpdate(World world, BlockPos pos) {

    IBlockState blockState = world.getBlockState(pos);
    world.notifyBlockUpdate(pos, blockState, blockState, 3);
  }

  private BlockHelper() {
    //
  }

}
