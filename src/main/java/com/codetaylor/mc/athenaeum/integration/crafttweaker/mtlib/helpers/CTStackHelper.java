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

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * https://github.com/jaredlll08/MTLib
 */
@SuppressWarnings("unused")
public class CTStackHelper {

  /**
   * Compares two ItemStack instances, trying to match the referenced item.
   */
  public static boolean areEqual(ItemStack stack1, ItemStack stack2) {

    if (stack1.isEmpty() || stack2.isEmpty()) {
      return false;
    } else {
      return stack1.isItemEqual(stack2);
    }
  }

  public static boolean areEqual(FluidStack stack1, FluidStack stack2) {

    if (stack1 == null || stack2 == null) {
      return false;
    }

    return stack1.isFluidEqual(stack2);
  }

  /**
   * Compares two ItemStack instances if they reference the same object or
   * are both null. If false, the method areEqual() is called to check if the
   * two instances reference the same item.
   */
  public static boolean areEqualOrNull(ItemStack stack1, ItemStack stack2) {
    // Check if they reference the same object or are are both null
    if (stack1 == stack2) {
      return true;
    }

    return (areEqual(stack1, stack2));
  }

  public static boolean areEqualOrNull(FluidStack stack1, FluidStack stack2) {
    // Check if they reference the same object or are are both null
    if (stack1 == stack2) {
      return true;
    }

    return (areEqual(stack1, stack2));
  }

  /**
   * Adds extra check to IIngredient matches() for Botania special flowers
   */
  public static boolean matches(IIngredient ingredient, IItemStack itemStack) {

    if (ingredient == null) {
      return false;
    }

    if (!ingredient.matches(itemStack)) {
      return false;
    }

    return true;
  }

  /**
   * Adds extra check to IIngredient matches() for Botania special flowers
   */
  public static boolean matches(IIngredient ingredient, IItemStack[] itemStack) {

    if (ingredient == null) {
      return false;
    }

    for (IItemStack stack : itemStack) {
      if (!ingredient.matches(stack)) {
        return false;
      }
    }

    return true;
  }
}
