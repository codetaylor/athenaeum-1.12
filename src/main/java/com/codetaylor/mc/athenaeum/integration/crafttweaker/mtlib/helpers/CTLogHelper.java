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

import crafttweaker.CraftTweakerAPI;
import crafttweaker.CrafttweakerImplementationAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.Arrays;
import java.util.List;

/**
 * https://github.com/jaredlll08/MTLib
 */
public class CTLogHelper {

  public static void logPrinted(IPlayer player) {

    if (player != null) {
      player.sendChat(CrafttweakerImplementationAPI.platform.getMessage(
          "List generated; see minetweaker.log in your minecraft dir"));
    }
  }

  public static void log(IPlayer player, String message) {

    if (player != null) {
      player.sendChat(CrafttweakerImplementationAPI.platform.getMessage(message));
    }
  }

  public static void print(String string) {

    System.out.println(string);
    CraftTweakerAPI.logCommand(string);
  }

  public static void logError(String message) {

    CraftTweakerAPI.logError(message);
  }

  public static void logError(String message, Throwable exception) {

    CraftTweakerAPI.logError(message, exception);
  }

  /**
   * Logs an error with the ZenScript filename and line number if called from a
   * method annotated with @{@link stanhebben.zenscript.annotations.ZenMethod}.
   *
   * @param message the message
   * @author codetaylor
   */
  public static void logErrorFromZenMethod(String message) {

    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

    for (StackTraceElement element : stackTrace) {
      String methodName = element.getMethodName();

      if ("__script__".equals(methodName)) {
        CraftTweakerAPI.logError(message + " (" + element.getFileName() + ":" + element.getLineNumber() + ")");
        break;
      }
    }
  }

  public static void logWarning(String message) {

    CraftTweakerAPI.logWarning(message);
  }

  public static void logInfo(String message) {

    CraftTweakerAPI.logInfo(message);
  }

  /**
   * Returns a string representation of the item which can also be used in scripts
   */
  @SuppressWarnings("rawtypes")
  public static String getStackDescription(Object object) {

    if (object instanceof IIngredient) {
      return getStackDescription((IIngredient) object);
    } else if (object instanceof ItemStack) {
      return new MCItemStack((ItemStack) object).toString();
    } else if (object instanceof FluidStack) {
      return getStackDescription((FluidStack) object);
    } else if (object instanceof Block) {
      return new MCItemStack(new ItemStack((Block) object, 1, 0)).toString();
    } else if (object instanceof String) {
      // Check if string specifies an oredict entry
      List<ItemStack> ores = OreDictionary.getOres((String) object);

      if (!ores.isEmpty()) {
        return "<ore:" + (String) object + ">";
      } else {
        return "\"" + (String) object + "\"";
      }
    } else if (object instanceof List) {
      return getListDescription((List) object);
    } else if (object instanceof Object[]) {
      return getListDescription(Arrays.asList((Object[]) object));
    } else if (object != null) {
      return "\"" + object.toString() + "\"";
    } else if (object instanceof Ingredient && !((Ingredient) object).apply(ItemStack.EMPTY) && ((Ingredient) object).getMatchingStacks().length > 0) {
      return getStackDescription(((Ingredient) object).getMatchingStacks()[0]);
    } else {
      return "null";
    }
  }

  public static String getStackDescription(IIngredient stack) {

    Object internalObject = stack.getInternal();

    if (internalObject instanceof ItemStack) {
      return getStackDescription((ItemStack) internalObject);
    } else if (internalObject instanceof FluidStack) {
      return getStackDescription((FluidStack) internalObject);
    } else if (internalObject instanceof IOreDictEntry) {
      return getStackDescription(((IOreDictEntry) internalObject).getName());
    } else {
      return "null";
    }
  }

  public static String getStackDescription(FluidStack stack) {

    StringBuilder sb = new StringBuilder();

    sb.append("<liquid:").append(stack.getFluid().getName()).append('>');

    if (stack.amount > 1) {
      sb.append(" * ").append(stack.amount);
    }

    return sb.toString();
  }

  public static String getListDescription(List<?> objects) {

    StringBuilder sb = new StringBuilder();

    if (objects.isEmpty()) {
      sb.append("[]");
    } else {
      sb.append('[');
      for (Object object : objects) {
        if (object instanceof List) {
          sb.append(getListDescription((List) object)).append(", ");
        } else if (object instanceof Object[]) {
          sb.append(getListDescription(Arrays.asList((Object[]) object))).append(", ");
        } else {
          sb.append(getStackDescription(object)).append(", ");
        }
      }
      sb.setLength(sb.length() - 2);
      sb.append(']');
    }

    return sb.toString();
  }

  public static String getCraftingDescription(IRecipe recipe) {

    if (recipe instanceof ShapelessOreRecipe) {
      return CTLogHelper.getCraftingDescription((ShapelessOreRecipe) recipe);
    } else if (recipe instanceof ShapedOreRecipe) {
      return CTLogHelper.getCraftingDescription((ShapedOreRecipe) recipe);
    } else if (recipe instanceof ShapelessRecipes) {
      return CTLogHelper.getCraftingDescription((ShapelessRecipes) recipe);
    } else if (recipe instanceof ShapedRecipes) {
      return CTLogHelper.getCraftingDescription((ShapedRecipes) recipe);
    }

    return recipe.toString();
  }

  public static String getCraftingDescription(ShapelessOreRecipe recipe) {

    return getListDescription(recipe.getIngredients());
  }

  public static String getCraftingDescription(ShapelessRecipes recipe) {

    return getListDescription(recipe.recipeItems);
  }

  public static String getCraftingDescription(ShapedOreRecipe recipe) {

    int height = recipe.getWidth();
    int width = recipe.getHeight();

    Ingredient[][] recipes = CTInputHelper.getMultiDimensionalArray(
        Ingredient.class,
        recipe.getIngredients().toArray(new Ingredient[0]),
        height,
        width
    );
    return getListDescription(Arrays.asList(recipes));
  }

  public static String getCraftingDescription(ShapedRecipes recipe) {

    Ingredient[][] recipes = CTInputHelper.getMultiDimensionalArray(
        Ingredient.class,
        recipe.getIngredients().toArray(new Ingredient[0]),
        recipe.recipeHeight,
        recipe.recipeWidth
    );
    return getListDescription(Arrays.asList(recipes));
  }
}
