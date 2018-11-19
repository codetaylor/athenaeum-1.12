package com.codetaylor.mc.athenaeum.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class OreDictHelper {

  public static boolean contains(String oreDict, ItemStack itemStack) {

    int logWood = OreDictionary.getOreID(oreDict);
    int[] oreIDs = OreDictionary.getOreIDs(itemStack);

    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < oreIDs.length; i++) {

      if (oreIDs[i] == logWood) {
        return true;
      }
    }

    return false;
  }

  private OreDictHelper() {
    //
  }

}
