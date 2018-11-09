package com.codetaylor.mc.athenaeum.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.vecmath.Vector4f;
import java.util.ArrayList;
import java.util.List;

public final class ModelHelper {

  /**
   * Transforms a quad.
   * <p>
   * https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/client/model/GunModel.java
   *
   * @param quad      the quad
   * @param transform the transform
   * @return the transformed quad
   */
  public static BakedQuad transform(BakedQuad quad, final TRSRTransformation transform) {

    UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(DefaultVertexFormats.ITEM);

    final IVertexConsumer consumer = new VertexTransformer(builder) {

      @Override
      public void put(int element, float... data) {

        VertexFormatElement formatElement = DefaultVertexFormats.ITEM.getElement(element);

        switch (formatElement.getUsage()) {

          case POSITION: {
            float[] newData = new float[4];
            Vector4f vec = new Vector4f(data);
            transform.getMatrix().transform(vec);
            vec.get(newData);
            this.parent.put(element, newData);
            break;
          }

          default: {
            this.parent.put(element, data);
            break;
          }
        }
      }
    };

    quad.pipe(consumer);

    return builder.build();
  }

  public static IBakedModel getBakedModel(ItemStack stack) {

    Item item = stack.getItem();

    if (item instanceof ItemBlock) {
      Block block = ((ItemBlock) item).getBlock();
      IBlockState blockState = block.getStateFromMeta(stack.getMetadata());
      return ModelHelper.getBakedModel(blockState);

    } else {
      return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
    }
  }

  public static IBakedModel getBakedModel(IBlockState blockState) {

    return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(blockState);
  }

  public static List<BakedQuad> getQuads(ItemStack stack, long rand) {

    Item item = stack.getItem();

    if (item instanceof ItemBlock) {
      Block block = ((ItemBlock) item).getBlock();
      IBlockState blockState = block.getStateFromMeta(stack.getMetadata());
      IBakedModel bakedModel = ModelHelper.getBakedModel(blockState);
      List<BakedQuad> result = new ArrayList<>();

      for (EnumFacing facing : EnumFacing.values()) {
        result.addAll(bakedModel.getQuads(blockState, facing, rand));
      }

      return result;

    } else {
      IBakedModel bakedModel = ModelHelper.getBakedModel(stack);
      return bakedModel.getQuads(null, null, rand);
    }
  }

  private ModelHelper() {
    //
  }
}
