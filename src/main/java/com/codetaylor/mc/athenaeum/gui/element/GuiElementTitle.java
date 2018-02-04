package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;

public class GuiElementTitle
    extends GuiElementBase {

  private final String titleKey;

  public GuiElementTitle(
      GuiContainerBase guiBase,
      String titleKey,
      int elementX,
      int elementY
  ) {

    // element width and height don't matter
    super(guiBase, elementX, elementY, 0, 0);
    this.titleKey = titleKey;
  }

  @Override
  public void drawBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    //
  }

  @Override
  public void drawForegroundLayer(int mouseX, int mouseY) {

    this.guiBase.drawString(this.titleKey, this.elementX, this.elementY);
  }
}
