package com.codetaylor.mc.athenaeum.network.tile.client;

import com.codetaylor.mc.athenaeum.ModAthenaeumConfig;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@Mod.EventBusSubscriber(Side.CLIENT)
public class TileDataServiceOverlayRenderer {

  private static final TileDataServiceOverlayRenderer INSTANCE = new TileDataServiceOverlayRenderer();

  @SubscribeEvent
  public static void onRenderGameOverlayPostEvent(RenderGameOverlayEvent.Post event) {

    if (!ModAthenaeumConfig.TILE_DATA_SERVICE.ENABLED ||
        Minecraft.getMinecraft().isGamePaused()) {
      return;
    }

    RenderGameOverlayEvent.ElementType type = event.getType();

    if (type == RenderGameOverlayEvent.ElementType.ALL) {

      ScaledResolution resolution = event.getResolution();

      // --- Total ---

      INSTANCE.renderMonitor(TileDataServiceClientMonitor.TOTAL, resolution.getScaledWidth() / 2 - 32 - 128, 100, "Total Rx");

      // --- Position ---

      RayTraceResult traceResult = Minecraft.getMinecraft().objectMouseOver;

      if (traceResult != null
          && traceResult.typeOfHit == RayTraceResult.Type.BLOCK) {

        BlockPos blockPos = traceResult.getBlockPos();
        TileDataServiceClientMonitor monitor = TileDataServiceClientMonitor.findMonitorForPosition(blockPos);

        int x = resolution.getScaledWidth() / 2 - 32 + 128;
        int y = 100;

        if (monitor != null) {
          String title = "[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]";
          INSTANCE.renderMonitor(monitor, x, y, title);
        }

        TileDataTrackerUpdateMonitor trackerUpdateMonitor = TileDataServiceClientMonitor.getTrackerUpdateMonitor();
        Object2ObjectArrayMap<BlockPos, Object2IntArrayMap<Class>> updateMap = trackerUpdateMonitor.getPublicTrackerUpdateMap();
        Object2IntArrayMap<Class> map = updateMap.get(blockPos);

        if (map != null) {

          ObjectIterator<Object2IntMap.Entry<Class>> iterator = map.object2IntEntrySet().iterator();
          FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
          int index = 0;

          while (iterator.hasNext()) {
            Object2IntMap.Entry<Class> entry = iterator.next();

            Class dataClass = entry.getKey();
            int count = entry.getIntValue();

            fontRenderer.drawStringWithShadow(dataClass.getSimpleName() + " " + count, x + 64 /* TODO: cost */ + 5, y + 9 + index * 10, Color.WHITE.getRGB());
            index += 1;
          }
        }
      }

    }
  }

  public void renderMonitor(TileDataServiceClientMonitor monitor, int x, int y, String title) {

    int trackedIndex = ModAthenaeumConfig.TILE_DATA_SERVICE.TRACKING_INDEX;
    int totalWidth = 64; // TODO: const

    int size = monitor.size();

    if (size == 0) {
      return;
    }

    int max = 0;
    int min = Integer.MAX_VALUE;
    int minActual = Integer.MAX_VALUE;
    int total = 0;
    int tracked = 0;

    for (int i = 0; i < size; i++) {
      int count = monitor.get(i);
      total += count;

      if (i == trackedIndex) {
        tracked = count;
      }

      if (count > max) {
        max = count;
      }

      if (count > 0 && count < min) {
        min = count;
      }

      if (count < minActual) {
        minActual = count;
      }
    }

    if (min > max) {
      min = 0;
    }

    FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

    {
      int textWidth = fontRenderer.getStringWidth(title);
      fontRenderer.drawStringWithShadow(title, (float) (x - (textWidth / 2.0) + (totalWidth / 2.0)), y - 9, Color.WHITE.getRGB());
    }

    {
      String text = "§a" + min + " §e" + (int) (total / (float) size) + " §c" + max + " §9" + tracked;
      int textWidth = fontRenderer.getStringWidth(text);
      fontRenderer.drawStringWithShadow(text, (float) (x - (textWidth / 2.0) + (totalWidth / 2.0)), y, Color.WHITE.getRGB());
    }

    if (max == 0) {
      return; // prevent div by zero
    }

    // Only render if the first value
    if (minActual != max) {

      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder renderer = tessellator.getBuffer();

      GlStateManager.disableTexture2D();
      GlStateManager.enableAlpha();
      GlStateManager.enableBlend();
      renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

      int height = 1;
      y += 9;

      int avg = (int) (total / (float) size);

      bufferColoredQuad(renderer, x, y, totalWidth, size, 0, 0, 0, 0.75f);

      int trackedX = 0;
      int innerY = y - 1;
      for (int i = 0; i < size; i++) {

        float widthScalar = monitor.get(i) / (float) max;
        int width = (int) (totalWidth * widthScalar);
        innerY += height;

        if (i == trackedIndex) {
          trackedX = width;

        } else if (width == totalWidth) {
          bufferColoredQuad(renderer, x, innerY, width, height, 1, 0, 0, 0.75f);

        } else if (width < (min / (float) max) * totalWidth + 1) {
          bufferColoredQuad(renderer, x, innerY, width, height, 0, 1, 0, 0.75f);

        } else {
          bufferColoredQuad(renderer, x, innerY, width, height, 1, 1, 1, 0.5f);
        }
      }

      bufferColoredQuad(renderer, (int) (((avg / (float) max) * totalWidth) + x), y, 1, size, 1, 1, 0, 1);
      bufferColoredQuad(renderer, (int) (((min / (float) max) * totalWidth) + x), y, 1, size, 0, 1, 0, 1);
      bufferColoredQuad(renderer, totalWidth + x, y, 1, size, 1, 0, 0, 1);

      if (trackedX > 0) {
        bufferColoredQuad(renderer, x, trackedIndex + y, trackedX, 1, 85 / 255f, 85 / 255f, 1, 1);
        bufferColoredQuad(renderer, (trackedX + x), y, 1, size, 85 / 255f, 85 / 255f, 1, 1);
      }

      tessellator.draw();
      GlStateManager.enableTexture2D();
    }

  }

  private static void bufferColoredQuad(BufferBuilder renderer, int x, int y, float width, int height, float red, float green, float blue, float alpha) {

    renderer.pos((double) (x), (double) (y), 1.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos((double) (x), (double) (y + height), 1.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos((double) (x + width), (double) (y + height), 1.0D).color(red, green, blue, alpha).endVertex();
    renderer.pos((double) (x + width), (double) (y), 1.0D).color(red, green, blue, alpha).endVertex();
  }

}
