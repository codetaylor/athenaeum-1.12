package com.codetaylor.mc.athenaeum.registry;

import com.codetaylor.mc.athenaeum.registry.strategy.IClientModelRegistrationStrategy;
import com.codetaylor.mc.athenaeum.registry.strategy.IForgeRegistryEventRegistrationStrategy;
import com.codetaylor.mc.athenaeum.registry.strategy.ITileEntityRegistrationStrategy;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
  public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {

    for (IForgeRegistryEventRegistrationStrategy<PotionType> strategy : this.registry.getPotionTypeRegistrationStrategyList()) {
      strategy.register(event.getRegistry());
    }
  }

  @Override
  public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
    // TODO: register enchantment event
  }

  @Override
  public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
    // TODO: register villager profession event
  }

  @Override
  public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {

    for (IForgeRegistryEventRegistrationStrategy<EntityEntry> strategy : this.registry.getEntityEntryRegistrationStrategyList()) {
      strategy.register(event.getRegistry());
    }
  }

  @Override
  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
    // TODO: register recipes
  }

  @Override
  public void onRegisterTileEntitiesEvent() {

    for (ITileEntityRegistrationStrategy strategy : this.registry.getTileEntityRegistrationStrategyList()) {
      strategy.register();
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    for (IClientModelRegistrationStrategy strategy : this.registry.getClientModelRegistrationStrategyList()) {
      strategy.register();
    }
  }

}
