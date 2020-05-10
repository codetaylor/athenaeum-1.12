package com.codetaylor.mc.athenaeum.registry;

import com.codetaylor.mc.athenaeum.registry.strategy.IClientModelRegistrationStrategy;
import com.codetaylor.mc.athenaeum.registry.strategy.IForgeRegistryEventRegistrationStrategy;
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

public class RegistryEventHandler
    implements IRegistryEventHandler {

  private final Registry registry;

  public RegistryEventHandler(Registry registry) {

    this.registry = registry;
  }

  @Override
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {

    for (IForgeRegistryEventRegistrationStrategy<Block> strategy : this.registry.getBlockRegistrationStrategyList()) {
      strategy.register(event.getRegistry());
    }
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    for (IForgeRegistryEventRegistrationStrategy<Item> strategy : this.registry.getItemRegistrationStrategyList()) {
      strategy.register(event.getRegistry());
    }
  }

  @Override
  public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {

    for (IForgeRegistryEventRegistrationStrategy<Potion> strategy : this.registry.getPotionRegistrationStrategyList()) {
      strategy.register(event.getRegistry());
    }
  }

  @Override
  public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {

    for (IForgeRegistryEventRegistrationStrategy<Biome> strategy : this.registry.getBiomeRegistrationStrategyList()) {
      strategy.register(event.getRegistry());
    }
  }

  @Override
  public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
    // TODO: register sound event
  }

  @Override
  public void onRegisterEffectEvent(RegistryEvent.Register<Effect> event) {

    for (IForgeRegistryEventRegistrationStrategy<Effect> strategy : this.registry.getEffectRegistrationStrategyList()) {
      strategy.register(event.getRegistry());
    }
  }

  @Override
  public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
    // TODO: register enchantment event
  }

  @Override
  public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerProfession> event) {
    // TODO: register villager profession event
  }

  @Override
  public void onRegisterEntityEvent(RegistryEvent.Register<EntityType<?>> event) {

    for (IForgeRegistryEventRegistrationStrategy<EntityType<?>> strategy : this.registry.getEntityTypeRegistrationStrategyList()) {
      strategy.register(event.getRegistry());
    }
  }

  @Override
  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipeSerializer<?>> event) {
    // TODO: register recipes
  }

  @Override
  public void onRegisterTileEntitiesEvent(RegistryEvent.Register<TileEntityType<?>> event) {

    for (IForgeRegistryEventRegistrationStrategy<TileEntityType<?>> strategy : this.registry.getTileEntityRegistrationStrategyList()) {
      strategy.register(event.getRegistry());
    }
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    for (IClientModelRegistrationStrategy strategy : this.registry.getClientModelRegistrationStrategyList()) {
      strategy.register();
    }
  }

}
