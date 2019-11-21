package com.codetaylor.mc.athenaeum.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiHelper {

  public static void drawModalRectWithCustomSizedTexture(int x, int y, int z, float u, float v, int width, int height, float textureWidth, float textureHeight) {

    float f = 1.0F / textureWidth;
    float f1 = 1.0F / textureHeight;
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
    bufferbuilder.pos((double) x, (double) (y + height), z).tex((double) (u * f), (double) ((v + (float) height) * f1)).endVertex();
    bufferbuilder.pos((double) (x + width), (double) (y + height), z).tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
    bufferbuilder.pos((double) (x + width), (double) y, z).tex((double) ((u + (float) width) * f), (double) (v * f1)).endVertex();
    bufferbuilder.pos((double) x, (double) y, z).tex((double) (u * f), (double) (v * f1)).endVertex();
    tessellator.draw();
  }

  public static void drawStringOutlined(
      String translateKey,
      int x,
      int y,
      FontRenderer fontRenderer,
      int textShadowColor
  ) {

    String displayText = I18n.format(translateKey);

    fontRenderer.drawString(displayText, x + 0, y + 1, textShadowColor);
    fontRenderer.drawString(displayText, x + 1, y + 1, textShadowColor);
    fontRenderer.drawString(displayText, x + 1, y - 1, textShadowColor);
    fontRenderer.drawString(displayText, x + 1, y + 0, textShadowColor);

    fontRenderer.drawString(displayText, x - 0, y - 1, textShadowColor);
    fontRenderer.drawString(displayText, x - 1, y - 1, textShadowColor);
    fontRenderer.drawString(displayText, x - 1, y + 1, textShadowColor);
    fontRenderer.drawString(displayText, x - 1, y - 0, textShadowColor);

    fontRenderer.drawString(displayText, x, y, Color.BLACK.getRGB());
  }

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
      float z,
      TextureAtlasSprite icon,
      int width,
      int height
  ) {

    // TODO: this only handles tiling vertically, need to implement horizontal tiling as well

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

  public static void drawScaledTexturedModalRectFromIconAnchorBottomLeft(
      int x,
      int y,
      float z,
      TextureAtlasSprite icon,
      int width,
      int height
  ) {

//    double scaledTime = (double) Minecraft.getMinecraft().world.getTotalWorldTime() * 0.05;
//    height += (Math.sin(scaledTime) * 0.5 + 0.5) * 32;
//    width += (Math.sin(scaledTime) * 0.5 + 0.5) * 32;

    if (icon == null) {
      return;
    }

    int iconHeight = icon.getIconHeight();
    int iconWidth = icon.getIconWidth();

    double minU = icon.getMinU();
    double maxU = icon.getMaxU();
    double minV = icon.getMinV();
    double maxV = icon.getMaxV();

    int verticalSections = height / iconHeight + 1;
    int horizontalSections = width / iconWidth + 1;

    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

    for (int i = 0; i < verticalSections; i++) {
      for (int j = 0; j < horizontalSections; j++) {

        int px1 = x + (j * iconWidth);
        int px2 = x + Math.min((j + 1) * iconWidth, width);
        int py1 = y + height - (i * iconHeight);
        int py2 = y + height - Math.min((i + 1) * iconHeight, height);

        double tu2 = minU + (maxU - minU) * (((j + 1) * iconWidth > width)
            ? (width - (j * iconWidth)) / (float) iconWidth : 1);
        double tv2 = maxV - (maxV - minV) * (((i + 1) * iconHeight > height)
            ? (height - (i * iconHeight)) / (float) iconHeight : 1);

        buffer.pos(px1, py1, z).tex(minU, maxV).endVertex();
        buffer.pos(px2, py1, z).tex(tu2, maxV).endVertex();
        buffer.pos(px2, py2, z).tex(tu2, tv2).endVertex();
        buffer.pos(px1, py2, z).tex(minU, tv2).endVertex();
      }
    }

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

  // https://github.com/TheCBProject/CoFHLib/blob/master/src/main/java/cofh/lib/gui/GuiBase.java
  public static void drawScaledTexturedModalRectFromIcon(
      int x,
      int y,
      float z,
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
    buffer
        .pos(x, y + height, z)
        .tex(minU, minV + (maxV - minV) * height / (float) iconHeight)
        .endVertex();
    buffer
        .pos(x + width, y + height, z)
        .tex(minU + (maxU - minU) * width / (float) iconWidth, minV + (maxV - minV) * height / (float) iconHeight)
        .endVertex();
    buffer
        .pos(x + width, y, z)
        .tex(minU + (maxU - minU) * width / (float) iconWidth, minV)
        .endVertex();
    buffer
        .pos(x, y, z)
        .tex(minU, minV)
        .endVertex();
    Tessellator.getInstance().draw();

  }

  // https://github.com/TheCBProject/CoFHLib/blob/master/src/main/java/cofh/lib/gui/GuiBase.java
  public static void drawSizedModalRect(int x1, int y1, int x2, int y2, float z, Color color) {

    int temp;

    if (x1 < x2) {
      temp = x1;
      x1 = x2;
      x2 = temp;
    }

    if (y1 < y2) {
      temp = y1;
      y1 = y2;
      y2 = temp;
    }

    GlStateManager.enableBlend();
    GlStateManager.disableTexture2D();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

    float a = color.getAlpha() / 255f;
    float r = color.getRed() / 255f;
    float g = color.getGreen() / 255f;
    float b = color.getBlue() / 255f;
    GlStateManager.color(r, g, b, a);

    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
    buffer.pos(x1, y2, z).endVertex();
    buffer.pos(x2, y2, z).endVertex();
    buffer.pos(x2, y1, z).endVertex();
    buffer.pos(x1, y1, z).endVertex();
    Tessellator.getInstance().draw();

    GlStateManager.enableTexture2D();
    GlStateManager.disableBlend();

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
  public static void drawRotatedTexturedModalSquare(
      int x,
      int y,
      float z,
      int textureX,
      int textureY,
      int size,
      int rotation
  ) {

    // TODO: these magic numbers tho...

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder vertexbuffer = tessellator.getBuffer();
    vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);

    if (rotation == 1) {

      vertexbuffer
          .pos(x, y + size, z)
          .tex((textureX + size) * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, (y + size), z)
          .tex((textureX + size) * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, y, z)
          .tex(textureX * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x, y, z)
          .tex(textureX * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();

    } else if (rotation == 2) {

      vertexbuffer
          .pos(x, y + size, z)
          .tex((textureX + size) * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, (y + size), z)
          .tex(textureX * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, y, z)
          .tex(textureX * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x, y, z)
          .tex((textureX + size) * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();

    } else if (rotation == 3) {

      vertexbuffer
          .pos(x, y + size, z)
          .tex(textureX * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, (y + size), z)
          .tex(textureX * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, y, z)
          .tex((textureX + size) * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x, y, z)
          .tex((textureX + size) * 0.00390625F, textureY * 0.00390625F)
          .endVertex();

    } else { // rotation 0 is default

      vertexbuffer
          .pos(x, y + size, z)
          .tex(textureX * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, (y + size), z)
          .tex((textureX + size) * 0.00390625F, (textureY + size) * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x + size, y, z)
          .tex((textureX + size) * 0.00390625F, textureY * 0.00390625F)
          .endVertex();
      vertexbuffer
          .pos(x, y, z)
          .tex(textureX * 0.00390625F, textureY * 0.00390625F)
          .endVertex();

    }

    tessellator.draw();

  }

  private GuiHelper() {
    //
  }

}
