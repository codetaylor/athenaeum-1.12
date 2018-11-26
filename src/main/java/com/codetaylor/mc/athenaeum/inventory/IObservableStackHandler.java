package com.codetaylor.mc.athenaeum.inventory;

import net.minecraftforge.items.ItemStackHandler;

public interface IObservableStackHandler {

  void addObserver(IContentsChangedEventHandler handler);

  public interface IContentsChangedEventHandler {

    void onContentsChanged(ItemStackHandler stackHandler, int slotIndex);
  }
}
