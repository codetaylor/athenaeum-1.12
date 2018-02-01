package com.codetaylor.mc.athenaeum.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * https://github.com/BloodWorkXGaming/ExNihiloCreatio/blob/1.12/src/main/java/exnihilocreatio/util/TankUtil.java
 */
public class BottleHelper {

  private static final ItemStack WATER_BOTTLE;

  static {
    NBTTagCompound waterPotion = new NBTTagCompound();
    waterPotion.setString("Potion", "minecraft:water");
    WATER_BOTTLE = new ItemStack(Items.POTIONITEM, 1, 0);
    WATER_BOTTLE.setTagCompound(waterPotion);
  }

  public static boolean drainWaterIntoBottle(TileEntity tileEntity, EntityPlayer player, FluidTank tank) {

    if (tileEntity.getWorld().isRemote) {
      throw new IllegalStateException("Don't call this on the client");
    }

    if (player.getHeldItemMainhand().getItem() == Items.GLASS_BOTTLE) {

      if (tank.getFluid() != null
          && tank.getFluidAmount() >= 250
          && tank.getFluid().getFluid() == FluidRegistry.WATER) {

        if (player.addItemStackToInventory(WATER_BOTTLE.copy())) {

          if (!player.isCreative()) {
            player.getHeldItemMainhand().shrink(1);
          }

          tank.drain(250, true);

          BlockHelper.notifyBlockUpdate(tileEntity.getWorld(), tileEntity.getPos());
          return true;
        }
      }
    }

    return false;
  }

  public static boolean drainWaterFromBottle(TileEntity tileEntity, EntityPlayer player, FluidTank tank) {

    if (tileEntity.getWorld().isRemote) {
      throw new IllegalStateException("Don't call this on the client");
    }

    if (player.getHeldItemMainhand().getItem() == Items.POTIONITEM
        && WATER_BOTTLE.getTagCompound().equals(player.getHeldItemMainhand().getTagCompound())) {

      FluidStack water = new FluidStack(FluidRegistry.WATER, 250);

      if (tank.fill(water, false) == water.amount) {

        if (player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {

          if (!player.isCreative()) {
            player.getHeldItemMainhand().shrink(1);
          }

          tank.fill(water, true);

          BlockHelper.notifyBlockUpdate(tileEntity.getWorld(), tileEntity.getPos());
          return true;
        }
      }
    }

    return false;
  }

  private BottleHelper() {
    //
  }

}
