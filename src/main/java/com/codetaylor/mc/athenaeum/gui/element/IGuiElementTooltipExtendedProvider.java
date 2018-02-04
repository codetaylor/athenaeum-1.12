package com.codetaylor.mc.athenaeum.gui.element;

import java.util.List;

public interface IGuiElementTooltipExtendedProvider extends
    IGuiElementTooltipProvider {

  List<String> tooltipTextExtendedGet(List<String> tooltip);

}
