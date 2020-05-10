package com.codetaylor.mc.athenaeum.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class PluginDelegate
    implements IModPlugin {

  private static final List<IModPlugin> PLUGIN_LIST;

  static {
    PLUGIN_LIST = new ArrayList<>();
  }

  public static void registerPlugin(IModPlugin plugin) {

    PLUGIN_LIST.add(plugin);
  }

  @Override
  public ResourceLocation getPluginUid() {

    return null;
  }

  @Override
  public void registerItemSubtypes(ISubtypeRegistration registration) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerItemSubtypes(registration);
    }
  }

  @Override
  public void registerIngredients(IModIngredientRegistration registration) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerIngredients(registration);
    }
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerCategories(registration);
    }
  }

  @Override
  public void registerAdvanced(IAdvancedRegistration registration) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerAdvanced(registration);
    }
  }

  @Override
  public void registerGuiHandlers(IGuiHandlerRegistration registration) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerGuiHandlers(registration);
    }
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerRecipeCatalysts(registration);
    }
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerRecipes(registration);
    }
  }

  @Override
  public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerRecipeTransferHandlers(registration);
    }
  }

  @Override
  public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.registerVanillaCategoryExtensions(registration);
    }
  }

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    for (IModPlugin plugin : PLUGIN_LIST) {
      plugin.onRuntimeAvailable(jeiRuntime);
    }
  }
}
