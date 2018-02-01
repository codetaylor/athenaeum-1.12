package com.codetaylor.mc.athenaeum.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiHelper {

  public static void drawTexturedRect(
      Minecraft minecraft,
      ResourceLocation texture,
      int x,
      int y,
      int width,
      int height,
      int zLevel,
      double u0,
      double v0,
      double u1,
      double v1
  ) {

    TextureManager renderEngine = minecraft.getRenderManager().renderEngine;
    renderEngine.bindTexture(texture);

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
    bufferbuilder
        .pos(x, (y + height), zLevel)
        .tex(u0, v1)
        .endVertex();
    bufferbuilder
        .pos((x + width), (y + height), zLevel)
        .tex(u1, v1)
        .endVertex();
    bufferbuilder
        .pos((x + width), y, zLevel)
        .tex(u1, v0)
        .endVertex();
    bufferbuilder
        .pos(x, y, zLevel)
        .tex(u0, v0)
        .endVertex();
    tessellator.draw();
  }

  public static void drawColoredRect(
      BufferBuilder renderer,
      int x,
      int y,
      int width,
      int height,
      int red,
      int green,
      int blue,
      int alpha
  ) {

    renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
    renderer.pos((double) (x + 0), (double) (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos((double) (x + 0), (double) (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos((double) (x + width), (double) (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos((double) (x + width), (double) (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
    Tessellator.getInstance().draw();
  }

  public static void drawDurabilityBar(
      int xPosition,
      int yPosition,
      double durabilityPercentage,
      int color,
      int width
  ) {

    GlStateManager.disableLighting();
    GlStateManager.disableDepth();
    GlStateManager.disableTexture2D();
    GlStateManager.disableAlpha();
    GlStateManager.disableBlend();
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    int subWidth = Math.round((float) width - (float) durabilityPercentage * (float) width);
    xPosition += 2;
    yPosition += 13;
    GuiHelper.drawColoredRect(bufferbuilder, xPosition, yPosition, width, 2, 0, 0, 0, 255);

    int red = color >> 16 & 255;
    int green = color >> 8 & 255;
    int blue = color & 255;
    GuiHelper.drawColoredRect(bufferbuilder, xPosition, yPosition, subWidth, 1, red, green, blue, 255);
    GlStateManager.enableBlend();
    GlStateManager.enableAlpha();
    GlStateManager.enableTexture2D();
    GlStateManager.enableLighting();
    GlStateManager.enableDepth();
  }

  public static void drawVerticalScaledTexturedModalRectFromIconAnchorBottomLeft(
      int x,
      int y,
      int z,
      TextureAtlasSprite icon,
      int width,
      int height
  ) {

    if (icon == null) {
      return;
    }

    int iconHeight = icon.getIconHeight();
    int iconWidth = icon.getIconWidth();

    double minU = icon.getMinU();
    double maxU = icon.getMaxU();
    double minV = icon.getMinV();
    double maxV = icon.getMaxV();

    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

    int sections = height / iconHeight;

    for (int i = 0; i < sections; i++) {
      buffer.pos(x, y + height - (i * iconHeight), z)
          .tex(minU, maxV)
          .endVertex();

      buffer.pos(x + width, y + height - (i * iconHeight), z)
          .tex(minU + (maxU - minU) * width / (float) iconWidth, maxV)
          .endVertex();

      buffer.pos(x + width, y + height - ((i + 1) * iconHeight), z)
          .tex(minU + (maxU - minU) * width / (float) iconWidth, maxV - (maxV - minV))
          .endVertex();

      buffer.pos(x, y + height - ((i + 1) * iconHeight), z)
          .tex(minU, maxV - (maxV - minV))
          .endVertex();
    }

    int remainder = height - sections * iconHeight;

    if (remainder > 0) {

      buffer.pos(x, y + height - (sections * iconHeight), z)
          .tex(minU, maxV)
          .endVertex();

      buffer.pos(x + width, y + height - (sections * iconHeight), z)
          .tex(minU + (maxU - minU) * width / (float) iconWidth, maxV)
          .endVertex();

      buffer.pos(x + width, y + height - (sections * iconHeight + remainder), z)
          .tex(minU + (maxU - minU) * width / (float) iconWidth, maxV - (maxV - minV) * remainder / (float) iconHeight)
          .endVertex();

      buffer.pos(x, y + height - (sections * iconHeight + remainder), z)
          .tex(minU, maxV - (maxV - minV) * remainder / (float) iconHeight)
          .endVertex();
    }

    /*buffer.pos(x, y + height, z).tex(minU, maxV).endVertex();

    buffer.pos(x + width, y + height, z).tex(minU + (maxU - minU) * width / 16F, maxV).endVertex();

    buffer.pos(x + width, y, z)
        .tex(minU + (maxU - minU) * width / 16F, maxV - (maxV - minV) * height / 16F)
        .endVertex();

    buffer.pos(x, y, z).tex(minU, maxV - (maxV - minV) * height / 16F).endVertex();*/

    Tessellator.getInstance().draw();

  }

  public static int getFluidHeight(int fluidAmount, int fluidCapacity, int displayHeight) {

    float fluidHeightScalar = GuiHelper.getFluidHeightScalar(fluidAmount, fluidCapacity, displayHeight);
    int elementHeightModified = (int) (fluidHeightScalar * displayHeight);
    return Math.max(0, Math.min(elementHeightModified, displayHeight));
  }

  public static float getFluidHeightScalar(int fluidAmount, int fluidCapacity, int displayHeight) {

    if (fluidAmount > 0) {
      return Math.max((float) fluidAmount / (float) fluidCapacity, 1 / (float) displayHeight);

    } else {
      return 0;
    }
  }

  public static int getFluidY(int fluidAmount, int fluidCapacity, int displayHeight, int offsetY) {

    float fluidHeightScalar = GuiHelper.getFluidHeightScalar(fluidAmount, fluidCapacity, displayHeight);
    int elementHeightModified = (int) (fluidHeightScalar * displayHeight);
    return displayHeight - Math.max(0, Math.min(elementHeightModified, displayHeight)) + offsetY;
  }

  private GuiHelper() {
    //
  }

}
