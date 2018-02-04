package com.codetaylor.mc.athenaeum.gui.element;

import java.util.List;

public interface IGuiElementTooltipProvider extends
    IGuiElement {

  List<String> tooltipTextGet(List<String> tooltip);

}
