package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public abstract class GuiElementFluidTankBase
    extends GuiElementTiledTextureAtlasSpriteBase {

  protected final FluidTank fluidTank;
  private TextureAtlasSprite fluidSprite;

  public GuiElementFluidTankBase(GuiContainerBase guiBase, int elementX, int elementY, int elementWidth, int elementHeight, FluidTank fluidTank) {

    super(guiBase, elementX, elementY, elementWidth, elementHeight);
    this.fluidTank = fluidTank;
  }

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

  @Override
  public void drawBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    FluidStack fluid = this.fluidTank.getFluid();

    if (fluid != null) {
      int color = fluid.getFluid().getColor();
      GlStateManager.color(
          ((color >> 16) & 0xFF) / 255f,
          ((color >> 8) & 0xFF) / 255f,
          (color & 0xFF) / 255f,
          ((color >> 24) & 0xFF) / 255f
      );
    }

    super.drawBackgroundLayer(partialTicks, mouseX, mouseY);

    if (fluid != null) {
      GlStateManager.color(1, 1, 1, 1);
    }
  }
}
