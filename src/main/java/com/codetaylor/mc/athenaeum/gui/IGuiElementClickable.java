package com.codetaylor.mc.athenaeum.gui;

/**
 * Created by codetaylor on 5/26/2017.
 */
public interface IGuiElementClickable {

  IGuiElementClickable NOOP = (mouseX, mouseY, mouseButton) -> {
    //
  };

  void elementClicked(int mouseX, int mouseY, int mouseButton);

}
