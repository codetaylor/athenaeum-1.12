package com.codetaylor.mc.athenaeum.inventory;

import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Random;

/**
 * An extension of the observable stack handler with a dynamically sized
 * backing array.
 */
public class DynamicStackHandler
    extends ObservableStackHandler
    implements ITileDataItemStackHandler {

  public DynamicStackHandler(int initialSize) {

    super(1);
    this.stacks = new ItemStackList(initialSize);
  }

  @Override
  public void setSize(int size) {

    this.stacks = new ItemStackList(size);
  }

  public int getTotalItemCount() {

    int result = 0;

    for (int i = 0; i < this.getSlots(); i++) {
      ItemStack stackInSlot = this.getStackInSlot(i);

      if (!stackInSlot.isEmpty()) {
        result += stackInSlot.getCount();
      }
    }

    return result;
  }

  public ItemStack getFirstNonEmptyItemStack() {

    for (int i = 0; i < this.getSlots(); i++) {
      ItemStack stackInSlot = this.getStackInSlot(i);

      if (!stackInSlot.isEmpty()) {
        return stackInSlot;
      }
    }
    return ItemStack.EMPTY;
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {

    while (this.getSlots() - 1 < slot) {
      this.stacks.add(ItemStack.EMPTY);
    }

    return super.insertItem(slot, stack, simulate);
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {

    if (slot > this.getSlots() - 1) {
      return ItemStack.EMPTY;
    }

    return super.extractItem(slot, amount, simulate);
  }

  public ItemStack extractRandomItem(boolean simulate, Random random) {

    IntArrayList slots = new IntArrayList(this.getSlots());

    for (int i = 0; i < this.getSlots(); i++) {

      if (!this.getStackInSlot(i).isEmpty()) {
        slots.add(i);
      }
    }

    if (slots.size() == 0) {
      return ItemStack.EMPTY;
    }

    int slot = slots.getInt(random.nextInt(slots.size()));
    return this.extractItem(slot, 1, simulate);
  }

  public ItemStack extractItem(boolean simulate) {

    for (int i = this.getSlots() - 1; i >= 0; i--) {

      if (!this.getStackInSlot(i).isEmpty()) {
        return this.extractItem(i, 1, simulate);
      }
    }

    return ItemStack.EMPTY;
  }

  @Override
  public void setStackInSlot(int slot, @Nonnull ItemStack stack) {

    while (this.getSlots() - 1 < slot) {
      this.stacks.add(ItemStack.EMPTY);
    }

    super.setStackInSlot(slot, stack);
  }

  public ItemStack insertItem(ItemStack itemStack, boolean simulate) {

    ItemStack remaining = itemStack;
    int i = 0;

    while (!remaining.isEmpty()) {

      while (this.getSlots() - 1 < i) {
        this.stacks.add(ItemStack.EMPTY);
      }

      remaining = super.insertItem(i, remaining, simulate);
      i += 1;
    }

    return ItemStack.EMPTY;
  }

  public void clearStacks() {

    for (int i = 0; i < this.getSlots(); i++) {
      this.extractItem(i, this.getSlotLimit(i), false);
    }
  }

  private class ItemStackList
      extends NonNullList<ItemStack> {

    public ItemStackList(int size) {

      super(new ArrayList<>(size), ItemStack.EMPTY);

      for (int i = 0; i < size; i++) {
        this.add(ItemStack.EMPTY);
      }
    }
  }
}
