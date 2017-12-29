package com.codetaylor.mc.athenaeum.module;

import com.codetaylor.mc.athenaeum.registry.IRegistryEventHandler;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.registry.RegistryEventHandler;
import com.codetaylor.mc.athenaeum.registry.RegistryEventHandlerNoOp;
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
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ModuleBase
    implements Comparable<ModuleBase> {

  private final String name;
  private final int priority;
  private final Map<String, Set<String>> integrationPluginMap;
  private Registry registry;
  private IRegistryEventHandler registryEventHandler;

  protected ModuleBase(int priority) {

    this.name = this.getClass().getSimpleName();
    this.priority = priority;
    this.integrationPluginMap = new HashMap<>();
    this.registryEventHandler = RegistryEventHandlerNoOp.INSTANCE;
  }

  // --------------------------------------------------------------------------

  public String getName() {

    return this.name;
  }

  public int getPriority() {

    return this.priority;
  }

  protected void setRegistry(Registry registry) {

    if (this.registry != null) {
      throw new IllegalStateException("Trying to assign module registry after it has already been assigned");
    }

    this.registry = registry;
  }

  protected void enableAutoRegistry() {

    if (this.registry == null) {
      throw new IllegalStateException("Set module registry before enabling auto registry");
    }

    this.registryEventHandler = new RegistryEventHandler(this.registry);
  }

  // --------------------------------------------------------------------------
  // - Integration
  // --------------------------------------------------------------------------

  protected void registerIntegrationPlugin(String modId, String plugin) {

    Set<String> list = this.integrationPluginMap.computeIfAbsent(modId, k -> new HashSet<>());
    list.add(plugin);
  }

  /* package */ Map<String, Set<String>> getIntegrationPluginMap() {

    return this.integrationPluginMap;
  }

  // --------------------------------------------------------------------------
  // - Comparator
  // --------------------------------------------------------------------------

  public int compareTo(@Nonnull ModuleBase otherModule) {

    return Integer.compare(otherModule.getPriority(), this.priority);
  }

  // --------------------------------------------------------------------------
  // - Registration
  // --------------------------------------------------------------------------

  public void onRegister(Registry registry) {
    //
  }

  public void onClientRegister(Registry registry) {
    //
  }

  public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {

    this.registryEventHandler.onRegisterBlockEvent(event);
  }

  public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {

    this.registryEventHandler.onRegisterItemEvent(event);
  }

  public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {

    this.registryEventHandler.onRegisterPotionEvent(event);
  }

  public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {

    this.registryEventHandler.onRegisterBiomeEvent(event);
  }

  public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {

    this.registryEventHandler.onRegisterSoundEvent(event);
  }

  public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {

    this.registryEventHandler.onRegisterPotionTypeEvent(event);
  }

  public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {

    this.registryEventHandler.onRegisterEnchantmentEvent(event);
  }

  public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {

    this.registryEventHandler.onRegisterVillagerProfessionEvent(event);
  }

  public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {

    this.registryEventHandler.onRegisterEntityEvent(event);
  }

  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {

    this.registryEventHandler.onRegisterRecipesEvent(event);
  }

  public void onRegisterTileEntitiesEvent() {

    this.registryEventHandler.onRegisterTileEntitiesEvent();
  }

  @SideOnly(Side.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    this.registryEventHandler.onClientRegisterModelsEvent(event);
  }

  // --------------------------------------------------------------------------
  // - FML Lifecycle
  // --------------------------------------------------------------------------

  public void onConstructionEvent(FMLConstructionEvent event) {
    //
  }

  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    if (this.registry != null) {
      this.onRegister(this.registry);
    }
  }

  public void onInitializationEvent(FMLInitializationEvent event) {
    //
  }

  public void onPostInitializationEvent(FMLPostInitializationEvent event) {
    //
  }

  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {
    //
  }

  // --------------------------------------------------------------------------
  // - FML Lifecycle: Client
  // --------------------------------------------------------------------------

  @SideOnly(Side.CLIENT)
  public void onClientPreInitializationEvent(FMLPreInitializationEvent event) {

    if (this.registry != null) {
      this.onClientRegister(this.registry);
    }
  }

  @SideOnly(Side.CLIENT)
  public void onClientInitializationEvent(FMLInitializationEvent event) {
    //
  }

  @SideOnly(Side.CLIENT)
  public void onClientPostInitializationEvent(FMLPostInitializationEvent event) {
    //
  }

  // --------------------------------------------------------------------------
  // - Server
  // --------------------------------------------------------------------------

  public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {
    //
  }

  public void onServerStartingEvent(FMLServerStartingEvent event) {
    //
  }

  public void onServerStartedEvent(FMLServerStartedEvent event) {
    //
  }

  public void onServerStoppingEvent(FMLServerStoppingEvent event) {
    //
  }

  public void onServerStoppedEvent(FMLServerStoppedEvent event) {
    //
  }

}
