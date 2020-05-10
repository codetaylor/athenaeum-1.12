package com.codetaylor.mc.athenaeum.inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class extends {@link LargeItemStackHandler} and allows registering
 * listeners that are triggered when the contents of the handler change.
 */
public class LargeObservableStackHandler
    extends LargeItemStackHandler
    implements IObservableStackHandler {

  private final List<IContentsChangedEventHandler> eventHandlerList;

  public LargeObservableStackHandler(int size) {

    super(size);
    this.eventHandlerList = new ArrayList<>();
  }

  @Override
  public void addObserver(IContentsChangedEventHandler handler) {

    this.eventHandlerList.add(handler);
  }

  @Override
  protected void onContentsChanged(int slot) {

    for (IContentsChangedEventHandler handler : this.eventHandlerList) {
      handler.onContentsChanged(this, slot);
    }

    super.onContentsChanged(slot);
  }
}
