package com.codetaylor.mc.athenaeum.registry;

import com.codetaylor.mc.athenaeum.registry.strategy.IClientModelRegistrationStrategy;
import com.codetaylor.mc.athenaeum.registry.strategy.IForgeRegistryEventRegistrationStrategy;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue", "unused"})
public class Registry {

  private final String modId;
  private final ItemGroup itemGroup;

  private final List<IForgeRegistryEventRegistrationStrategy<Block>> blockRegistrationStrategyList;
  private final List<IForgeRegistryEventRegistrationStrategy<Item>> itemRegistrationStrategyList;
  private final List<IForgeRegistryEventRegistrationStrategy<TileEntityType<?>>> tileEntityRegistrationStrategyList;
  private final List<IClientModelRegistrationStrategy> clientModelRegistrationStrategyList;
  private final List<IForgeRegistryEventRegistrationStrategy<Biome>> biomeRegistrationStrategyList;
  private final List<IForgeRegistryEventRegistrationStrategy<Potion>> potionRegistrationStrategyList;
  private final List<IForgeRegistryEventRegistrationStrategy<Effect>> effectRegistrationStrategyList;
  private final List<IForgeRegistryEventRegistrationStrategy<EntityType<?>>> entityTypeRegistrationStrategyList;

  public Registry(String modId) {

    this(modId, null);
  }

  public Registry(String modId, @Nullable ItemGroup itemGroup) {

    this.modId = modId;
    this.itemGroup = itemGroup;

    this.blockRegistrationStrategyList = new ArrayList<>();
    this.itemRegistrationStrategyList = new ArrayList<>();
    this.tileEntityRegistrationStrategyList = new ArrayList<>();
    this.clientModelRegistrationStrategyList = new ArrayList<>();
    this.biomeRegistrationStrategyList = new ArrayList<>();
    this.potionRegistrationStrategyList = new ArrayList<>();
    this.effectRegistrationStrategyList = new ArrayList<>();
    this.entityTypeRegistrationStrategyList = new ArrayList<>();
  }

  public String getModId() {

    return this.modId;
  }

  public String getLangKey(ResourceLocation resourceLocation) {

    String namespace = resourceLocation.getNamespace().replaceAll("_", ".");
    String path = resourceLocation.getPath().toLowerCase().replace("_", ".");
    return namespace + "." + path;
  }

  public String getLangKey(String path) {

    String namespace = this.modId.replaceAll("_", ".");
    return namespace + "." + path;
  }

  public ItemGroup getItemGroup() {

    return this.itemGroup;
  }

  // --------------------------------------------------------------------------
  // - Block
  // --------------------------------------------------------------------------

  public List<IForgeRegistryEventRegistrationStrategy<Block>> getBlockRegistrationStrategyList() {

    return Collections.unmodifiableList(this.blockRegistrationStrategyList);
  }

  public void registerBlockRegistrationStrategy(IForgeRegistryEventRegistrationStrategy<Block> strategy) {

    this.blockRegistrationStrategyList.add(strategy);
  }

  /**
   * Registers the given block, using the mod id as the namespace and the given
   * path for the block's registry name.
   *
   * @param block the block to register
   * @param path  the resource path of the block's registry name
   * @param <B>   the block type
   * @return the registered block
   */
  public <B extends Block> B registerBlock(B block, String path) {

    ResourceLocation resourceLocation = new ResourceLocation(this.getModId(), path);
    block.setRegistryName(resourceLocation);
    this.registerBlockRegistrationStrategy(forgeRegistry -> forgeRegistry.register(block));
    return block;
  }

  /**
   * Registers the given block, using the mod id as the namespace and the given
   * path for the block's registry name. Creates a new {@link BlockItem} using
   * the given {@link Item.Properties} and the same resource location used for
   * the block.
   *
   * @param block      the block to register
   * @param path       the resource path of the block's registry name
   * @param properties the bock's item properties
   * @param <B>        the block type
   * @return the registered block
   */
  public <B extends Block> B registerBlockWithItem(B block, String path, Item.Properties properties) {

    BlockItem itemBlock = new BlockItem(block, properties);
    return this.registerBlock(block, itemBlock, path);
  }

  /**
   * Registers the given block, using the mod id as the namespace and the given
   * path for the block's registry name. Registers the given {@link BlockItem}
   * using the same resource location used for the block.
   *
   * @param block     the block to register
   * @param itemBlock the block's item to register
   * @param path      the resource path of the block's registry name
   * @param <B>       the block type
   * @param <I>       the block's item type
   * @return the registered block
   */
  public <B extends Block, I extends BlockItem> B registerBlock(B block, I itemBlock, String path) {

    this.registerBlock(block, path);
    ResourceLocation resourceLocation = new ResourceLocation(this.getModId(), path);
    this.registerItem(itemBlock, resourceLocation);
    return block;
  }

  // --------------------------------------------------------------------------
  // - Item
  // --------------------------------------------------------------------------

  public List<IForgeRegistryEventRegistrationStrategy<Item>> getItemRegistrationStrategyList() {

    return Collections.unmodifiableList(this.itemRegistrationStrategyList);
  }

  public void registerItemRegistrationStrategy(IForgeRegistryEventRegistrationStrategy<Item> strategy) {

    this.itemRegistrationStrategyList.add(strategy);
  }

  public Item registerItem(Item item, String name) {

    return this.registerItem(item, new ResourceLocation(this.getModId(), name));
  }

  public Item registerItem(Item item, String name, boolean noCreativeTab) {

    return this.registerItem(item, new ResourceLocation(this.getModId(), name), noCreativeTab);
  }

