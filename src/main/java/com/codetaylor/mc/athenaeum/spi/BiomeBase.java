package com.codetaylor.mc.athenaeum.spi;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

public abstract class BiomeBase
    extends Biome {

  public BiomeBase(BiomeProperties properties) {

    super(properties);
  }

  public abstract int getBiomeWeight();

  public abstract BiomeDictionary.Type[] getTypes();

  public BiomeBase mutate(Random rand) {

    return this;
  }
}
