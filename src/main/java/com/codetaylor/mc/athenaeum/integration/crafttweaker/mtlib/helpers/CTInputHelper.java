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

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    //noinspection unchecked
    T[][] multiDim = (T[][]) Array.newInstance(clazz, height, width);

    for (int y = 0; y < height; y++) {
      System.arraycopy(array, (y * width), multiDim[y], 0, width);
    }

    return multiDim;
  }

  public static IItemStack[] toStacks(IIngredient[] ingredients) {

    ArrayList<IItemStack> stacks = new ArrayList<>();

    for (IIngredient ingredient : ingredients) {
      stacks.addAll(Arrays.asList(ingredient.getItems()));
    }

    return stacks.toArray(new IItemStack[0]);
  }

  public static boolean isABlock(ItemStack block) {

    return block.getItem() instanceof BlockItem;
  }

  public static ItemStack toStack(IItemStack iStack) {

    if (iStack == null) {
      return ItemStack.EMPTY;

    } else {
      ItemStack internal = iStack.getInternal();

      if (internal == null) {
        CraftTweakerAPI.logError("Not a valid item stack: " + iStack);
      }

      return internal;
    }
  }

  public static IIngredient toIngredient(ItemStack stack) {

    return toIItemStack(stack);
  }

  public static IItemStack toIItemStack(ItemStack stack) {

    if (stack.isEmpty()) {
      return null;
    }

    return new MCItemStack(stack);
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

      internalList.add(itemStack);
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

    if (ingredient instanceof IItemStack) {
      ItemStack itemStack = CTInputHelper.toStack((IItemStack) ingredient);
      itemStack.setCount(((IItemStack) ingredient).getAmount());
      result.add(itemStack);

    } else {
      IItemStack[] items = ingredient.getItems();

      for (IItemStack item : items) {
        ItemStack itemStack = CTInputHelper.toStack(item);
        CTInputHelper.getMatchingStacks(Collections.singletonList(itemStack), 1, result);
      }
    }

    return result;
  }

  /**
   * Wraps an {@link IIngredient} as an {@link Ingredient}.
   */
  public static class IngredientWrapper
      extends Ingredient {

    // We're intentionally only reading from this field.
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private static final IItemStack[] ITEM_STACKS_EMPTY = new IItemStack[0];

    private IIngredient ingredient;

    public IngredientWrapper(@Nullable IIngredient ingredient) {

      super(Arrays.stream(ingredient != null ? ingredient.getItems() : ITEM_STACKS_EMPTY)
          .map(IItemStack::getInternal)
          .map((itemStack) -> new SingleItemList(itemStack.copy())));
      this.ingredient = ingredient;
    }

    @Nonnull
    @Override
    public ItemStack[] getMatchingStacks() {

      List<ItemStack> matchingStacks = CTInputHelper.getMatchingStacks(this.ingredient, new ArrayList<>());
      return matchingStacks.toArray(new ItemStack[0]);
    }

    @Override
    public boolean test(@Nullable ItemStack itemStack) {

      if (this.ingredient == null) {
        return itemStack == null || itemStack.isEmpty();
      }

      if (itemStack == null || itemStack.isEmpty()) {
        return false;
      }

      return this.ingredient.matches(CTInputHelper.toIItemStack(itemStack));
    }

  }

}
