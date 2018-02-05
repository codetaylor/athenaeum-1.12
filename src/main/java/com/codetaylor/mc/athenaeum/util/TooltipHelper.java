package com.codetaylor.mc.athenaeum.util;

import com.codetaylor.mc.athenaeum.ModAthenaeum;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public class TooltipHelper {

  public static final String TOOLTIP_COMMON_HOLD_SHIFT = "gui." + ModAthenaeum.MOD_ID + ".tooltip.common.hold_shift";

  public static String tooltipHoldShiftStringGet() {

    return TextFormatting.DARK_GRAY + I18n.format(
        TOOLTIP_COMMON_HOLD_SHIFT,
        TextFormatting.AQUA,
        TextFormatting.DARK_GRAY
    );
  }
}
