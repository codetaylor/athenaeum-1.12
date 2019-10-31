package com.codetaylor.mc.athenaeum.network.tile.spi;

import com.codetaylor.mc.athenaeum.inventory.IObservableEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Energy storage data elements need to implement the
 * {@link IObservableEnergyStorage} interface.
 */
public interface ITileDataEnergyStorage
    extends IObservableEnergyStorage,
    INBTSerializable<NBTTagCompound> {

}
