package com.codetaylor.mc.athenaeum.gui;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

/**
 * Created by codetaylor on 5/27/2017.
 */
public abstract class GuiElementTiledTextureAtlasSpriteBase
    extends GuiElementBase {

  public GuiElementTiledTextureAtlasSpriteBase(
      GuiContainerBase guiBase,
      int elementX,
      int elementY,
      int elementWidth,
      int elementHeight
  ) {

    super(guiBase, elementX, elementY, elementWidth, elementHeight);
  }

  @Override
  public void drawBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    this.textureBind(TextureMap.LOCATION_BLOCKS_TEXTURE);

    this.guiBase.drawScaledTexturedModalRectFromIconAnchorBottomLeft(
        this.elementXModifiedGet(),
        this.elementYModifiedGet(),
        this.textureAtlasSpriteGet(),
        this.elementWidthModifiedGet(),
        this.elementHeightModifiedGet()
    );
  }

  @Override
  public void drawForegroundLayer(int mouseX, int mouseY) {
    //
  }

  @Override
  protected int elementHeightModifiedGet() {

    int elementHeightModified = (int) (this.scalarPercentageGet() * this.elementHeight);
    int min = Math.min(elementHeightModified, this.elementHeight);
    return Math.max(0, min);
  }

  @Override
  protected int elementYModifiedGet() {

    int elementHeightModified = (int) (this.scalarPercentageGet() * this.elementHeight);
    int min = Math.min(elementHeightModified, this.elementHeight);
    return this.elementHeight - Math.max(0, min) + super.elementYModifiedGet();
  }

  protected abstract float scalarPercentageGet();

  protected abstract TextureAtlasSprite textureAtlasSpriteGet();

}
