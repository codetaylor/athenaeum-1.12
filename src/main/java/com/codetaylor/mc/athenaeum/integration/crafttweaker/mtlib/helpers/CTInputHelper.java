/*
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016 Jared
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package com.codetaylor.mc.athenaeum.integration.crafttweaker.mtlib.helpers;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientOr;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import gnu.trove.set.TCharSet;
import gnu.trove.set.hash.TCharHashSet;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://github.com/jaredlll08/MTLib
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class CTInputHelper {

  public static boolean isABlock(IItemStack block) {

    return CTInputHelper.isABlock(CTInputHelper.toStack(block));
  }

  public static <T> T[][] getMultiDimensionalArray(Class<T> clazz, T[] array, int height, int width) {

    T[][] multiDim = (T[][]) Array.newInstance(clazz, height, width);

    for (int y = 0; y < height; y++) {
      System.arraycopy(array, (y * width), multiDim[y], 0, width);
    }

    return multiDim;
  }

  public static IItemStack[] toStacks(IIngredient[] ingredients) {

    ArrayList<IItemStack> stacks = new ArrayList<>();

    for (IIngredient ingredient : ingredients) {
      stacks.addAll(ingredient.getItems());
    }

    return stacks.toArray(new IItemStack[stacks.size()]);
  }

  public static boolean isABlock(ItemStack block) {

    return block.getItem() instanceof ItemBlock;
  }

  public static ItemStack toStack(IItemStack iStack) {

    if (iStack == null) {
      return ItemStack.EMPTY;

    } else {
      Object internal = iStack.getInternal();

      if (!(internal instanceof ItemStack)) {
        CTLogHelper.logError("Not a valid item stack: " + iStack);
      }

      return (ItemStack) internal;
    }
  }

  public static IIngredient toIngredient(ItemStack stack) {

    return toIItemStack(stack);
  }

  public static IIngredient toIngredient(FluidStack stack) {

    if (stack == null) {
      return null;
    }

    return new MCLiquidStack(stack);
  }

  public static IItemStack toIItemStack(ItemStack stack) {

    if (stack.isEmpty()) {
      return null;
    }

    return new MCItemStack(stack);
  }

  public static ILiquidStack toILiquidStack(FluidStack stack) {

    if (stack == null) {
      return null;
    }

    return new MCLiquidStack(stack);
  }

  public static ItemStack[] toStacks(IItemStack[] iStack) {

    if (iStack == null) {
      return null;

    } else {
      ItemStack[] output = new ItemStack[iStack.length];

      for (int i = 0; i < iStack.length; i++) {
        output[i] = toStack(iStack[i]);
      }

      return output;
    }
  }

  public static Object toObject(IIngredient iStack) {

    if (iStack == null) {
      return null;

    } else {

      if (iStack instanceof IOreDictEntry) {
        return toString((IOreDictEntry) iStack);

      } else if (iStack instanceof IItemStack) {
        return toStack((IItemStack) iStack);

      } else if (iStack instanceof IngredientStack) {
        return iStack.getItems();

      } else {
        return null;
      }
    }
  }

  public static Object[] toObjects(IIngredient[] ingredient) {

    if (ingredient == null) {
      return null;

    } else {
      Object[] output = new Object[ingredient.length];

      for (int i = 0; i < ingredient.length; i++) {

        if (ingredient[i] != null) {
          output[i] = toObject(ingredient[i]);

        } else {
          output[i] = "";
        }
      }

      return output;
    }
  }

  public static String toString(IOreDictEntry entry) {

    return entry.getName();
  }

  public static FluidStack toFluid(ILiquidStack iStack) {

    if (iStack == null) {
      return null;

    } else {
      return FluidRegistry.getFluidStack(iStack.getName(), iStack.getAmount());
    }
  }

  public static Fluid getFluid(ILiquidStack iStack) {

    if (iStack == null) {
      return null;

    } else {
      return FluidRegistry.getFluid(iStack.getName());
    }

  }

  public static FluidStack[] toFluids(ILiquidStack[] iStack) {

    FluidStack[] stack = new FluidStack[iStack.length];

    for (int i = 0; i < stack.length; i++) {
      stack[i] = toFluid(iStack[i]);
    }
    return stack;
  }

  public static Object[] toShapedObjects(IIngredient[][] ingredients) {

    if (ingredients == null) {
      return null;

    } else {
      ArrayList<Object> prep = new ArrayList<>();
      TCharSet usedCharSet = new TCharHashSet();

      prep.add("abc");
      prep.add("def");
      prep.add("ghi");
      char[][] map = new char[][]{{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}};

      for (int x = 0; x < ingredients.length; x++) {

        if (ingredients[x] != null) {

          for (int y = 0; y < ingredients[x].length; y++) {

            if (ingredients[x][y] != null && x < map.length && y < map[x].length) {
              prep.add(map[x][y]);
              usedCharSet.add(map[x][y]);
              prep.add(CTInputHelper.toObject(ingredients[x][y]));
            }
          }
        }
      }

      for (int i = 0; i < 3; i++) {
        StringBuilder sb = new StringBuilder();

        if (prep.get(i) instanceof String) {
          String s = (String) prep.get(i);

          for (int j = 0; j < 3; j++) {
            char c = s.charAt(j);

            if (usedCharSet.contains(c)) {
              sb.append(c);

            } else {
              sb.append(" ");
            }
          }

          prep.set(i, sb.toString());
        }
      }

      return prep.toArray();
    }
  }

  public static <R> NonNullList<R> toNonNullList(R[] items) {

    NonNullList<R> nonNullList = NonNullList.create();
    Collections.addAll(nonNullList, items);

    return nonNullList;
  }

  /**
   * Converts an {@link IIngredient} to an {@link Ingredient}.
   *
   * @param ingredient {@link IIngredient} to convert
   * @return {@link Ingredient}
   * @author codetaylor
   */
  public static Ingredient toIngredient(@Nullable IIngredient ingredient) {

    if (ingredient == null) {
      return Ingredient.EMPTY;
    }

    return new IngredientWrapper(ingredient);
  }

  /**
   * Converts a two-dimensional {@link IIngredient} array into a two-dimensional {@link Ingredient} array.
   *
   * @param ingredients two-dimensional {@link IIngredient} array to convert
   * @return two-dimensional {@link Ingredient} array
   * @author codetaylor
   */
  public static Ingredient[][] toIngredientMatrix(IIngredient[][] ingredients) {

    Ingredient[][] result = new Ingredient[ingredients.length][];

    for (int row = 0; row < ingredients.length; row++) {
      result[row] = new Ingredient[ingredients[row].length];

      for (int col = 0; col < ingredients[row].length; col++) {
        result[row][col] = CTInputHelper.toIngredient(ingredients[row][col]);
      }
    }

    return result;
  }

  /**
   * Converts an {@link IIngredient} array into an {@link Ingredient} array.
   *
   * @param ingredients {@link IIngredient} array to convert
   * @return {@link Ingredient} array
   * @author codetaylor
   */
  public static Ingredient[] toIngredientArray(IIngredient[] ingredients) {

    Ingredient[] result = new Ingredient[ingredients.length];

    for (int i = 0; i < ingredients.length; i++) {
      result[i] = CTInputHelper.toIngredient(ingredients[i]);
    }

    return result;
  }

  public static List<ItemStack> getMatchingStacks(List<ItemStack> itemStackList, int amount, List<ItemStack> result) {

    NonNullList<ItemStack> internalList = NonNullList.create();

    for (ItemStack itemStack : itemStackList) {

      if (itemStack.isEmpty()) {
        continue;
      }

      if (itemStack.getMetadata() == OreDictionary.WILDCARD_VALUE) {

        itemStack.getItem().getSubItems(CreativeTabs.SEARCH, internalList);

      } else {
        internalList.add(itemStack);
      }
    }

    for (ItemStack itemStack : internalList) {
      itemStack.setCount(amount);
    }

    result.addAll(internalList);
    return result;
  }

  public static List<ItemStack> getMatchingStacks(IIngredient ingredient, List<ItemStack> result) {

    if (ingredient == null) {
      return result;
    }

    if (ingredient instanceof IOreDictEntry) {
      NonNullList<ItemStack> ores = OreDictionary.getOres(((IOreDictEntry) ingredient).getName());
      CTInputHelper.getMatchingStacks(ores, ingredient.getAmount(), result);

    } else if (ingredient instanceof IItemStack) {
      ItemStack itemStack = CTInputHelper.toStack((IItemStack) ingredient);
      itemStack.setCount(ingredient.getAmount());
      result.add(itemStack);

    } else {
      List<IItemStack> items = ingredient.getItems();

      for (IItemStack item : items) {
        ItemStack itemStack = CTInputHelper.toStack(item);
        CTInputHelper.getMatchingStacks(Collections.singletonList(itemStack), ingredient.getAmount(), result);
      }
    }

    return result;
  }

  /**
   * Wraps an {@link IIngredient} as an {@link Ingredient}.
   */
  public static class IngredientWrapper
      extends Ingredient {

    private IIngredient ingredient;

    public IngredientWrapper(@Nullable IIngredient ingredient) {

      this.ingredient = ingredient;
    }

    public int getAmount() {

      if (this.ingredient == null) {
        return 0;
      }

      return this.ingredient.getAmount();
    }

    @Nonnull
    @Override
    public ItemStack[] getMatchingStacks() {

      List<ItemStack> matchingStacks = CTInputHelper.getMatchingStacks(this.ingredient, new ArrayList<>());
      return matchingStacks.toArray(new ItemStack[matchingStacks.size()]);
    }

    @Override
    public boolean apply(@Nullable ItemStack itemStack) {

      if (this.ingredient == null) {
        return itemStack == null || itemStack.isEmpty();
      }

      if (itemStack == null || itemStack.isEmpty()) {
        return this.ingredient == null;
      }

      return this.ingredient.matches(CTInputHelper.toIItemStack(itemStack));
    }

  }

}
