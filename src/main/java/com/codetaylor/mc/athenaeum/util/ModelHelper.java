package com.codetaylor.mc.athenaeum.util;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.vecmath.Vector4f;

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

  private ModelHelper() {
    //
  }
}
