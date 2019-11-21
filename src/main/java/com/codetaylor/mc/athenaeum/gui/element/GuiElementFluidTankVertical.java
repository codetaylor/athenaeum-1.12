package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import net.minecraftforge.fluids.FluidTank;

public class GuiElementFluidTankVertical
    extends GuiElementFluidTankBase {

  public GuiElementFluidTankVertical(
      GuiContainerBase guiBase,
      FluidTank fluidTank,
      int elementX,
      int elementY,
      int elementWidth,
      int elementHeight
  ) {

    super(
        guiBase,
        elementX,
        elementY,
        elementWidth,
        elementHeight,
        fluidTank
    );
  }

  @Override
  protected int elementHeightModifiedGet() {

    int elementHeightModified = (int) (this.scalarPercentageGet() * this.elementHeight);
    int min = Math.min(elementHeightModified, this.elementHeight);
    return Math.max(0, min);
  }

  @Override
  protected int elementYModifiedGet() {

    int elementHeightModified = (int) (this.scalarPercentageGet() * this.elementHeight);
    int min = Math.min(elementHeightModified, this.elementHeight);
    return this.elementHeight - Math.max(0, min) + super.elementYModifiedGet();
  }
}
