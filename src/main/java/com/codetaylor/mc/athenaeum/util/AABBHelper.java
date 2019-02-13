package com.codetaylor.mc.athenaeum.util;

import net.minecraft.util.math.AxisAlignedBB;

public final class AABBHelper {

  private static final double SIN_90 = Math.sin(Math.PI / 2.0);
  private static final double COS_90 = Math.cos(Math.PI / 2.0);

  public static AxisAlignedBB rotateHorizontalCCW90Centered(AxisAlignedBB axisAlignedBB) {

    return new AxisAlignedBB(
        0.5 + (axisAlignedBB.minX - 0.5) * COS_90 - (axisAlignedBB.minZ - 0.5) * SIN_90,
        axisAlignedBB.minY,
        0.5 + (axisAlignedBB.minX - 0.5) * SIN_90 + (axisAlignedBB.minZ - 0.5) * COS_90,
        0.5 + (axisAlignedBB.maxX - 0.5) * COS_90 - (axisAlignedBB.maxZ - 0.5) * SIN_90,
        axisAlignedBB.maxY,
        0.5 + (axisAlignedBB.maxX - 0.5) * SIN_90 + (axisAlignedBB.maxZ - 0.5) * COS_90
    );
  }

  public static AxisAlignedBB create(double x0, double y0, double z0, double x1, double y1, double z1) {

    return new AxisAlignedBB(x0 / 16.0, y0 / 16.0, z0 / 16.0, x1 / 16.0, y1 / 16.0, z1 / 16.0);
  }

  public static boolean contains(AxisAlignedBB bounds, double x, double y, double z) {

    if (x > bounds.minX && x < bounds.maxX) {

      if (y > bounds.minY && y < bounds.maxY) {
        return z > bounds.minZ && z < bounds.maxZ;

      } else {
        return false;
      }

    } else {
      return false;
    }
  }

  private AABBHelper() {
    //
  }

}
