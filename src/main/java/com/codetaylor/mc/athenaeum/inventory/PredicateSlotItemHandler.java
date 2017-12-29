package com.codetaylor.mc.athenaeum.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

/**
 * This class extends {@link SlotItemHandler} and takes a {@link Predicate} that
 * is used to determine if an item can go into the slot.
 */
public class PredicateSlotItemHandler
    extends SlotItemHandler {

  private final Predicate<ItemStack> predicate;

  public PredicateSlotItemHandler(
      Predicate<ItemStack> predicate,
      IItemHandler itemHandler,
      int index,
      int xPosition,
      int yPosition
  ) {

    super(itemHandler, index, xPosition, yPosition);
    this.predicate = predicate;
  }

  @Override
  public boolean isItemValid(@Nonnull ItemStack stack) {

    return this.predicate.test(stack);
  }
}
