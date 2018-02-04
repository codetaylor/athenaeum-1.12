package com.codetaylor.mc.athenaeum.gui.element;

/**
 * Created by codetaylor on 5/26/2017.
 */
public interface IGuiElementClickable {

  IGuiElementClickable NOOP = new IGuiElementClickable() {
    //
  };

  /**
   * Called when the mouse is clicked, regardless if it is inside the element or not.
   *
   * @param mouseX      mouse x
   * @param mouseY      mouse y
   * @param mouseButton mouse button index
   */
  default void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    //
  }

  /**
   * Called when the mouse is clicked inside the element.
   *
   * @param mouseX      mouse x
   * @param mouseY      mouse y
   * @param mouseButton mouse button index
   */
  default void elementClicked(int mouseX, int mouseY, int mouseButton) {
    //
  }

}
