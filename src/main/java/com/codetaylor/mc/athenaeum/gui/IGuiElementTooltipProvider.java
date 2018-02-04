package com.codetaylor.mc.athenaeum.gui;

import java.util.List;

public interface IGuiElementTooltipProvider extends
    IGuiElement {

  List<String> tooltipTextGet(List<String> tooltip);

}
