package com.codetaylor.mc.athenaeum.spi;

import net.minecraft.block.properties.IProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IBlockVariant<T extends IVariant & Comparable<T>> {

  @Nonnull
  String getModelName(ItemStack itemStack);

  @Nonnull
  IProperty<T> getVariant();

}
