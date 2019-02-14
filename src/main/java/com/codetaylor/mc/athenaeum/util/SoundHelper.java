package com.codetaylor.mc.athenaeum.util;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class SoundHelper {

  public static float getPitchEntityItemPickup() {

    return ((RandomHelper.random().nextFloat() - RandomHelper.random().nextFloat()) * 0.7f + 1.0f) * 2.0f;
  }

  public static float getPitchDefault() {

    return 0.9f + (float) Math.random() * 0.15f;
  }

  public static void playSoundServer(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory soundCategory) {

    SoundHelper.playSoundServer(world, pos, soundEvent, soundCategory, 1);
  }

  public static void playSoundServer(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory soundCategory, float volumeModifier) {

    SoundHelper.playSoundServer(world, pos, soundEvent, soundCategory, volumeModifier, SoundHelper.getPitchDefault());
  }

  public static void playSoundServer(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory soundCategory, float volumeModifier, float pitch) {

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
        pitch
    );
  }

  private SoundHelper() {
    //
  }

}
