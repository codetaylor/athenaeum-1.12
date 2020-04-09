package com.codetaylor.mc.athenaeum.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

import java.util.List;

public final class RenderHelper {

  private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

  public static void renderItemModelIntoGUI(ItemStack stack, int x, int y, IBakedModel bakedmodel, boolean renderEffect, float zLevel, float alpha) {

    GlStateManager.pushMatrix();

    // Use GlStateManager.enableNormalize() instead of GlStateManager.enableRescaleNormal() because it works with non-uniform scales.
    GlStateManager.enableNormalize();

    GlStateManager.enableAlpha();
    GlStateManager.alphaFunc(516, 0.1F);
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

    GlStateManager.color(1, 1, 1, 1);

    RenderHelper.setupGuiTransform(x, y, bakedmodel.isGui3d(), zLevel);

    RenderHelper.renderItemModelCustom(stack, bakedmodel, ItemCameraTransforms.TransformType.GUI, false, renderEffect, alpha);

    GlStateManager.disableAlpha();
    GlStateManager.disableRescaleNormal();
    GlStateManager.disableLighting();
    GlStateManager.popMatrix();
  }

  public static void setupGuiTransform(int xPosition, int yPosition, boolean isGui3d, float zLevel) {

    GlStateManager.translate((float) xPosition, (float) yPosition, 100.0F + zLevel);
    GlStateManager.translate(8.0F, 8.0F, 0.0F);
    GlStateManager.scale(1.0F, -1.0F, 1.0F);
    GlStateManager.scale(16.0F, 16.0F, 16.0F);

    if (isGui3d) {
      GlStateManager.enableLighting();

    } else {
      GlStateManager.disableLighting();
    }
  }

  public static void renderItemModel(
      ItemStack itemStack,
      IBakedModel model,
      ItemCameraTransforms.TransformType transform,
      boolean leftHanded,
      boolean renderEffect
  ) {

    if (!itemStack.isEmpty()) {

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

      // Use GlStateManager.enableNormalize() instead of GlStateManager.enableRescaleNormal() because it works with non-uniform scales.
      GlStateManager.enableNormalize();

      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

      RenderHelper.renderItemModelCustom(itemStack, model, transform, leftHanded, renderEffect);

      GlStateManager.cullFace(GlStateManager.CullFace.BACK);
      GlStateManager.disableNormalize();
      GlStateManager.disableBlend();
    }
  }

  public static void renderItemModelCustom(
      ItemStack itemStack,
      IBakedModel model,
      ItemCameraTransforms.TransformType transform,
      boolean leftHanded,
      boolean renderEffect
  ) {

    RenderHelper.renderItemModelCustom(itemStack, model, transform, leftHanded, renderEffect, 1);
  }

  public static void renderItemModelCustom(
      ItemStack itemStack,
      IBakedModel model,
      ItemCameraTransforms.TransformType transform,
      boolean leftHanded,
      boolean renderEffect,
      float alpha
  ) {

    if (!itemStack.isEmpty()) {

      TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();

      textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);

      GlStateManager.pushMatrix();

      model = ForgeHooksClient.handleCameraTransforms(model, transform, leftHanded);
      RenderHelper.renderItem(itemStack, model, renderEffect, alpha);

      GlStateManager.popMatrix();

      textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
    }
  }

  public static void renderItem(ItemStack stack, IBakedModel model, boolean renderEffect) {

    RenderHelper.renderItem(stack, model, renderEffect, 1);
  }

  public static void renderItem(ItemStack stack, IBakedModel model, boolean renderEffect, float alpha) {

    if (!stack.isEmpty()) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(-0.5F, -0.5F, -0.5F);

      if (model.isBuiltInRenderer()) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        stack.getItem().getTileEntityItemStackRenderer().renderByItem(stack);

      } else {
        RenderHelper.renderModel(model, -1, alpha, stack);

        if (renderEffect
            && stack.hasEffect()) {
          RenderHelper.renderEffect(model);
        }
      }

      GlStateManager.popMatrix();
    }
  }

  public static void renderModel(IBakedModel model, ItemStack stack) {

    RenderHelper.renderModel(model, -1, 1, stack);
  }

  public static void renderModel(IBakedModel model, int color) {

    RenderHelper.renderModel(model, color, 1, ItemStack.EMPTY);
  }

  public static void renderModel(IBakedModel model, int color, float alpha) {

    RenderHelper.renderModel(model, color, alpha, ItemStack.EMPTY);
  }

  public static void renderModel(IBakedModel model, int color, float alpha, ItemStack stack) {

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

    for (EnumFacing enumfacing : EnumFacing.values()) {
      List<BakedQuad> quads = model.getQuads(null, enumfacing, 0L);
      RenderHelper.renderQuads(bufferbuilder, quads, color, alpha, stack);
    }

    List<BakedQuad> quads = model.getQuads(null, null, 0L);
    RenderHelper.renderQuads(bufferbuilder, quads, color, alpha, stack);
    tessellator.draw();
  }

  public static void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color, float alpha, ItemStack stack) {

    boolean flag = color == -1 && !stack.isEmpty();
    int i = 0;

    for (int j = quads.size(); i < j; ++i) {
      BakedQuad bakedquad = quads.get(i);
      int k = color;

      if (flag && bakedquad.hasTintIndex()) {
        k = RenderHelper.getItemColors().colorMultiplier(stack, bakedquad.getTintIndex());

        if (EntityRenderer.anaglyphEnable) {
          k = TextureUtil.anaglyphColor(k);
        }

        k = k | -16777216;
      }

      int a = (int) (alpha * 255 + 0.5);
      int c = (k & 0x00FFFFFF) | ((a & 0xFF) << 24);

      net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(renderer, bakedquad, c);
    }
  }

  public static ItemColors getItemColors() {

    return Minecraft.getMinecraft().getItemColors();
  }

  public static void renderEffect(IBakedModel model) {

    TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();

    GlStateManager.depthMask(false);
    GlStateManager.depthFunc(514);
    GlStateManager.disableLighting();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
    textureManager.bindTexture(RES_ITEM_GLINT);
    GlStateManager.matrixMode(5890);
    GlStateManager.pushMatrix();
    GlStateManager.scale(8.0F, 8.0F, 8.0F);
    float f = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
    GlStateManager.translate(f, 0.0F, 0.0F);
    GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
    RenderHelper.renderModel(model, -8372020);
    GlStateManager.popMatrix();
    GlStateManager.pushMatrix();
    GlStateManager.scale(8.0F, 8.0F, 8.0F);
    float f1 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
    GlStateManager.translate(-f1, 0.0F, 0.0F);
    GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
    RenderHelper.renderModel(model, -8372020);
    GlStateManager.popMatrix();
    GlStateManager.matrixMode(5888);
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.enableLighting();
    GlStateManager.depthFunc(515);
    GlStateManager.depthMask(true);
    textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
  }

  private RenderHelper() {
    //
  }

}
