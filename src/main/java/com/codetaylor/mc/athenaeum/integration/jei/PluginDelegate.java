package com.codetaylor.mc.athenaeum.integration.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class PluginDelegate
    implements IModPlugin {

  private static final List<IModPlugin> PLUGIN_LIST;

  static {
    PLUGIN_LIST = new ArrayList<>();
  }

  public static void registerPlugin(IModPlugin plugin) {

    PLUGIN_LIST.add(plugin);
  }

  public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerItemSubtypes(subtypeRegistry);
    }
  }

  public void registerIngredients(IModIngredientRegistration registry) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerIngredients(registry);
    }
  }

  public void registerCategories(IRecipeCategoryRegistration registry) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerCategories(registry);
    }
  }

  public void register(IModRegistry registry) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.register(registry);
    }
  }

  public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.onRuntimeAvailable(jeiRuntime);
    }
  }
}
