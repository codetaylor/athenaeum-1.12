package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.codetaylor.mc.athenaeum.gui.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public abstract class GuiElementTextureButtonBase
    extends GuiElementTextureRectangle
    implements IGuiElementClickable {

  private static final int TEXTURE_BASE_INDEX = 0;
  private static final int TEXTURE_HOVERED_INDEX = 1;

  public GuiElementTextureButtonBase(GuiContainerBase guiBase, Texture[] textures, int elementX, int elementY, int elementWidth, int elementHeight) {

    super(guiBase, textures, elementX, elementY, elementWidth, elementHeight);
  }

  @Override
  protected int textureIndexGet(int mouseX, int mouseY) {

    if (this.elementIsMouseInside(mouseX, mouseY)) {
      return TEXTURE_HOVERED_INDEX;
    }

    return TEXTURE_BASE_INDEX;
  }

  @Override
  public void elementClicked(int mouseX, int mouseY, int mouseButton) {

    Minecraft.getMinecraft()
        .getSoundHandler()
        .playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1));
  }
}
