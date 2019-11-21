package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
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

  protected abstract TextureAtlasSprite textureAtlasSpriteGet();
}