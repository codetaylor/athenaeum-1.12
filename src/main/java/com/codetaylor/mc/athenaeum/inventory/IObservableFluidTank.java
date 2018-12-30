package com.codetaylor.mc.athenaeum.inventory;

import net.minecraftforge.fluids.FluidTank;

public interface IObservableFluidTank {

  void addObserver(IContentsChangedEventHandler handler);

  interface IContentsChangedEventHandler {

    void onContentsChanged(FluidTank fluidTank, int amount);
  }
}
