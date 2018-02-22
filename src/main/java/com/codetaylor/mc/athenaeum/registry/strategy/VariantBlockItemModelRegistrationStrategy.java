package com.codetaylor.mc.athenaeum.registry.strategy;

import com.codetaylor.mc.athenaeum.spi.IVariant;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class VariantBlockItemModelRegistrationStrategy<T extends IVariant & Comparable<T>>
    implements IClientModelRegistrationStrategy {

  /**
   * A {@link StateMapperBase} used to create property strings.
   */
  private static final StateMapperBase PROPERTY_STRING_MAPPER = new StateMapperBase() {

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {

      return new ModelResourceLocation("minecraft:air");
    }
  };

  private final IBlockState blockState;
  private final IProperty<T> property;

  public VariantBlockItemModelRegistrationStrategy(IBlockState blockState, IProperty<T> property) {

    this.blockState = blockState;
    this.property = property;
  }

  @Override
  public void register() {

    this.property.getAllowedValues().forEach(value -> {
      IBlockState state = this.blockState.withProperty(this.property, value);
      Block block = state.getBlock();
      Item item = Item.getItemFromBlock(block);
      ModelLoader.setCustomModelResourceLocation(
          item,
          value.getMeta(),
          new ModelResourceLocation(
              Preconditions.checkNotNull(item.getRegistryName(), "Item %s has null registry name", item),
              PROPERTY_STRING_MAPPER.getPropertyString(state.getProperties())
          )
      );
    });
  }
}
