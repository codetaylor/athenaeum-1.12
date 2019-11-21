package com.codetaylor.mc.athenaeum.gui;

import com.codetaylor.mc.athenaeum.gui.element.GuiElementBase;
import com.codetaylor.mc.athenaeum.gui.element.IGuiElementClickable;
import com.codetaylor.mc.athenaeum.gui.element.IGuiElementTooltipExtendedProvider;
import com.codetaylor.mc.athenaeum.gui.element.IGuiElementTooltipProvider;
import com.codetaylor.mc.athenaeum.util.TooltipHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class GuiContainerBase
    extends GuiContainer {

  private List<GuiElementBase> guiElementList;
  private List<IGuiElementClickable> guiElementClickableList;
  private List<IGuiElementTooltipProvider> tooltipProviderList;
  private List<String> tooltipTextList;

  protected ScaledResolution scaledResolution;

  public GuiContainerBase(ContainerBase container, int width, int height) {

    super(container);
    this.xSize = width;
    this.ySize = height;
    this.guiElementList = new ArrayList<>();
    this.guiElementClickableList = new ArrayList<>();
    this.tooltipProviderList = new ArrayList<>();
    this.tooltipTextList = new ArrayList<>();
    this.scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
  }

  public FontRenderer getFontRenderer() {

    return this.fontRenderer;
  }

  public RenderItem getItemRender() {

    return this.itemRender;
  }

  protected void guiContainerElementAdd(GuiElementBase... elements) {

    for (GuiElementBase element : elements) {
      this.guiElementList.add(element);

      if (element instanceof IGuiElementClickable) {
        this.guiElementClickableList.add((IGuiElementClickable) element);
      }
    }
  }

  @Override
  public void handleMouseInput() throws IOException {

    super.handleMouseInput();

    for (GuiElementBase element : this.guiElementList) {
      element.handleMouseInput();
    }
  }

  public int guiContainerOffsetXGet() {

    return (this.scaledResolution.getScaledWidth() - this.xSize) / 2;
  }

  public int guiContainerOffsetYGet() {

    return (this.scaledResolution.getScaledHeight() - this.ySize) / 2;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    GL11.glStencilMask(0xFF);
    GL11.glClearStencil(0);
    GlStateManager.clear(GL11.GL_STENCIL_BUFFER_BIT);

    this.scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

    for (GuiElementBase element : this.guiElementList) {
      element.update(partialTicks);

      if (element.elementIsVisible(mouseX, mouseY)) {
        element.drawBackgroundLayer(partialTicks, mouseX, mouseY);
      }
    }
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    this.tooltipProviderList.clear();

    for (GuiElementBase element : this.guiElementList) {

      if (element.elementIsVisible(mouseX, mouseY)) {
        element.drawForegroundLayer(mouseX, mouseY);

        if (element instanceof IGuiElementTooltipProvider
            && element.elementIsMouseInside(mouseX, mouseY)) {

          this.tooltipProviderList.add((IGuiElementTooltipProvider) element);
        }
      }
    }

    for (IGuiElementTooltipProvider element : this.tooltipProviderList) {
      this.tooltipTextList.clear();

      if (element.elementIsVisible(mouseX, mouseY)
          && element.elementIsMouseInside(mouseX, mouseY)) {
        element.tooltipTextGet(this.tooltipTextList);

        if (element instanceof IGuiElementTooltipExtendedProvider) {

          if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
              || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            ((IGuiElementTooltipExtendedProvider) element).tooltipTextExtendedGet(this.tooltipTextList);

          } else {
            this.tooltipTextList.add(TooltipHelper.tooltipHoldShiftStringGet());
          }
        }

        this.drawHoveringText(
            this.tooltipTextList,
            mouseX - this.guiContainerOffsetXGet(),
            mouseY - this.guiContainerOffsetYGet()
        );
      }
    }
  }

  /**
   * Test if the 2D point is in a rectangle (relative to the GUI).
   */
  public boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {

    int i = this.guiLeft;
    int j = this.guiTop;
    pointX = pointX - i;
    pointY = pointY - j;
    return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
  }

  @Override
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

    super.mouseClicked(mouseX, mouseY, mouseButton);

    for (IGuiElementClickable element : this.guiElementClickableList) {

      element.mouseClicked(mouseX, mouseY, mouseButton);

      if (((GuiElementBase) element).elementIsMouseInside(mouseX, mouseY)
          && ((GuiElementBase) element).elementIsVisible(mouseX, mouseY)) {
        element.elementClicked(mouseX, mouseY, mouseButton);
      }
    }
  }

  public void drawScaledTexturedModalRectFromIcon(int x, int y, TextureAtlasSprite icon, int width, int height) {

    GuiHelper.drawScaledTexturedModalRectFromIcon(x, y, this.zLevel, icon, width, height);
  }

  public void drawScaledTexturedModalRectFromIconAnchorBottomLeft(
      int x,
      int y,
      TextureAtlasSprite icon,
      int width,
      int height
  ) {

    GuiHelper.drawScaledTexturedModalRectFromIconAnchorBottomLeft(x, y, this.zLevel, icon, width, height);
  }

  public void drawSizedModalRect(int x1, int y1, int x2, int y2, Color color) {

    GuiHelper.drawSizedModalRect(x1, y1, x2, y2, this.zLevel, color);
  }

  /**
   * Draws a textured square with an optionally rotated texture.
   *
   * @param x        the x
   * @param y        the y
   * @param textureX the texture x
   * @param textureY the texture y
   * @param size     the size
   * @param rotation (clockwise) 0 = 0 degrees, 1 = 90 degrees, 2 = 180 degrees, 3 = 270 degrees
   */
  public void drawRotatedTexturedModalSquare(int x, int y, int textureX, int textureY, int size, int rotation) {

    GuiHelper.drawRotatedTexturedModalSquare(x, y, this.zLevel, textureX, textureY, size, rotation);
  }

  public void drawString(String format, int x, int y) {

    this.drawString(format, x, y, Color.WHITE.getRGB());
  }

  public void drawString(String format, int x, int y, int color) {

    this.fontRenderer.drawString(format, x, y, color);
  }

  public int getGuiLeft() {

    return this.guiLeft;
  }

  public int getGuiTop() {

    return this.guiTop;
  }

}