  public Item registerItem(Item item, ResourceLocation registryName) {

    return this.registerItem(item, registryName, false);
  }

  public Item registerItem(Item item, ResourceLocation registryName, boolean noCreativeTab) {

    item.setRegistryName(registryName);
    this.registerItemRegistrationStrategy(forgeRegistry -> forgeRegistry.register(item));
    return item;
  }

  // --------------------------------------------------------------------------
  // - Tile Entity
  // --------------------------------------------------------------------------

  public List<IForgeRegistryEventRegistrationStrategy<TileEntityType<?>>> getTileEntityRegistrationStrategyList() {

    return Collections.unmodifiableList(this.tileEntityRegistrationStrategyList);
  }

  public void registerTileEntityRegistrationStrategy(IForgeRegistryEventRegistrationStrategy<TileEntityType<?>> strategy) {

    this.tileEntityRegistrationStrategyList.add(strategy);
  }

  public final void registerTileEntities(TileEntityType[] tileEntityTypes) {

    for (TileEntityType tileEntityType : tileEntityTypes) {
      this.registerTileEntity(tileEntityType);
    }
  }

  public void registerTileEntity(TileEntityType<?> tileEntityType) {

    this.registerTileEntityRegistrationStrategy(forgeRegistry -> {
      forgeRegistry.register(tileEntityType);
    });
  }

  // --------------------------------------------------------------------------
  // - Models
  // --------------------------------------------------------------------------

  public List<IClientModelRegistrationStrategy> getClientModelRegistrationStrategyList() {

    return Collections.unmodifiableList(this.clientModelRegistrationStrategyList);
  }

  @OnlyIn(Dist.CLIENT)
  public void registerClientModelRegistrationStrategy(IClientModelRegistrationStrategy strategy) {

    this.clientModelRegistrationStrategyList.add(strategy);
  }

  // --------------------------------------------------------------------------
  // - Biomes
  // --------------------------------------------------------------------------

  public List<IForgeRegistryEventRegistrationStrategy<Biome>> getBiomeRegistrationStrategyList() {

    return Collections.unmodifiableList(this.biomeRegistrationStrategyList);
  }

  public void registerBiomeRegistrationStrategy(IForgeRegistryEventRegistrationStrategy<Biome> strategy) {

    this.biomeRegistrationStrategyList.add(strategy);
  }

  public void registerBiome(Biome biome, String name) {

    this.registerBiome(biome, name, new BiomeDictionary.Type[0]);
  }

  public void registerBiome(Biome biome, String name, BiomeDictionary.Type[] types) {

    this.registerBiomeRegistrationStrategy(forgeRegistry -> {
      biome.setRegistryName(this.getModId(), name);
      forgeRegistry.register(biome);

      if (types.length > 0) {
        BiomeDictionary.addTypes(biome, types);
      }
    });
  }

  // --------------------------------------------------------------------------
  // - Effects
  // --------------------------------------------------------------------------

  public List<IForgeRegistryEventRegistrationStrategy<Effect>> getEffectRegistrationStrategyList() {

    return Collections.unmodifiableList(this.effectRegistrationStrategyList);
  }

  public void registerEffectRegistrationStrategy(IForgeRegistryEventRegistrationStrategy<Effect> strategy) {

    this.effectRegistrationStrategyList.add(strategy);
  }

  public void registerEffect(Effect potionType, String name) {

    this.registerEffect(potionType, new ResourceLocation(this.getModId(), name));
  }

  public void registerEffect(Effect potionType, ResourceLocation registryName) {

    this.registerEffectRegistrationStrategy(forgeRegistry -> {
      potionType.setRegistryName(registryName);
      forgeRegistry.register(potionType);
    });

  }

  // --------------------------------------------------------------------------
  // - Potions
  // --------------------------------------------------------------------------

  public List<IForgeRegistryEventRegistrationStrategy<Potion>> getPotionRegistrationStrategyList() {

    return Collections.unmodifiableList(this.potionRegistrationStrategyList);
  }

  public void registerPotionRegistrationStrategy(IForgeRegistryEventRegistrationStrategy<Potion> strategy) {

    this.potionRegistrationStrategyList.add(strategy);
  }

  public void registerPotion(Potion potion, String name) {

    this.registerPotion(potion, new ResourceLocation(this.getModId(), name));
  }

  public void registerPotion(Potion potion, ResourceLocation registryName) {

    this.registerPotionRegistrationStrategy(forgeRegistry -> {
      potion.setRegistryName(registryName);
      forgeRegistry.register(potion);
    });
  }

  // --------------------------------------------------------------------------
  // - Entities
  // --------------------------------------------------------------------------

  public List<IForgeRegistryEventRegistrationStrategy<EntityType<?>>> getEntityTypeRegistrationStrategyList() {

    return Collections.unmodifiableList(this.entityTypeRegistrationStrategyList);
  }

  public void registerEntityTypeRegistrationStrategy(IForgeRegistryEventRegistrationStrategy<EntityType<?>> strategy) {

    this.entityTypeRegistrationStrategyList.add(strategy);
  }

  public void registerEntityType(EntityType<?> entityType) {

    this.registerEntityTypeRegistrationStrategy(forgeRegistry -> {

      forgeRegistry.register(entityType);
    });
  }

  public <E extends Entity> void createEntityType(String name, EntityType.Builder<E> builder) {

    ResourceLocation resourceLocation = new ResourceLocation(this.getModId(), name);
    EntityType<E> entityType = builder.build(resourceLocation.toString());
    entityType.setRegistryName(resourceLocation);
    this.registerEntityType(entityType);
  }
}
