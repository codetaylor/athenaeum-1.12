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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public final class RecipeHelper {

  public static <R extends IForgeRegistryEntry<R>> void removeAllRecipes(IForgeRegistryModifiable<R> registry) {

    registry.clear();
  }

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
      String parentPath,
      IForgeRegistryModifiable<E> inheritFrom,
      IForgeRegistryModifiable<F> inheritTo,
      Function<E, F> transformer
  ) {

    RecipeHelper.inherit(parentPath, inheritFrom, inheritTo, transformer, null);
  }

  public static <E extends IForgeRegistryEntry<E>, F extends IForgeRegistryEntry<F>> void inherit(
      String parentPath,
      IForgeRegistryModifiable<E> inheritFrom,
      IForgeRegistryModifiable<F> inheritTo,
      Function<E, F> transformer,
      @Nullable Predicate<E> filter
  ) {

    Collection<E> valuesCollection = inheritFrom.getValuesCollection();
    List<E> snapshot = new ArrayList<>(valuesCollection);

    for (E recipe : snapshot) {

      if (filter != null && !filter.test(recipe)) {
        continue;
      }

      RecipeHelper.inherit(parentPath, inheritTo, transformer, recipe);
    }
  }

  public static <E extends IForgeRegistryEntry<E>, F extends IForgeRegistryEntry<F>> F inherit(
      String parentPath,
      IForgeRegistryModifiable<F> inheritTo,
      Function<E, F> transformer,
      E recipe
  ) {

    ResourceLocation registryName = recipe.getRegistryName();

    if (registryName == null) {
      throw new RuntimeException("Null registry name");
    }

    ResourceLocation resourceLocation = new ResourceLocation(registryName.getResourceDomain(), parentPath + "/" + registryName.getResourcePath());
    F result = transformer.apply(recipe);
    inheritTo.register(result.setRegistryName(resourceLocation));
    return result;
  }

  private RecipeHelper() {
    //
  }
}
