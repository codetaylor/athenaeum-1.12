package com.codetaylor.mc.athenaeum.inventory;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class LargeDynamicItemLimitedStackHandler
    extends LargeDynamicStackHandler {

  private final int itemCapacity;

  public LargeDynamicItemLimitedStackHandler(int initialSize, int itemCapacity) {

    super(initialSize);
    this.itemCapacity = itemCapacity;
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {

    int totalItemCount = this.getTotalItemCount();
    int remainingCapacity = this.itemCapacity - totalItemCount;

    if (remainingCapacity == 0) {
      return stack;

    } else {
      int stackCount = stack.getCount();

      if (remainingCapacity < stackCount) {

        ItemStack copy = stack.copy();
        copy.setCount(remainingCapacity);

        ItemStack rejectedItems = super.insertItem(slot, copy, simulate);
        ItemStack result = stack.copy();

        int rejectedCount = rejectedItems.getCount();
        int insertedCount = remainingCapacity - rejectedCount;
        result.setCount(stackCount - insertedCount);

        return result;
      }
    }

    return super.insertItem(slot, stack, simulate);
  }

  @Override
  public ItemStack insertItem(ItemStack itemStack, boolean simulate) {

    int totalItemCount = this.getTotalItemCount();
    int remainingCapacity = this.itemCapacity - totalItemCount;

    if (remainingCapacity == 0) {
      return itemStack;

    } else {
      int stackCount = itemStack.getCount();

      if (remainingCapacity < stackCount) {

        ItemStack copy = itemStack.copy();
        copy.setCount(remainingCapacity);

        super.insertItem(copy, simulate);

        ItemStack result = itemStack.copy();
        result.setCount(stackCount - remainingCapacity);
        return result;
      }
    }

    return super.insertItem(itemStack, simulate);
  }
}
