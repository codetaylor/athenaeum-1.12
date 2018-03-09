package com.codetaylor.mc.athenaeum.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * https://github.com/BloodWorkXGaming/ExNihiloCreatio/blob/1.12/src/main/java/exnihilocreatio/util/TankUtil.java
 */
public class FluidHelper {

  private static final ItemStack WATER_BOTTLE;

  static {
    NBTTagCompound waterPotion = new NBTTagCompound();
    waterPotion.setString("Potion", "minecraft:water");
    WATER_BOTTLE = new ItemStack(Items.POTIONITEM, 1, 0);
    WATER_BOTTLE.setTagCompound(waterPotion);
  }

  public static boolean drainWaterIntoBottle(EntityPlayer player, IFluidHandler tank) {

    if (player.getHeldItemMainhand().getItem() == Items.GLASS_BOTTLE) {

      FluidStack drain = tank.drain(250, false);

      if (drain != null
          && drain.getFluid() != null
          && drain.amount == 250
          && drain.getFluid() == FluidRegistry.WATER) {

        if (player.addItemStackToInventory(WATER_BOTTLE.copy())) {

          if (!player.isCreative()) {
            player.getHeldItemMainhand().shrink(1);
          }

          tank.drain(250, true);
          return true;
        }
      }
    }

    return false;
  }

  public static boolean drainWaterFromBottle(EntityPlayer player, IFluidHandler fluidHandler) {

    if (player.getHeldItemMainhand().getItem() == Items.POTIONITEM
        && WATER_BOTTLE.getTagCompound() != null
        && WATER_BOTTLE.getTagCompound().equals(player.getHeldItemMainhand().getTagCompound())) {

      FluidStack water = new FluidStack(FluidRegistry.WATER, 250);

      if (fluidHandler.fill(water, false) == water.amount) {

        if (player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {

          if (!player.isCreative()) {
            player.getHeldItemMainhand().shrink(1);
          }

          fluidHandler.fill(water, true);
          return true;
        }
      }
    }

    return false;
  }

  private FluidHelper() {
    //
  }

}
