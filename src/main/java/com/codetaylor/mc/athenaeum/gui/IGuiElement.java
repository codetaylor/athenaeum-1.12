package com.codetaylor.mc.athenaeum.gui;

public interface IGuiElement {

  boolean elementIsVisible(int mouseX, int mouseY);

  boolean elementIsMouseInside(int mouseX, int mouseY);

}
