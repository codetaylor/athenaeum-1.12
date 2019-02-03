package com.codetaylor.mc.athenaeum.util;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class SoundHelper {

  public static void playSoundServer(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory soundCategory) {

    SoundHelper.playSoundServer(world, pos, soundEvent, soundCategory, 1);
  }

  public static void playSoundServer(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory soundCategory, float volumeModifier) {

    if (world.isRemote) {
      return;
    }

    world.playSound(
        null,
        pos.getX() + 0.5f,
        pos.getY() + 0.5f,
        pos.getZ() + 0.5f,
        soundEvent,
        soundCategory,
        (0.5F + (float) Math.random() * 0.5F) * volumeModifier,
        0.9F + (float) Math.random() * 0.15F
    );
  }

  private SoundHelper() {
    //
  }

}
