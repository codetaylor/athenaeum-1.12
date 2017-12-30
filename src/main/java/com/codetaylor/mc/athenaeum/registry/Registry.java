package com.codetaylor.mc.athenaeum.registry;

import com.codetaylor.mc.athenaeum.registry.strategy.IModelRegistrationStrategy;
import com.codetaylor.mc.athenaeum.spi.IBlockColored;
import com.codetaylor.mc.athenaeum.spi.IBlockVariant;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Registry {

  private final String modId;
  private final CreativeTabs creativeTabs;

  private final List<Block> blockList;
  private final List<Item> itemList;
  private final List<Class<? extends TileEntity>> tileEntityClassList;
  private final List<IModelRegistrationStrategy> modelRegistrationStrategyList;

  public Registry(@Nonnull String modId) {

    this(modId, null);
  }

  public Registry(@Nonnull String modId, @Nullable CreativeTabs creativeTabs) {

    this.modId = modId;
    this.creativeTabs = creativeTabs;

    this.blockList = new ArrayList<>();
    this.itemList = new ArrayList<>();
    this.tileEntityClassList = new ArrayList<>();
    this.modelRegistrationStrategyList = new ArrayList<>();
  }

  public String getModId() {

    return this.modId;
  }

  // --------------------------------------------------------------------------
  // - Block
  // --------------------------------------------------------------------------

  public List<Block> getBlockList() {

    return Collections.unmodifiableList(this.blockList);
  }

  public <B extends Block> B registerBlock(B block, String name) {

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

    ResourceLocation resourceLocation = new ResourceLocation(this.modId, name);
    block.setRegistryName(resourceLocation);
    block.setUnlocalizedName(this.modId + "." + name);

    if (this.creativeTabs != null) {
      block.setCreativeTab(this.creativeTabs);
    }

    this.registerItem(itemBlock, resourceLocation);

    this.blockList.add(block);

    return block;
  }

  // --------------------------------------------------------------------------
  // - Item
  // --------------------------------------------------------------------------

  public List<Item> getItemList() {

    return Collections.unmodifiableList(this.itemList);
  }

  public Item registerItem(@Nonnull Item item, @Nonnull ResourceLocation registryName) {

    item.setRegistryName(registryName);
    item.setUnlocalizedName(
        registryName.getResourceDomain().replaceAll("_", ".") + "."
            + registryName.getResourcePath().toLowerCase().replace("_", ".")
    );

    if (this.creativeTabs != null) {
      item.setCreativeTab(this.creativeTabs);
    }

    this.itemList.add(item);

    return item;
  }

  // --------------------------------------------------------------------------
  // - Tile Entity
  // --------------------------------------------------------------------------

  public List<Class<? extends TileEntity>> getTileEntityClassList() {

    return Collections.unmodifiableList(this.tileEntityClassList);
  }

  @SafeVarargs
  public final void registerTileEntities(Class<? extends TileEntity>... tileEntityClasses) {

    for (Class<? extends TileEntity> tileEntityClass : tileEntityClasses) {
      this.registerTileEntity(tileEntityClass);
    }
  }

  public void registerTileEntity(@Nonnull Class<? extends TileEntity> tileEntityClass) {

    this.tileEntityClassList.add(tileEntityClass);
  }

  // --------------------------------------------------------------------------
  // - Models
  // --------------------------------------------------------------------------

  public List<IModelRegistrationStrategy> getModelRegistrationStrategyList() {

    return Collections.unmodifiableList(this.modelRegistrationStrategyList);
  }

  @SideOnly(Side.CLIENT)
  public void registerItemModelStrategy(IModelRegistrationStrategy strategy) {

    this.modelRegistrationStrategyList.add(strategy);
  }

}
