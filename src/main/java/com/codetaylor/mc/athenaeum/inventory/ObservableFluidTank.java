package com.codetaylor.mc.athenaeum.inventory;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
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

    super(capacity);
    this.eventHandlerList = new ArrayList<>();

    if (fluidStack != null) {
      this.fluid = fluidStack.copy();
    }
  }

  public ObservableFluidTank(Fluid fluid, int amount, int capacity) {

    this(new FluidStack(fluid, amount), capacity);
  }

  @Override
  public void addObserver(IContentsChangedEventHandler handler) {

    this.eventHandlerList.add(handler);
  }

  @Override
  public int fill(FluidStack resource, FluidAction action) {

    int amount = super.fill(resource, action);

    if (amount > 0 && action == FluidAction.EXECUTE) {
      this.onContentsChanged(amount);
    }

    return amount;
  }

  @Nonnull
  @Override
  public FluidStack drain(int maxDrain, FluidAction action) {

    FluidStack fluidStack = super.drain(maxDrain, action);

    if (action == FluidAction.EXECUTE) {
      this.onContentsChanged(-fluidStack.getAmount());
    }

    return fluidStack;
  }

  @Override
  public FluidTank setCapacity(int capacity) {

    this.capacity = capacity;

    if (this.fluid.getAmount() > capacity) {
      this.drain(this.fluid.getAmount() - capacity, FluidAction.EXECUTE);
    }
    return this;
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
