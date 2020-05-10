package com.codetaylor.mc.athenaeum.module;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent;
import net.minecraftforge.fml.event.server.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ModuleEventRouter {

  private final List<ModuleBase> moduleList;
  private final Map<Class<? extends Event>, IFMLEventRoute> routes;

  public ModuleEventRouter(List<ModuleBase> moduleList) {

    this.moduleList = moduleList;
    this.routes = new HashMap<>();

    this.routes.put(
        FMLCommonSetupEvent.class,
        (IFMLEventRoute<FMLCommonSetupEvent>) (event) ->
            this.fireEvent(moduleBase -> moduleBase.onCommonSetupEvent(event))
    );
    this.routes.put(
        FMLLoadCompleteEvent.class,
        (IFMLEventRoute<FMLLoadCompleteEvent>) (event) ->
            this.fireEvent(moduleBase -> moduleBase.onLoadCompleteEvent(event))
    );
    this.routes.put(
        FMLServerAboutToStartEvent.class,
        (IFMLEventRoute<FMLServerAboutToStartEvent>) (event) ->
            this.fireEvent(moduleBase -> moduleBase.onServerAboutToStartEvent(event))
    );
    this.routes.put(
        FMLServerStartingEvent.class,
        (IFMLEventRoute<FMLServerStartingEvent>) (event) ->
            this.fireEvent(moduleBase -> moduleBase.onServerStartingEvent(event))
    );
    this.routes.put(
        FMLServerStartedEvent.class,
        (IFMLEventRoute<FMLServerStartedEvent>) (event) ->
            this.fireEvent(moduleBase -> moduleBase.onServerStartedEvent(event))
    );
    this.routes.put(
        FMLServerStoppingEvent.class,
        (IFMLEventRoute<FMLServerStoppingEvent>) (event) ->
            this.fireEvent(moduleBase -> moduleBase.onServerStoppingEvent(event))
    );
    this.routes.put(
        FMLServerStoppedEvent.class,
        (IFMLEventRoute<FMLServerStoppedEvent>) (event) ->
            this.fireEvent(moduleBase -> moduleBase.onServerStoppedEvent(event))
    );
  }

  /* package */ <E extends ModLifecycleEvent> void routeModLifecycleEvent(E event) {

    //noinspection unchecked
    IFMLEventRoute<E> route = this.routes.get(event.getClass());

    if (route == null) {
      throw new IllegalArgumentException("No route found for event: " + event.getClass());
    }

    route.routeEvent(event);
  }

  // --------------------------------------------------------------------------
  // - Registration Events
  // --------------------------------------------------------------------------

  @SubscribeEvent
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {

    this.fireEvent(module -> module.onRegisterBlockEvent(event));
  }

  @SubscribeEvent
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    this.fireEvent(module -> module.onRegisterItemEvent(event));
  }

  @SubscribeEvent
  public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {

    this.fireEvent(module -> module.onRegisterPotionEvent(event));
  }

  @SubscribeEvent
  public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {

    this.fireEvent(module -> module.onRegisterBiomeEvent(event));
  }

  @SubscribeEvent
  public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {

    this.fireEvent(module -> module.onRegisterSoundEvent(event));
  }

  @SubscribeEvent
  public void onRegisterEffectEvent(RegistryEvent.Register<Effect> event) {

    this.fireEvent(module -> module.onRegisterEffectEvent(event));
  }

  @SubscribeEvent
  public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {

    this.fireEvent(module -> module.onRegisterEnchantmentEvent(event));
  }

  @SubscribeEvent
  public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerProfession> event) {

    this.fireEvent(module -> module.onRegisterVillagerProfessionEvent(event));
  }

  @SubscribeEvent
  public void onRegisterEntityEvent(RegistryEvent.Register<EntityType<?>> event) {

    this.fireEvent(module -> module.onRegisterEntityEvent(event));
  }

  @SubscribeEvent
  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipeSerializer<?>> event) {

    this.fireEvent(module -> module.onRegisterRecipesEvent(event));
  }

  @SubscribeEvent
  public void onRegisterTileEntitiesEvent(RegistryEvent.Register<TileEntityType<?>> event) {

    this.fireEvent(module -> module.onRegisterTileEntitiesEvent(event));
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @SubscribeEvent
  @OnlyIn(Dist.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    this.fireEvent(module -> module.onClientRegisterModelsEvent(event));
  }

  // --------------------------------------------------------------------------
  // - Internal
  // --------------------------------------------------------------------------

  private void fireEvent(Consumer<ModuleBase> moduleConsumer) {

    for (ModuleBase module : this.moduleList) {
      moduleConsumer.accept(module);
    }
  }

}
