package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class GuiElementFluidTank
    extends GuiElementTiledTextureAtlasSpriteBase {

  protected final FluidTank fluidTank;
  private TextureAtlasSprite fluidSprite;

  public GuiElementFluidTank(
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
        elementHeight
    );

    this.fluidTank = fluidTank;
  }

  @Override
  protected float scalarPercentageGet() {

    int fluidAmount = this.fluidTank.getFluidAmount();

    if (fluidAmount > 0) {
      int capacity = this.fluidTank.getCapacity();
      return Math.max((float) fluidAmount / (float) capacity, 1 / (float) this.elementHeight);
    }

    return 0;
  }

  @Override
  protected TextureAtlasSprite textureAtlasSpriteGet() {

    FluidStack fluidStack = this.fluidTank.getFluid();

    if (fluidStack == null) {
      this.fluidSprite = null;

    } else if (this.fluidSprite == null) {
      ResourceLocation resourceLocation = fluidStack.getFluid().getStill();
      this.fluidSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(resourceLocation.toString());
    }

    return this.fluidSprite;
  }
}
