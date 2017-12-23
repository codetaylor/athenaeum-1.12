package com.codetaylor.mc.athenaeum.util;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;

/**
 * This utility class holds references to common, reusable variant properties.
 */
@SuppressWarnings("WeakerAccess")
public class Properties {

  public static final PropertyEnum<EnumFacing.Axis> PORTAL_AXIS = PropertyEnum.create(
      "axis",
      EnumFacing.Axis.class,
      EnumFacing.Axis.X,
      EnumFacing.Axis.Z
  );

  private Properties() {
    //
  }

}
