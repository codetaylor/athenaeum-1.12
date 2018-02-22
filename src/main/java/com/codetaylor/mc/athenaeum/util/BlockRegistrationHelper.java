package com.codetaylor.mc.athenaeum.util;

import com.codetaylor.mc.athenaeum.spi.IBlockColored;
import com.codetaylor.mc.athenaeum.spi.IBlockVariant;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("WeakerAccess")
public class BlockRegistrationHelper {

  /**
   * Calls {@link BlockRegistrationHelper#createItemBlock(Block)} for each block given.
   *
   * @param blocks the blocks
   * @return an array of {@link ItemBlock}
   */
  public static ItemBlock[] createItemBlocks(Block... blocks) {

    ItemBlock[] result = new ItemBlock[blocks.length];

    for (int i = 0; i < blocks.length; i++) {
      ItemBlock itemBlock = BlockRegistrationHelper.createItemBlock(blocks[i]);
      result[i] = itemBlock;
    }

    return result;
  }

  /**
   * Creates and returns an {@link ItemBlock}, {@link ItemMultiTexture}, or {@link ItemColored}.
   *
   * @param block the block
   * @return a new {@link ItemBlock}
   */
  public static ItemBlock createItemBlock(Block block) {

    ItemBlock itemBlock;

    if (block instanceof IBlockColored) {
      itemBlock = new ItemColored(block, ((IBlockColored) block).hasBlockColoredSubtypes());

    } else if (block instanceof IBlockVariant) {
      itemBlock = new ItemMultiTexture(block, block, ((IBlockVariant) block)::getModelName);

    } else {
      itemBlock = new ItemBlock(block);
    }

    BlockRegistrationHelper.setItemBlockRegistryName(block, itemBlock);
    return itemBlock;
  }

  /**
   * Set the given {@link ItemBlock} registry name to the given {@link Block} registry name.
   * <p>
   * Throws a NPE if the block doesn't have a registry name.
   *
   * @param block     the {@link Block}
   * @param itemBlock the {@link ItemBlock}
   */
  public static void setItemBlockRegistryName(Block block, ItemBlock itemBlock) {

    ResourceLocation registryName = block.getRegistryName();
    Preconditions.checkNotNull(registryName, "Block %s has null registry name", block);
    itemBlock.setRegistryName(registryName);
  }

}
