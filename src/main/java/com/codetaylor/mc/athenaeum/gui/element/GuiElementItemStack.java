package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.codetaylor.mc.athenaeum.gui.element.GuiElementBase;
import com.codetaylor.mc.athenaeum.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class GuiElementItemStack
    extends GuiElementBase {

  private final Supplier<ItemStack> itemStackSupplier;
  private final float alpha;

  public GuiElementItemStack(
      Supplier<ItemStack> itemStackSupplier,
      float alpha,
      GuiContainerBase guiBase,
      int elementX, int elementY
  ) {

    super(guiBase, elementX, elementY, 16, 16);
    this.itemStackSupplier = itemStackSupplier;
    this.alpha = alpha;
  }

  @Override
  public void drawBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    ItemStack stack = this.itemStackSupplier.get();

    if (!stack.isEmpty()) {
      RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
      net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();

      RenderHelper.renderItemModelIntoGUI(
          stack,
          this.elementXModifiedGet(),
          this.elementYModifiedGet(),
          renderItem.getItemModelWithOverrides(stack, null, null),
          true,
          renderItem.zLevel,
          this.alpha
      );

      GlStateManager.color(1, 1, 1, 1);
      net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    }
  }

  @Override
  public void drawForegroundLayer(int mouseX, int mouseY) {
    //
  }
}
