package com.codetaylor.mc.athenaeum.tile;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IContainer {

  List<ItemStack> getBlockBreakDrops();

}
