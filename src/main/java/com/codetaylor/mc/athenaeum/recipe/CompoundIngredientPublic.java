package com.codetaylor.mc.athenaeum.recipe;

import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;

import java.util.Collection;

public class CompoundIngredientPublic
    extends CompoundIngredient {

  public CompoundIngredientPublic(Collection<Ingredient> children) {

    super(children);
  }
}
