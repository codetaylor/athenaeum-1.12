package com.codetaylor.mc.athenaeum.util;

import com.google.common.base.Preconditions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This is a workaround for https://github.com/codetaylor/artisan-worktables/issues/236
 */
public final class FluidUtilFix {

  /**
   * @see FluidUtil#interactWithFluidHandler(EntityPlayer, EnumHand, IFluidHandler)
   */
  public static boolean interactWithFluidHandler(@Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull IFluidHandler handler) {

    Preconditions.checkNotNull(player);
    Preconditions.checkNotNull(hand);
    Preconditions.checkNotNull(handler);

    ItemStack heldItem = player.getHeldItem(hand);

    if (!heldItem.isEmpty()) {
      IItemHandler playerInventory = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

      if (playerInventory != null) {
        FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(heldItem, handler, playerInventory, Integer.MAX_VALUE, player, true);

        if (!fluidActionResult.isSuccess()) {
          fluidActionResult = FluidUtilFix.tryEmptyContainerAndStow(heldItem, handler, playerInventory, Integer.MAX_VALUE, player, true);
        }

        if (fluidActionResult.isSuccess()) {
          player.setHeldItem(hand, fluidActionResult.getResult());
          return true;
        }
      }
    }
    return false;
  }

  /**
   * @see FluidUtil#tryEmptyContainerAndStow(ItemStack, IFluidHandler, IItemHandler, int, EntityPlayer, boolean)
   */
  public static FluidActionResult tryEmptyContainerAndStow(@Nonnull ItemStack container, IFluidHandler fluidDestination, IItemHandler inventory, int maxAmount, @Nullable EntityPlayer player, boolean doDrain) {

    if (container.isEmpty()) {
      return FluidActionResult.FAILURE;
    }

    if (player != null && player.capabilities.isCreativeMode) {
      FluidActionResult emptiedReal = FluidUtilFix.tryEmptyContainer(container, fluidDestination, maxAmount, player, doDrain);

      if (emptiedReal.isSuccess()) {
        return new FluidActionResult(container); // creative mode: item does not change
      }

    } else if (container.getCount() == 1) // don't need to stow anything, just fill and edit the container stack
    {
      FluidActionResult emptiedReal = FluidUtilFix.tryEmptyContainer(container, fluidDestination, maxAmount, player, doDrain);

      if (emptiedReal.isSuccess()) {
        return emptiedReal;
      }

    } else {
      FluidActionResult emptiedSimulated = FluidUtilFix.tryEmptyContainer(container, fluidDestination, maxAmount, player, false);

      if (emptiedSimulated.isSuccess()) {
        // check if we can give the itemStack to the inventory
        ItemStack remainder = ItemHandlerHelper.insertItemStacked(inventory, emptiedSimulated.getResult(), true);

        if (remainder.isEmpty() || player != null) {
          FluidActionResult emptiedReal = FluidUtilFix.tryEmptyContainer(container, fluidDestination, maxAmount, player, doDrain);
          remainder = ItemHandlerHelper.insertItemStacked(inventory, emptiedReal.getResult(), !doDrain);

          // give it to the player or drop it at their feet
          if (!remainder.isEmpty() && player != null && doDrain) {
            ItemHandlerHelper.giveItemToPlayer(player, remainder);
          }

          ItemStack containerCopy = container.copy();
          containerCopy.shrink(1);
          return new FluidActionResult(containerCopy);
        }
      }
    }

    return FluidActionResult.FAILURE;
  }

  /**
   * @see FluidUtil#tryEmptyContainer(ItemStack, IFluidHandler, int, EntityPlayer, boolean)
   */
  public static FluidActionResult tryEmptyContainer(@Nonnull ItemStack container, IFluidHandler fluidDestination, int maxAmount, @Nullable EntityPlayer player, boolean doDrain) {

    ItemStack containerCopy = ItemHandlerHelper.copyStackWithSize(container, 1); // do not modify the input
    IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(containerCopy);

    if (containerFluidHandler != null) {

      if (doDrain) {
        FluidStack transfer = FluidUtil.tryFluidTransfer(fluidDestination, containerFluidHandler, maxAmount, true);

        if (transfer != null) {

          if (player != null) {
            SoundEvent soundevent = transfer.getFluid().getEmptySound(transfer);
            player.world.playSound(null, player.posX, player.posY + 0.5, player.posZ, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
          }
          ItemStack resultContainer = containerFluidHandler.getContainer();
          return new FluidActionResult(resultContainer);
        }

      } else {
        FluidStack simulatedTransfer = FluidUtil.tryFluidTransfer(fluidDestination, containerFluidHandler, maxAmount, false);

        if (simulatedTransfer != null) {
          /*
          Here we check the result of the drain and only return if it isn't null.
          This is the only difference from the Forge classes.
           */
          FluidStack drained = containerFluidHandler.drain(simulatedTransfer, true);

          if (drained != null) {
            ItemStack resultContainer = containerFluidHandler.getContainer();
            return new FluidActionResult(resultContainer);
          }
        }
      }
    }
    return FluidActionResult.FAILURE;
  }

  private FluidUtilFix() {
    //
  }

}
