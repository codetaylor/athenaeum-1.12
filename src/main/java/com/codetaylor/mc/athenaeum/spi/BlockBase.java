package com.codetaylor.mc.athenaeum.spi;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public abstract class BlockBase
    extends Block {

  public BlockBase(String modId, CreativeTabs creativeTab, Material materialIn, String name) {

    this(modId, creativeTab, materialIn, name, materialIn.getMaterialMapColor());
  }

  public BlockBase(String modId, CreativeTabs creativeTab, Material materialIn, String name, MapColor mapColor) {

    super(materialIn, mapColor);
    this.setRegistryName(modId, name);
    this.setUnlocalizedName(modId + "." + name);
    this.setCreativeTab(creativeTab);
  }

}
