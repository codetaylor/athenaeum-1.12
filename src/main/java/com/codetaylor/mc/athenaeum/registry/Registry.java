package com.codetaylor.mc.athenaeum.registry;

import com.codetaylor.mc.athenaeum.registry.strategy.IClientModelRegistrationStrategy;
import com.codetaylor.mc.athenaeum.registry.strategy.IForgeRegistryEventRegistrationStrategy;
import com.codetaylor.mc.athenaeum.registry.strategy.ITileEntityRegistrationStrategy;
import com.codetaylor.mc.athenaeum.spi.IBlockColored;
import com.codetaylor.mc.athenaeum.spi.IBlockVariant;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue", "unused"})
public class Registry {

  private final String modId;
  private final CreativeTabs creativeTabs;

  private final List<IForgeRegistryEventRegistrationStrategy<Block>> blockRegistrationStrategyList;
  private final List<IForgeRegistryEventRegistrationStrategy<Item>> itemRegistrationStrategyList;
  private final List<ITileEntityRegistrationStrategy> tileEntityRegistrationStrategyList;
  private final List<IClientModelRegistrationStrategy> clientModelRegistrationStrategyList;
  private final List<IForgeRegistryEventRegistrationStrategy<Biome>> biomeRegistrationStrategyList;
  private final List<IForgeRegistryEventRegistrationStrategy<Potion>> potionRegistrationStrategyList;

  public Registry(String modId) {

    this(modId, null);
  }

  public Registry(String modId, @Nullable CreativeTabs creativeTabs) {

    this.modId = modId;
    this.creativeTabs = creativeTabs;

    this.blockRegistrationStrategyList = new ArrayList<>();
    this.itemRegistrationStrategyList = new ArrayList<>();
    this.tileEntityRegistrationStrategyList = new ArrayList<>();
    this.clientModelRegistrationStrategyList = new ArrayList<>();
    this.biomeRegistrationStrategyList = new ArrayList<>();
    this.potionRegistrationStrategyList = new ArrayList<>();
  }

  public String getModId() {

    return this.modId;
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

  public <B extends Block> B registerBlock(B block, String name) {

    ResourceLocation resourceLocation = new ResourceLocation(this.modId, name);
    block.setRegistryName(resourceLocation);
    block.setUnlocalizedName(this.modId + "." + name);

    if (this.creativeTabs != null) {
      block.setCreativeTab(this.creativeTabs);
    }

    this.registerBlockRegistrationStrategy(forgeRegistry -> forgeRegistry.register(block));

    return block;
  }

  public <B extends Block> B registerBlockWithItem(B block, String name) {

    ItemBlock itemBlock;

    if (block instanceof IBlockColored) {
      itemBlock = new ItemColored(block, ((IBlockColored) block).hasBlockColoredSubtypes());

    } else if (block instanceof IBlockVariant) {
      itemBlock = new ItemMultiTexture(block, block, ((IBlockVariant) block)::getModelName);

    } else {
      itemBlock = new ItemBlock(block);
    }

    return this.registerBlock(block, itemBlock, name);
  }

  public <B extends Block, I extends ItemBlock> B registerBlock(B block, I itemBlock, String name) {

    this.registerBlock(block, name);

    ResourceLocation resourceLocation = new ResourceLocation(this.modId, name);
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

  public Item registerItem(Item item, ResourceLocation registryName) {

    String resourceDomain = registryName.getResourceDomain().replaceAll("_", ".");
    String resourcePath = registryName.getResourcePath().toLowerCase().replace("_", ".");
    item.setRegistryName(registryName);
    item.setUnlocalizedName(resourceDomain + "." + resourcePath);

    if (this.creativeTabs != null) {
      item.setCreativeTab(this.creativeTabs);
    }

    this.registerItemRegistrationStrategy(forgeRegistry -> forgeRegistry.register(item));

    return item;
  }

  // --------------------------------------------------------------------------
  // - Tile Entity
  // --------------------------------------------------------------------------

  public List<ITileEntityRegistrationStrategy> getTileEntityRegistrationStrategyList() {

    return Collections.unmodifiableList(this.tileEntityRegistrationStrategyList);
  }

  public void registerTileEntityRegistrationStrategy(ITileEntityRegistrationStrategy strategy) {

    this.tileEntityRegistrationStrategyList.add(strategy);
  }

  @SafeVarargs
  public final void registerTileEntities(Class<? extends TileEntity>... tileEntityClasses) {

    for (Class<? extends TileEntity> tileEntityClass : tileEntityClasses) {
      this.registerTileEntity(tileEntityClass);
    }
  }

  public void registerTileEntity(Class<? extends TileEntity> tileEntityClass) {

    this.registerTileEntityRegistrationStrategy(() -> GameRegistry.registerTileEntity(
        tileEntityClass,
        this.getModId() + ".tile." + tileEntityClass.getSimpleName()
    ));
  }

  // --------------------------------------------------------------------------
  // - Models
  // --------------------------------------------------------------------------

  public List<IClientModelRegistrationStrategy> getClientModelRegistrationStrategyList() {

    return Collections.unmodifiableList(this.clientModelRegistrationStrategyList);
  }

  @SideOnly(Side.CLIENT)
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
      String resourceDomain = registryName.getResourceDomain().replaceAll("_", ".");
      String resourcePath = registryName.getResourcePath().toLowerCase().replace("_", ".");
      potion.setRegistryName(registryName);
      potion.setPotionName(resourceDomain + ".effect." + resourcePath);
      forgeRegistry.register(potion);
    });
  }
}
