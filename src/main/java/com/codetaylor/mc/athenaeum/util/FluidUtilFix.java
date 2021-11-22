package com.codetaylor.mc.athenaeum.util;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.wrappers.BlockLiquidWrapper;
import net.minecraftforge.fluids.capability.wrappers.BlockWrapper;
import net.minecraftforge.fluids.capability.wrappers.FluidBlockWrapper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This is a workaround for:
 * https://github.com/codetaylor/artisan-worktables/issues/236
 * https://github.com/codetaylor/pyrotech-1.12/issues/375
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

  @Nonnull
  public static FluidActionResult tryPlaceFluid(@Nullable EntityPlayer player, World world, BlockPos pos, @Nonnull ItemStack container, FluidStack resource) {

    ItemStack containerCopy = ItemHandlerHelper.copyStackWithSize(container, 1); // do not modify the input
    IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(containerCopy);
    if (containerFluidHandler != null && tryPlaceFluid(player, world, pos, containerFluidHandler, resource)) {
      return new FluidActionResult(containerFluidHandler.getContainer());
    }
    return FluidActionResult.FAILURE;
  }

  public static boolean tryPlaceFluid(@Nullable EntityPlayer player, World world, BlockPos pos, IFluidHandler fluidSource, FluidStack resource) {

    if (world == null || resource == null || pos == null) {
      return false;
    }

    Fluid fluid = resource.getFluid();
    if (fluid == null || !fluid.canBePlacedInWorld()) {
      return false;
    }

    if (fluidSource.drain(resource, false) == null) {
      return false;
    }

    // check that we can place the fluid at the destination
    IBlockState destBlockState = world.getBlockState(pos);
    Material destMaterial = destBlockState.getMaterial();
    boolean isDestNonSolid = !destMaterial.isSolid();
    boolean isDestReplaceable = destBlockState.getBlock().isReplaceable(world, pos);

    if (!world.isAirBlock(pos) && !isDestNonSolid && !isDestReplaceable) {
      return false; // Non-air, solid, unreplacable block. We can't put fluid here.
    }

    if (world.provider.doesWaterVaporize() && fluid.doesVaporize(resource)) {
      FluidStack result = fluidSource.drain(resource, true);

      if (result != null) {
        result.getFluid().vaporize(player, world, pos, result);
        return true;
      }
    } else {
      // This fluid handler places the fluid block when filled
      IFluidHandler handler = getFluidBlockHandler(fluid, world, pos);

      // Fix for https://github.com/codetaylor/pyrotech-1.12/issues/375
      if (isDestReplaceable && !destMaterial.isLiquid() && !(destBlockState.getBlock() instanceof BlockFluidBase)) {
        // Simulate the test first and break the block if it passes.
        FluidStack resultTest = FluidUtil.tryFluidTransfer(handler, fluidSource, resource, false);

        if (resultTest != null) {
          world.destroyBlock(pos, true);
        }
      }
      // End fix

      FluidStack result = FluidUtil.tryFluidTransfer(handler, fluidSource, resource, true);

      if (result != null) {
        SoundEvent soundevent = resource.getFluid().getEmptySound(resource);
        world.playSound(player, pos, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        return true;
      }
    }
    return false;
  }

  private static IFluidHandler getFluidBlockHandler(Fluid fluid, World world, BlockPos pos) {

    Block block = fluid.getBlock();
    if (block instanceof IFluidBlock) {
      return new FluidBlockWrapper((IFluidBlock) block, world, pos);

    } else if (block instanceof BlockLiquid) {
      return new BlockLiquidWrapper((BlockLiquid) block, world, pos);

    } else {
      return new BlockWrapper(block, world, pos);
    }
  }

  private FluidUtilFix() {
    //
  }

}
