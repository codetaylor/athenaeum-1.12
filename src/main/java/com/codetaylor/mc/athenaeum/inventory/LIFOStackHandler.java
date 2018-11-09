package com.codetaylor.mc.athenaeum.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class LIFOStackHandler
    extends ObservableStackHandler {

  public interface IContentsClearedEventHandler {

    void onContentsCleared(ItemStackHandler stackHandler);
  }

  private final List<IContentsClearedEventHandler> contentsClearedEventHandlerList;

  public LIFOStackHandler(int size) {

    super(size);
    this.contentsClearedEventHandlerList = new ArrayList<>(1);
  }

  public void addObserverContentsCleared(IContentsClearedEventHandler handler) {

    this.contentsClearedEventHandlerList.add(handler);
  }

  /**
   * Set all slots to an empty ItemStack.
   */
  public void clear() {

    for (int i = 0; i < this.stacks.size(); i++) {
      this.stacks.set(i, ItemStack.EMPTY);
    }

    // If we trigger the slot changed method, a block update packet will be sent
    // for all 16 slots. Doing it this way sends the update packet only once.
    for (IContentsClearedEventHandler handler : this.contentsClearedEventHandlerList) {
      handler.onContentsCleared(this);
    }
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {

    // Always insert into the first empty slot regardless of the slot attempted.

    for (int i = 0; i < this.stacks.size(); i++) {

      if (this.stacks.get(i).isEmpty()) {
        return super.insertItem(i, stack, simulate);
      }
    }

    return stack;
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {

    // Always extract the last item regardless of the slot attempted.

    for (int i = this.stacks.size() - 1; i >= 0; i--) {

      if (!this.stacks.get(i).isEmpty()) {
        return super.extractItem(i, amount, simulate);
      }
    }

    return ItemStack.EMPTY;
  }
}
