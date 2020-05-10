package com.codetaylor.mc.athenaeum.module;

import com.codetaylor.mc.athenaeum.network.IPacketRegistry;
import com.codetaylor.mc.athenaeum.network.IPacketService;
import com.codetaylor.mc.athenaeum.network.PacketRegistry;
import com.codetaylor.mc.athenaeum.network.PacketService;
import com.codetaylor.mc.athenaeum.network.tile.ITileDataService;
import com.codetaylor.mc.athenaeum.network.tile.TileDataServiceContainer;
import com.codetaylor.mc.athenaeum.registry.IRegistryEventHandler;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.registry.RegistryEventHandler;
import com.codetaylor.mc.athenaeum.registry.RegistryEventHandlerNoOp;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class ModuleBase
    implements Comparable<ModuleBase> {

  private final String name;
  private final int priority;
  private final String modId;
  private final Map<String, Set<String>> integrationPluginMap;
  private Registry registry;
  private IRegistryEventHandler registryEventHandler;

  /**
   * Stores a network wrapper for each mod id.
   */
  private static Map<String, PacketService> NETWORK_WRAPPER_MAP = new HashMap<>();

  /**
   * Stores a packet registry for each mod id.
   */
  private static Map<String, IPacketRegistry> PACKET_REGISTRY_MAP = new HashMap<>();

  private IPacketRegistry packetRegistry;
  private PacketService packetService;
  private ITileDataService tileDataService;
  private Path configurationDirectory;

  protected ModuleBase(int priority, String modId) {

    this.priority = priority;
    this.modId = modId;
    this.name = this.getClass().getSimpleName();
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

  public Path getConfigurationDirectory() {

    return this.configurationDirectory;
  }

  protected void setConfigurationDirectory(Path path) {

    this.configurationDirectory = path;
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

    if (this.registryEventHandler == RegistryEventHandlerNoOp.INSTANCE) {
      this.registryEventHandler = new RegistryEventHandler(this.registry);
    }
  }

  /**
   * Call this in the constructor to enable network functionality for this module.
   * <p>
   * This will create a new network wrapper and packet registry for this module's
   * mod id if they don't already exist. If they do already exist, the existing
   * network wrapper and packet registry will be used.
   *
   * @return a reference to the module's packet service
   */
  protected IPacketService enableNetwork(String protocolVersion) {

    if (this.packetService == null) {
      this.packetService = NETWORK_WRAPPER_MAP.computeIfAbsent(
          this.modId,
          modId -> {
            String channelName = modId; // can be exposed later
            ResourceLocation name = new ResourceLocation(modId, channelName);
            Supplier<String> protocolVersionSupplier = () -> protocolVersion;
            SimpleChannel simpleChannel = NetworkRegistry.newSimpleChannel(name, protocolVersionSupplier, protocolVersion::equals, protocolVersion::equals);
            return new PacketService(simpleChannel);
          }
      );
      this.packetRegistry = PACKET_REGISTRY_MAP.computeIfAbsent(
          this.modId,
          s -> new PacketRegistry(this.packetService)
      );
    }

    return this.packetService;
  }

  protected ITileDataService enableNetworkTileDataService(IPacketService packetService) {

    if (this.tileDataService == null) {
      this.tileDataService = TileDataServiceContainer.register(new ResourceLocation(this.modId, this.name), packetService);
    }

    return this.tileDataService;
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

  /**
   * This is called to allow the module to register blocks, items, etc.
   * <p>
   * Only called if {@link ModuleBase#enableAutoRegistry()} has been called in the module's constructor.
   *
   * @param registry the registry
   */
  public void onRegister(Registry registry) {
    // Override to use.
  }

  /**
   * This is called to allow the module to register client only things like models.
   * <p>
   * Only called if {@link ModuleBase#enableAutoRegistry()} has been called in the module's constructor.
   *
   * @param registry the registry
   */
  public void onClientRegister(Registry registry) {
    // Override to use.
  }

  /**
   * This is called to allow the module to register network packets.
   * <p>
   * Only called if {@link ModuleBase#enableNetwork(String)} has been called in the module's constructor.
   *
   * @param registry the packet registry
   */
  public void onNetworkRegister(IPacketRegistry registry) {
    // Override to use.
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

  public void onRegisterEffectEvent(RegistryEvent.Register<Effect> event) {

    this.registryEventHandler.onRegisterEffectEvent(event);
  }

  public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {

    this.registryEventHandler.onRegisterEnchantmentEvent(event);
  }

  public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerProfession> event) {

    this.registryEventHandler.onRegisterVillagerProfessionEvent(event);
  }

  public void onRegisterEntityEvent(RegistryEvent.Register<EntityType<?>> event) {

    this.registryEventHandler.onRegisterEntityEvent(event);
  }

  public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipeSerializer<?>> event) {

    this.registryEventHandler.onRegisterRecipesEvent(event);
  }

  public void onRegisterTileEntitiesEvent(RegistryEvent.Register<TileEntityType<?>> event) {

    this.registryEventHandler.onRegisterTileEntitiesEvent(event);
  }

  @OnlyIn(Dist.CLIENT)
  public void onClientRegisterModelsEvent(ModelRegistryEvent event) {

    this.registryEventHandler.onClientRegisterModelsEvent(event);
  }

  // --------------------------------------------------------------------------
  // - FML Lifecycle
  // --------------------------------------------------------------------------

  public void onCommonSetupEvent(FMLCommonSetupEvent event) {

    if (this.packetRegistry != null) {
      this.onNetworkRegister(this.packetRegistry);
    }

    Path gamePath = FMLPaths.GAMEDIR.get();
    Path configPath = gamePath.resolve(FMLConfig.defaultConfigPath());
    this.setConfigurationDirectory(configPath.resolve(this.modId));

    if (this.registry != null) {
      this.onRegister(this.registry);
    }
  }

  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {
    //
  }

  // --------------------------------------------------------------------------
  // - FML Lifecycle: Client
  // --------------------------------------------------------------------------

  @OnlyIn(Dist.CLIENT)
  public void onClientSetupEvent(FMLClientSetupEvent event) {

    if (this.registry != null) {
      this.onClientRegister(this.registry);
    }
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
