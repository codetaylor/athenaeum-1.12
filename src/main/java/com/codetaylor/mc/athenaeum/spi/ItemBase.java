package com.codetaylor.mc.athenaeum.spi;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class ItemBase
    extends Item {

  public ItemBase(String modId, CreativeTabs creativeTab, String name) {

    this.setRegistryName(new ResourceLocation(modId, name));
    this.setUnlocalizedName(modId + "." + name);
    this.setCreativeTab(creativeTab);
  }
}
