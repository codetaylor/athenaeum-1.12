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

public class RegistryEventHandlerNoOp
    implements IRegistryEventHandler {

  public static final IRegistryEventHandler INSTANCE = new RegistryEventHandlerNoOp();

  @Override
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {
    //
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {
    //
  }

  @Override
  public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
    //
  }

  @Override
  public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
    //
  }

  @Override
  public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
    //
  }

  @Override
  public void onRegisterEffectEvent(RegistryEvent.Register<Effect> event) {
    //
  }

  @Override
  public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
    //
  }

  @Override
  public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerProfession> event) {
    //
  }

  @Override
  public void onRegisterEntityEvent(RegistryEvent.Register<EntityType<?>> event) {
    //
  }

  @Override
  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipeSerializer<?>> event) {
    //
  }

  @Override
  public void onRegisterTileEntitiesEvent(RegistryEvent.Register<TileEntityType<?>> event) {
    //
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {
    //
  }
}
