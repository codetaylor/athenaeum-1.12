package com.codetaylor.mc.athenaeum.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockHelper {

  public static void notifyBlockUpdate(World world, BlockPos pos) {

    IBlockState blockState = world.getBlockState(pos);
    world.notifyBlockUpdate(pos, blockState, blockState, 3);
  }

  public static boolean isBlockSurroundedByAirHorizontal(World world, BlockPos pos) {

    return world.isAirBlock(pos.offset(EnumFacing.NORTH))
        && world.isAirBlock(pos.offset(EnumFacing.SOUTH))
        && world.isAirBlock(pos.offset(EnumFacing.EAST))
        && world.isAirBlock(pos.offset(EnumFacing.WEST));
  }

  public interface IBlockAction {

    /**
     * Return false to stop processing, true to keep processing.
     *
     * @param w
     * @param p
     * @param bs
     * @return
     */
    boolean execute(World w, BlockPos p, IBlockState bs);
  }

  public static void forBlocksInRange(World world, BlockPos pos, int range, IBlockAction action) {

    int rangeSq = range * range;
    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

    complete:
    for (int x = pos.getX() - range; x <= pos.getX() + range; x++) {
      for (int y = pos.getY() - range; y <= pos.getY() + range; y++) {
        for (int z = pos.getZ() - range; z <= pos.getZ() + range; z++) {
          double distanceSq = pos.distanceSq(x, y, z);

          if (distanceSq <= rangeSq) {
            mutableBlockPos.setPos(x, y, z);
            IBlockState blockState = world.getBlockState(mutableBlockPos);

            if (!action.execute(world, mutableBlockPos, blockState)) {
              break complete;
            }
          }
        }
      }
    }
  }

  public static void forBlocksInRangeShuffled(World world, BlockPos pos, int range, IBlockAction action) {

    ArrayList<BlockPos> blockList = new ArrayList<>();
    BlockHelper.findBlocksInCube(world, pos, range, range, range, IBlockFilter.TRUE, blockList);
    Collections.shuffle(blockList);
    int rangeSq = range * range;
    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

    for (BlockPos blockPos : blockList) {
      double distanceSq = pos.distanceSq(blockPos.getX(), blockPos.getY(), blockPos.getZ());

      if (distanceSq <= rangeSq) {
        mutableBlockPos.setPos(blockPos);
        IBlockState blockState = world.getBlockState(mutableBlockPos);

        if (!action.execute(world, mutableBlockPos, blockState)) {
          break;
        }
      }
    }
  }

  public static void forBlocksInCube(World world, BlockPos pos, int rangeX, int rangeY, int rangeZ, IBlockAction action) {

    complete:
    for (int x = pos.getX() - rangeX; x <= pos.getX() + rangeX; x++) {
      for (int y = pos.getY() - rangeY; y <= pos.getY() + rangeY; y++) {
        for (int z = pos.getZ() - rangeZ; z <= pos.getZ() + rangeZ; z++) {

          BlockPos candidatePos = new BlockPos(x, y, z);
          IBlockState blockState = world.getBlockState(candidatePos);

          if (!action.execute(world, candidatePos, blockState)) {
            break complete;
          }
        }
      }
    }
  }

  public static void forBlocksInCubeShuffled(World world, BlockPos pos, int rangeX, int rangeY, int rangeZ, IBlockAction action) {

    ArrayList<BlockPos> blockList = new ArrayList<>();
    BlockHelper.findBlocksInCube(world, pos, rangeX, rangeY, rangeZ, IBlockFilter.TRUE, blockList);
    Collections.shuffle(blockList);

    for (BlockPos blockPos : blockList) {
      IBlockState blockState = world.getBlockState(blockPos);

      if (!action.execute(world, blockPos, blockState)) {
        break;
      }
    }
  }

  public interface IBlockFilter {

    IBlockFilter TRUE = (world, pos, blockState) -> true;

    boolean allow(World world, BlockPos pos, IBlockState blockState);
  }

  public static List<BlockPos> findBlocksInCube(World world, BlockPos pos, int rangeX, int rangeY, int rangeZ, IBlockFilter filter, List<BlockPos> result) {

    for (int x = pos.getX() - rangeX; x <= pos.getX() + rangeX; x++) {
      for (int y = pos.getY() - rangeY; y <= pos.getY() + rangeY; y++) {
        for (int z = pos.getZ() - rangeZ; z <= pos.getZ() + rangeZ; z++) {

          BlockPos candidatePos = new BlockPos(x, y, z);
          IBlockState blockState = world.getBlockState(candidatePos);

          if (filter.allow(world, candidatePos, blockState)) {
            result.add(candidatePos);
          }
        }
      }
    }

    return result;
  }

  public static List<BlockPos> findBlocksInRange(World world, BlockPos pos, int range, IBlockFilter filter, List<BlockPos> result) {

    int rangeSq = range * range;

    for (int x = pos.getX() - range; x <= pos.getX() + range; x++) {
      for (int y = pos.getY() - range; y <= pos.getY() + range; y++) {
        for (int z = pos.getZ() - range; z <= pos.getZ() + range; z++) {
          double distanceSq = pos.distanceSq(x, y, z);

          if (distanceSq <= rangeSq) {
            BlockPos candidatePos = new BlockPos(x, y, z);
            IBlockState blockState = world.getBlockState(candidatePos);

            if (filter.allow(world, candidatePos, blockState)) {
              result.add(candidatePos);
            }
          }
        }
      }
    }

    return result;
  }

  private BlockHelper() {
    //
  }

}
