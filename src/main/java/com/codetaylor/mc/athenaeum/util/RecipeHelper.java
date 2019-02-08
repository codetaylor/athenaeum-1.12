package com.codetaylor.mc.athenaeum.util;

import com.codetaylor.mc.athenaeum.recipe.IRecipeSingleFluidOutput;
import com.codetaylor.mc.athenaeum.recipe.IRecipeSingleOutput;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public final class RecipeHelper {

  /**
   * Removes all recipes from the given registry that have an output that
   * matches the given output.
   * <p>
   * This only works with recipes that implement {@link IRecipeSingleOutput}.
   *
   * @param registry registry
   * @param output   the output to match
   * @param <R>      recipe type
   * @return true if any recipes were removed
   */
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

  public static <R extends IForgeRegistryEntry<R> & IRecipeSingleFluidOutput> boolean removeRecipesByOutput(IForgeRegistryModifiable<R> registry, FluidStack output) {

    Iterator<R> iterator = registry.iterator();
    List<ResourceLocation> toRemove = new ArrayList<>(1);

    while (iterator.hasNext()) {
      R recipe = iterator.next();

      if (output.isFluidEqual(recipe.getOutput())) {
        toRemove.add(recipe.getRegistryName());
      }
    }

    for (ResourceLocation resourceLocation : toRemove) {
      registry.remove(resourceLocation);
    }

    return !toRemove.isEmpty();
  }

  /**
   * Returns true if the given itemStack has a furnace recipe with an output
   * item that extends {@link ItemFood}.
   *
   * @param furnaceInput the input item stack to check
   * @return true
   */
  public static boolean hasFurnaceFoodRecipe(ItemStack furnaceInput) {

    FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();
    ItemStack smeltingResult = furnaceRecipes.getSmeltingResult(furnaceInput);

    return !smeltingResult.isEmpty()
        && smeltingResult.getItem() instanceof ItemFood;
  }

  public static <E extends IForgeRegistryEntry<E>, F extends IForgeRegistryEntry<F>> void inherit(
      IForgeRegistryModifiable<E> inheritFrom,
      IForgeRegistryModifiable<F> inheritTo,
      Function<E, F> transformer
  ) {

    Collection<E> valuesCollection = inheritFrom.getValuesCollection();
    List<E> snapshot = new ArrayList<>(valuesCollection);

    for (E recipe : snapshot) {
      inheritTo.register(transformer.apply(recipe));
    }
  }

  private RecipeHelper() {
    //
  }
}
