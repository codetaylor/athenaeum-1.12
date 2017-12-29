package com.codetaylor.mc.athenaeum.registry;

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

public interface IRegistryEventHandler {

  void onRegisterBlockEvent(RegistryEvent.Register<Block> event);

  void onRegisterItemEvent(RegistryEvent.Register<Item> event);

  void onRegisterPotionEvent(RegistryEvent.Register<Potion> event);

  void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event);

  void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event);

  void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event);

  void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event);

  void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event);

  void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event);

  void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event);

  void onRegisterTileEntitiesEvent();

  @SideOnly(Side.CLIENT)
  void onClientRegisterModelsEvent(ModelRegistryEvent event);

}
