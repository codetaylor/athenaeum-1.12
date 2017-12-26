package com.codetaylor.mc.athenaeum.helper;

import com.codetaylor.mc.athenaeum.spi.IBlockVariant;
import com.codetaylor.mc.athenaeum.spi.IVariant;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;
import java.util.function.ToIntFunction;

/**
 * Based on:
 * https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.12.2/src/main/java/choonster/testmod3/client/model/ModModelManager.java
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ModelRegistrationHelper {

  /**
   * A {@link StateMapperBase} used to create property strings.
   */
  public static final StateMapperBase PROPERTY_STRING_MAPPER = new StateMapperBase() {

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {

      return new ModelResourceLocation("minecraft:air");
    }
  };

  // --------------------------------------------------------------------------
  // - ItemBlock
  // --------------------------------------------------------------------------

  /**
   * Registers given blocks either as variant block item models or single block item models.
   *
   * @param blocks the blocks
   */
  public static void registerBlockItemModels(Block... blocks) {

    for (Block block : blocks) {

      if (block instanceof IBlockVariant) {
        //noinspection unchecked
        ModelRegistrationHelper.registerVariantBlockItemModels(
            block.getDefaultState(),
            ((IBlockVariant) block).getVariant()
        );

      } else {
        ModelRegistrationHelper.registerBlockItemModel(block.getDefaultState());
      }
    }
  }

  /**
   * Registers a single item model for the block of the given blockState.
   * <p>
   * Uses the block registry name as the resource path / domain.
   * Uses the property string mapper for the variant.
   *
   * @param blockState the blockState
   */
  public static void registerBlockItemModel(IBlockState blockState) {

    Block block = blockState.getBlock();
    Item item = Item.getItemFromBlock(block);

    ModelRegistrationHelper.registerItemModel(
        item,
        new ModelResourceLocation(
            Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block),
            PROPERTY_STRING_MAPPER.getPropertyString(blockState.getProperties())
        )
    );
  }

  /**
   * Register a model for each property given.
   * <p>
   * Uses the given mod id as the resource domain.
   * Queries the given {@link IBlockVariant} for the resource path.
   * <p>
   * This is useful to provide a modified version of the {@link IVariant#getName()} or override completely.
   *
   * @param modId    the mod id
   * @param block    the {@link IBlockVariant}
   * @param property the property
   * @param <T>      the property type
   * @param <B>      the block type
   */
  public static <T extends IVariant & Comparable<T>, B extends Block & IBlockVariant<T>> void registerVariantBlockItemModelsSeparately(
      String modId,
      B block,
      IProperty<T> property
  ) {

    for (T value : property.getAllowedValues()) {
      Item item = Item.getItemFromBlock(block);

      if (item == Items.AIR) {
        continue;
      }

      String name = block.getModelName(new ItemStack(item, 1, value.getMeta()));
      ModelRegistrationHelper.registerItemModel(
          item,
          value.getMeta(),
          new ModelResourceLocation(modId + ":" + name, "inventory")
      );
    }
  }

  /**
   * Register a model for each property given.
   * <p>
   * Uses the registry name as the domain / path and the property string mapper for the variant.
   * <p>
   * Note: Throws NPE if the block's item doesn't have a registry name.
   *
   * @param baseState the blockState
   * @param property  the property
   * @param <T>       the property type
   */
  public static <T extends IVariant & Comparable<T>> void registerVariantBlockItemModels(
      IBlockState baseState,
      IProperty<T> property
  ) {

    ModelRegistrationHelper.registerVariantBlockItemModels(baseState, property, IVariant::getMeta);
  }

  /**
   * Register a model for each property given.
   * <p>
   * Uses the registry name as the domain / path and the property string mapper for the variant.
   * <p>
   * Note: Throws NPE if the block's item doesn't have a registry name.
   *
   * @param baseState the blockState
   * @param property  the property
   * @param getMeta   the meta function
   * @param <T>       the property type
   */
  public static <T extends Comparable<T>> void registerVariantBlockItemModels(
      IBlockState baseState,
      IProperty<T> property,
      ToIntFunction<T> getMeta
  ) {

    property.getAllowedValues()
        .forEach(value -> ModelRegistrationHelper.registerBlockItemModelForMeta(
            baseState.withProperty(property, value),
            getMeta.applyAsInt(value)
        ));
  }

  /**
   * Register a model for the blockState and meta given.
   * <p>
   * Uses the registry name as the domain / path and the property string mapper for the variant.
   * <p>
   * Note: Throws NPE if the block's item doesn't have a registry name.
   *
   * @param state    the blockState
   * @param metadata the meta
   */
  public static void registerBlockItemModelForMeta(final IBlockState state, final int metadata) {

    Item item = Item.getItemFromBlock(state.getBlock());

    if (item == Items.AIR) {
      return;
    }

    ModelRegistrationHelper.registerItemModel(
        item,
        metadata,
        PROPERTY_STRING_MAPPER.getPropertyString(state.getProperties())
    );
  }

  // --------------------------------------------------------------------------
  // - Item
  // --------------------------------------------------------------------------

  /**
   * Register a single model for the given items.
   * <p>
   * Uses a meta of 0.
   * <p>
   * Uses the registry name as the domain / path and {@code "inventory"} as the variant.
   * <p>
   * Note: This method will check that each item has a registry name and throw a NPE if it doesn't.
   *
   * @param items the items
   */
  public static void registerItemModels(Item... items) {

    for (Item item : items) {
      ResourceLocation registryName = item.getRegistryName();
      Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);
      ModelRegistrationHelper.registerItemModel(item, registryName.toString());
    }
  }

  /**
   * Register a single model for the given item.
   * <p>
   * Uses a meta of 0.
   * <p>
   * Uses {@code modelLocation} as the domain/path and {@code "inventory"} as the variant.
   *
   * @param item          the item
   * @param modelLocation the model location
   */
  public static void registerItemModel(Item item, String modelLocation) {

    ModelResourceLocation resourceLocation = new ModelResourceLocation(modelLocation, "inventory");
    ModelRegistrationHelper.registerItemModel(item, 0, resourceLocation);
  }

  /**
   * Register a single model for the item given.
   * <p>
   * Uses a meta of 0.
   *
   * @param item             the item
   * @param resourceLocation the model resource location
   */
  public static void registerItemModel(Item item, ModelResourceLocation resourceLocation) {

    ModelRegistrationHelper.registerItemModel(item, 0, resourceLocation);
  }

  /**
   * Register a model for the item and meta given.
   * <p>
   * Uses the registry name as the domain / path and the given variant.
   * <p>
   * Note: This method will check that the item has a registry name and throw a NPE if it doesn't.
   *
   * @param item     the item
   * @param metadata the item's meta
   * @param variant  the variant
   */
  public static void registerItemModel(@Nonnull final Item item, final int metadata, @Nonnull final String variant) {

    ResourceLocation registryName = item.getRegistryName();
    Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

    ModelRegistrationHelper.registerItemModel(
        item,
        metadata,
        new ModelResourceLocation(registryName, variant)
    );
  }

  /**
   * Registers a model for each {@link IVariant} value given.
   * <p>
   * Uses the registry name as the domain/path and {@code "[variantName]=[valueName]"} as the variant.
   * <p>
   * Uses {@link IVariant#getMeta()} to determine the metadata of each value.
   *
   * @param item        the item
   * @param variantName the variant name
   * @param values      the {@link IVariant} values
   * @param <T>         the {@link IVariant} type
   */
  public static <T extends IVariant> void registerVariantItemModels(
      @Nonnull Item item,
      @Nonnull String variantName,
      @Nonnull T[] values
  ) {

    for (T value : values) {
      ModelRegistrationHelper.registerItemModel(item, value.getMeta(), variantName + "=" + value.getName());
    }
  }

  /**
   * Sets a custom model resource location for the item and meta given.
   *
   * @param item             the item
   * @param meta             the item's meta
   * @param resourceLocation the custom model resource location
   */
  public static void registerItemModel(@Nonnull Item item, int meta, @Nonnull ModelResourceLocation resourceLocation) {

    ModelLoader.setCustomModelResourceLocation(item, meta, resourceLocation);
  }

}
