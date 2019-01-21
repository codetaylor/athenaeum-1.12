package com.codetaylor.mc.athenaeum.util;

import net.minecraft.util.math.AxisAlignedBB;

public final class AABBHelper {

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
