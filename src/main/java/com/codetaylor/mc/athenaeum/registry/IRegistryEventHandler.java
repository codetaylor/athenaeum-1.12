package com.codetaylor.mc.athenaeum.registry;

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

public interface IRegistryEventHandler {

  void onRegisterBlockEvent(RegistryEvent.Register<Block> event);

  void onRegisterItemEvent(RegistryEvent.Register<Item> event);

  void onRegisterPotionEvent(RegistryEvent.Register<Potion> event);

  void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event);

  void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event);

  void onRegisterEffectEvent(RegistryEvent.Register<Effect> event);

  void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event);

  void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerProfession> event);

  void onRegisterEntityEvent(RegistryEvent.Register<EntityType<?>> event);

  void onRegisterRecipesEvent(RegistryEvent.Register<IRecipeSerializer<?>> event);

  void onRegisterTileEntitiesEvent(RegistryEvent.Register<TileEntityType<?>> event);

  @OnlyIn(Dist.CLIENT)
  void onClientRegisterModelsEvent(ModelRegistryEvent event);

}
