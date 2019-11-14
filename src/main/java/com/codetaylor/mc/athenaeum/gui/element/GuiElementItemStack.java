package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.codetaylor.mc.athenaeum.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class GuiElementItemStack
    extends GuiElementBase
    implements IGuiElementTooltipProvider {

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

  @Override
  public List<String> tooltipTextGet(List<String> tooltip) {

    ItemStack itemStack = this.itemStackSupplier.get();

    if (!itemStack.isEmpty()) {
      Minecraft minecraft = Minecraft.getMinecraft();
      ITooltipFlag.TooltipFlags tooltipFlag = minecraft.gameSettings.advancedItemTooltips
          ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL;
      List<String> itemStackTooltip = itemStack.getTooltip(minecraft.player, tooltipFlag);
      tooltip.addAll(itemStackTooltip);
    }

    return tooltip;
  }
}
