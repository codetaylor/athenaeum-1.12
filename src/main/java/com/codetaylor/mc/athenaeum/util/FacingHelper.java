package com.codetaylor.mc.athenaeum.util;

import net.minecraft.util.EnumFacing;

public final class FacingHelper {

  /**
   * Translate and return the external facing based on the internal facing.
   *
   * @param internalFacing
   * @param externalFacing
   * @return
   */
  public static EnumFacing translateFacing(EnumFacing internalFacing, EnumFacing externalFacing) {

    if (internalFacing == EnumFacing.EAST) {

      if (externalFacing == EnumFacing.NORTH) {
        return EnumFacing.WEST;

      } else if (externalFacing == EnumFacing.EAST) {
        return EnumFacing.NORTH;

      } else if (externalFacing == EnumFacing.SOUTH) {
        return EnumFacing.EAST;

      } else if (externalFacing == EnumFacing.WEST) {
        return EnumFacing.SOUTH;
      }

    } else if (internalFacing == EnumFacing.SOUTH) {

      if (externalFacing == EnumFacing.NORTH) {
        return EnumFacing.SOUTH;

      } else if (externalFacing == EnumFacing.EAST) {
        return EnumFacing.WEST;

      } else if (externalFacing == EnumFacing.SOUTH) {
        return EnumFacing.NORTH;

      } else if (externalFacing == EnumFacing.WEST) {
        return EnumFacing.EAST;
      }

    } else if (internalFacing == EnumFacing.WEST) {

      if (externalFacing == EnumFacing.NORTH) {
        return EnumFacing.EAST;

      } else if (externalFacing == EnumFacing.EAST) {
        return EnumFacing.SOUTH;

      } else if (externalFacing == EnumFacing.SOUTH) {
        return EnumFacing.WEST;

      } else if (externalFacing == EnumFacing.WEST) {
        return EnumFacing.NORTH;
      }
    }

    return externalFacing;
  }

  private FacingHelper() {
    //
  }

}
