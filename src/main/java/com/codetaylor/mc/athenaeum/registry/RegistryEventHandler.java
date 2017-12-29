package com.codetaylor.mc.athenaeum.registry;

import com.codetaylor.mc.athenaeum.registry.strategy.IModelRegistrationStrategy;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryEventHandler
    implements IRegistryEventHandler {

  private final Registry registry;

  public RegistryEventHandler(Registry registry) {

    this.registry = registry;
  }

  @Override
  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {

    IForgeRegistry<Block> registry = event.getRegistry();

    for (Block block : this.registry.getBlockList()) {
      registry.register(block);
    }
  }

  @Override
  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    IForgeRegistry<Item> registry = event.getRegistry();

    for (Item item : this.registry.getItemList()) {
      registry.register(item);
    }
  }

  @Override
  public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
    // TODO
  }

  @Override
  public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
    // TODO
  }

  @Override
  public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
    // TODO
  }

  @Override
  public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
    // TODO
  }

  @Override
  public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
    // TODO
  }

  @Override
  public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
    // TODO
  }

  @Override
  public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {
    // TODO
  }

  @Override
  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
    // TODO
  }

  @Override
  public void onRegisterTileEntitiesEvent() {

    for (Class<? extends TileEntity> tileEntityClass : this.registry.getTileEntityClassList()) {
      GameRegistry.registerTileEntity(
          tileEntityClass,
          this.registry.getModId() + ".tile." + tileEntityClass.getSimpleName()
      );
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    for (IModelRegistrationStrategy strategy : this.registry.getModelRegistrationStrategyList()) {
      strategy.register();
    }
  }

}
