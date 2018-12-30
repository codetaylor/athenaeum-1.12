package com.codetaylor.mc.athenaeum.inventory;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ObservableFluidTank
    extends FluidTank
    implements IObservableFluidTank {

  private List<IContentsChangedEventHandler> eventHandlerList;

  public ObservableFluidTank(int capacity) {

    super(capacity);
    this.eventHandlerList = new ArrayList<>();
  }

  public ObservableFluidTank(@Nullable FluidStack fluidStack, int capacity) {

    super(fluidStack, capacity);
    this.eventHandlerList = new ArrayList<>();
  }

  public ObservableFluidTank(Fluid fluid, int amount, int capacity) {

    super(fluid, amount, capacity);
    this.eventHandlerList = new ArrayList<>();
  }

  @Override
  public void addObserver(IContentsChangedEventHandler handler) {

    this.eventHandlerList.add(handler);
  }

  @Override
  public int fillInternal(FluidStack resource, boolean doFill) {

    int amount = super.fillInternal(resource, doFill);

    if (amount > 0 && doFill) {
      this.onContentsChanged(amount);
    }

    return amount;
  }

  @Nullable
  @Override
  public FluidStack drainInternal(int maxDrain, boolean doDrain) {

    FluidStack fluidStack = super.drainInternal(maxDrain, doDrain);

    if (fluidStack != null && doDrain) {
      this.onContentsChanged(-fluidStack.amount);
    }

    return fluidStack;
  }

  @Override
  public void setCapacity(int capacity) {

    this.capacity = capacity;

    if (this.fluid != null
        && this.fluid.amount > capacity) {
      this.drainInternal(this.fluid.amount - capacity, true);
    }
  }

  protected void onContentsChanged(int amount) {

    if (amount != 0
        && !this.eventHandlerList.isEmpty()) {

      for (IContentsChangedEventHandler handler : this.eventHandlerList) {
        handler.onContentsChanged(this, amount);
      }
    }
  }
}
