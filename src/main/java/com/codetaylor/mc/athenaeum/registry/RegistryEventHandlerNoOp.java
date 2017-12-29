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
  public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
    //
  }

  @Override
  public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
    //
  }

  @Override
  public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
    //
  }

  @Override
  public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {
    //
  }

  @Override
  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
    //
  }

  @Override
  public void onRegisterTileEntitiesEvent() {
    //
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {
    //
  }
}
