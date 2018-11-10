package com.codetaylor.mc.athenaeum.util;

import com.codetaylor.mc.athenaeum.recipe.IRecipeSingleOutput;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class RecipeHelper {

  public static <R extends IForgeRegistryEntry<R> & IRecipeSingleOutput> boolean removeRecipesByOutput(IForgeRegistryModifiable<R> registry, Ingredient output) {

    Iterator<R> iterator = registry.iterator();
    List<ResourceLocation> toRemove = new ArrayList<>(1);

    while (iterator.hasNext()) {
      R recipe = iterator.next();

      if (output.apply(recipe.getOutput())) {
        toRemove.add(recipe.getRegistryName());
      }
    }

    for (ResourceLocation resourceLocation : toRemove) {
      registry.remove(resourceLocation);
    }

    return !toRemove.isEmpty();
  }

  private RecipeHelper() {
    //
  }
}
