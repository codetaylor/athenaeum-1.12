package com.codetaylor.mc.athenaeum.util;

import net.minecraft.util.math.AxisAlignedBB;

public final class AABBHelper {

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
