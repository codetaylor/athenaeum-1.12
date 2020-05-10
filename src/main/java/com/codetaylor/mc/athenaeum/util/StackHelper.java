package com.codetaylor.mc.athenaeum.util;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public final class StackHelper {

  public static final String BLOCK_ENTITY_TAG = "BlockEntityTag";

  public static boolean isFuel(ItemStack itemStack) {

    return FurnaceTileEntity.isFuel(itemStack);
  }

  public static int getItemBurnTime(ItemStack itemStack) {

    return ForgeHooks.getBurnTime(itemStack);
  }

  /**
   * Decreases the stack in the given slot by the given amount.
   *
   * @param stackHandler   the handler
   * @param slot           the slot to adjust
   * @param amount         the amount to add
   * @param checkContainer should this decrement be container sensitive
   * @return the adjusted stack
   * @see #decrease(ItemStack, int, boolean)
   */
  public static ItemStack decreaseStackInSlot(ItemStackHandler stackHandler, int slot, int amount, boolean checkContainer) {

    ItemStack stackInSlot = stackHandler.getStackInSlot(slot).copy();
    ItemStack adjustedStack = StackHelper.decrease(stackInSlot, amount, checkContainer);
    stackHandler.setStackInSlot(slot, adjustedStack);
    return adjustedStack;
  }

  /**
   * Returns an item stack's {@link net.minecraft.nbt.CompoundNBT}. If the stack is empty,
   * returns a new, empty tag.
   *
   * @param itemStack the item stack
   * @return the stack's tag or a new tag if the stack is empty
   */
  @Nonnull
  public static CompoundNBT getTagSafe(ItemStack itemStack) {

    if (itemStack.isEmpty()) {
      return new CompoundNBT();
    }

    CompoundNBT tag = itemStack.getTag();

    if (tag == null) {
      tag = new CompoundNBT();
      itemStack.setTag(tag);
    }

    return tag;
  }

  @ParametersAreNonnullByDefault
  public static List<ItemStack> copyInto(List<ItemStack> sourceList, List<ItemStack> targetList) {

    for (ItemStack itemStack : sourceList) {
      targetList.add(itemStack.copy());
    }

    return targetList;
  }

  /**
   * Container sensitive decrease stack.
   * <p>
   * ie. bucket
   *
   * @param itemStack      the {@link ItemStack}
   * @param amount         decrease amount
   * @param checkContainer check for container
   * @return the resulting {@link ItemStack}
   */
  public static ItemStack decrease(ItemStack itemStack, int amount, boolean checkContainer) {

    if (itemStack.isEmpty()) {
      return ItemStack.EMPTY;
    }

    itemStack.shrink(amount);

    if (itemStack.getCount() <= 0) {

      if (checkContainer && itemStack.getItem().hasContainerItem(itemStack)) {
        return itemStack.getItem().getContainerItem(itemStack);

      } else {
        return ItemStack.EMPTY;
      }
    }

    return itemStack;
  }

  /**
   * Must be called from both sides, client and server.
   *
   * @param world
   * @param player
   * @param itemStack
   * @param pos
   * @param offsetY
   * @param preferActiveSlot
   * @param playPickupSound
   */
  public static void addToInventoryOrSpawn(World world, PlayerEntity player, ItemStack itemStack, BlockPos pos, double offsetY, boolean preferActiveSlot, boolean playPickupSound) {

    if (preferActiveSlot) {
      IItemHandler inventory = new PlayerMainInvWrapper(player.inventory);

      ItemStack remainder = inventory.insertItem(player.inventory.currentItem, itemStack, false);

      if (!remainder.isEmpty()) {
        remainder = ItemHandlerHelper.insertItemStacked(inventory, remainder, false);
      }

      if (playPickupSound
          && remainder.isEmpty()
          || remainder.getCount() != itemStack.getCount()) {

        world.playSound(player, player.posX, player.posY, player.posZ,
            SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F
        );
      }

      if (!remainder.isEmpty() && !world.isRemote) {
        StackHelper.spawnStackOnTop(world, itemStack, pos, offsetY);
      }

    } else {

      if (!player.inventory.addItemStackToInventory(itemStack)) {

        if (!world.isRemote) {
          StackHelper.spawnStackOnTop(world, itemStack, pos, offsetY);
        }

      } else if (playPickupSound) {
        world.playSound(player, player.posX, player.posY, player.posZ,
            SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F
        );
      }
    }
  }

  /**
   * Iterates the contents of an {@link ItemStackHandler} and spawns each
   * non-empty item in the world.
   * <p>
   * Server only.
   *
   * @param world        the world
   * @param stackHandler the stack handler to empty
   * @param pos          the position to spawn the items
   */
  public static void spawnStackHandlerContentsOnTop(World world, ItemStackHandler stackHandler, BlockPos pos) {

    StackHelper.spawnStackHandlerContentsOnTop(world, stackHandler, pos, 1.0);
  }

  /**
   * Iterates the contents of an {@link ItemStackHandler} and spawns each
   * non-empty item in the world.
   * <p>
   * Server only.
   *
   * @param world        the world
   * @param stackHandler the stack handler to empty
   * @param pos          the position to spawn the items
   * @param offsetY      the Y offset to spawn the items
   */
  public static void spawnStackHandlerContentsOnTop(World world, ItemStackHandler stackHandler, BlockPos pos, double offsetY) {

    for (int slot = 0; slot < stackHandler.getSlots(); slot++) {
      StackHelper.spawnStackHandlerSlotContentsOnTop(world, stackHandler, slot, pos, offsetY);
    }
  }

  public static void spawnStackHandlerSlotContentsOnTop(World world, ItemStackHandler stackHandler, int slot, BlockPos pos) {

    StackHelper.spawnStackHandlerSlotContentsOnTop(world, stackHandler, slot, pos, 1.0);
  }

  public static void spawnStackHandlerSlotContentsOnTop(World world, ItemStackHandler stackHandler, int slot, BlockPos pos, double offsetY) {

    ItemStack itemStack;

    while (!(itemStack = stackHandler.extractItem(slot, stackHandler.getSlotLimit(slot), false)).isEmpty()) {
      StackHelper.spawnStackOnTop(world, itemStack, pos, offsetY);
    }
  }

  /**
   * Spawns an {@link ItemStack} in the world, directly above the given position.
   * <p>
   * Server only.
   *
   * @param world     the world
   * @param itemStack the {@link ItemStack} to spawn
   * @param pos       the position to spawn
   */
  public static void spawnStackOnTop(World world, ItemStack itemStack, BlockPos pos) {

    StackHelper.spawnStackOnTop(world, itemStack, pos, 1.0);
  }

  /**
   * Spawns an {@link ItemStack} in the world, directly above the given position.
   * <p>
   * Server only.
   *
   * @param world     the world
   * @param itemStack the {@link ItemStack} to spawn
   * @param pos       the position to spawn
   */
  public static void spawnStackOnTop(World world, ItemStack itemStack, BlockPos pos, double offsetY) {

    ItemEntity entityItem = new ItemEntity(
        world,
        pos.getX() + 0.5,
        pos.getY() + 0.5 + offsetY,
        pos.getZ() + 0.5,
        itemStack
    );
    entityItem.setMotion(0, 0.1, 0);

    world.addEntity(entityItem);
  }

  /**
   * Create and write a tile entity's NBT to the block entity tag of an item stack.
   *
   * @param block      the block
   * @param amount     the amount
   * @param tileEntity the TE
   * @return the IS
   */
  public static ItemStack createItemStackFromTileEntity(Block block, int amount, TileEntity tileEntity) {

    return StackHelper.createItemStackFromTileEntity(Item.getItemFromBlock(block), amount, tileEntity);
  }

  /**
   * Create and write a tile entity's NBT to the block entity tag of an item stack.
   *
   * @param item       the item
   * @param amount     the amount
   * @param tileEntity the TE
   * @return the IS
   */
  public static ItemStack createItemStackFromTileEntity(Item item, int amount, TileEntity tileEntity) {

    ItemStack itemStack = new ItemStack(() -> item, amount);
    return StackHelper.writeTileEntityToItemStack(tileEntity, itemStack);
  }

  /**
   * Write a tile entity's NBT to the block entity tag of an item stack.
   *
   * @param tileEntity the TE
   * @param itemStack  the IS
   * @return the IS
   */
  public static ItemStack writeTileEntityToItemStack(TileEntity tileEntity, ItemStack itemStack) {

    CompoundNBT compound;

    if (itemStack.getTag() != null) {
      compound = itemStack.getTag();

    } else {
      compound = new CompoundNBT();
    }

    CompoundNBT teCompound = new CompoundNBT();
    tileEntity.write(teCompound);
    compound.put(BLOCK_ENTITY_TAG, teCompound);
    itemStack.setTag(compound);
    return itemStack;
  }

  public static <T extends TileEntity> T readTileEntityFromItemStack(T tile, ItemStack itemStack) {

    CompoundNBT tagCompound = itemStack.getTag();

    if (tagCompound != null) {
      CompoundNBT teCompound = tagCompound.getCompound(BLOCK_ENTITY_TAG);
      tile.read(teCompound);
    }

    return tile;
  }

  public static ItemStack readLargeItemStack(CompoundNBT compound) {

    ItemStack itemStack = ItemStack.read(Preconditions.checkNotNull(compound));
    itemStack.setCount(compound.getInt("CountLarge"));
    return itemStack;
  }

  public static CompoundNBT writeLargeItemStack(ItemStack itemStack, CompoundNBT compound) {

    itemStack.write(compound);
    compound.putInt("CountLarge", itemStack.getCount());
    return compound;
  }

  private StackHelper() {
    //
  }

}
