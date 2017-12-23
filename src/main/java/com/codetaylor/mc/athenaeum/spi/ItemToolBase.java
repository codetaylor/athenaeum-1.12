package com.codetaylor.mc.athenaeum.spi;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public abstract class ItemToolBase
    extends ItemTool {

  public ItemToolBase(
      String modId,
      CreativeTabs creativeTab,
      String name,
      float attackDamageIn,
      float attackSpeedIn,
      Item.ToolMaterial materialIn,
      Set<Block> effectiveBlocksIn
  ) {

    super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
    this.setRegistryName(new ResourceLocation(modId, name));
    this.setUnlocalizedName(modId + "." + name);
    this.setCreativeTab(creativeTab);
  }

  public ItemToolBase(
      String modId,
      CreativeTabs creativeTab,
      String name,
      Item.ToolMaterial materialIn,
      Set<Block> effectiveBlocksIn
  ) {

    super(materialIn, effectiveBlocksIn);
    this.setRegistryName(new ResourceLocation(modId, name));
    this.setUnlocalizedName(modId + "." + name);
    this.setCreativeTab(creativeTab);
  }
}
