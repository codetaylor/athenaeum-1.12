package com.codetaylor.mc.athenaeum.gui;

import java.util.List;

public interface IGuiElementTooltipExtendedProvider extends
    IGuiElementTooltipProvider {

  List<String> tooltipTextExtendedGet(List<String> tooltip);

}
