package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.codetaylor.mc.athenaeum.gui.Texture;
import net.minecraft.client.gui.Gui;

/**
 * Created by codetaylor on 5/25/2017.
 */
public class GuiElementTextureRectangle
    extends GuiElementTextureBase {

  public GuiElementTextureRectangle(
      GuiContainerBase guiBase,
      Texture texture,
      int elementX,
      int elementY,
      int elementWidth,
      int elementHeight
  ) {

    this(guiBase, new Texture[]{texture}, elementX, elementY, elementWidth, elementHeight);
  }

  public GuiElementTextureRectangle(
      GuiContainerBase guiBase,
      Texture[] textures,
      int elementX,
      int elementY,
      int elementWidth,
      int elementHeight
  ) {

    super(guiBase, elementX, elementY, elementWidth, elementHeight, textures);
  }

  @Override
  public void drawBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    Texture texture = this.textureGet(mouseX, mouseY);

    this.textureBind(texture);
    this.elementDraw(texture);

  }

  protected void elementDraw(Texture texture) {

    Gui.drawModalRectWithCustomSizedTexture(
        this.elementXModifiedGet(),
        this.elementYModifiedGet(),
        this.texturePositionXModifiedGet(texture),
        this.texturePositionYModifiedGet(texture),
        this.elementWidthModifiedGet(),
        this.elementHeightModifiedGet(),
        texture.getWidth(),
        texture.getHeight()
    );
  }

  @Override
  public void drawForegroundLayer(int mouseX, int mouseY) {
    //
  }

}
