package com.codetaylor.mc.athenaeum.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.IngredientNBT;

public final class IngredientHelper {

  public static IngredientNBT fromStackWithNBT(ItemStack itemStack) {

    return new IngredientNBTExposed(itemStack);
  }

  public static class IngredientNBTExposed
      extends IngredientNBT {

    public IngredientNBTExposed(ItemStack stack) {

      super(stack);
    }
  }

  private IngredientHelper() {
    //
  }
}
