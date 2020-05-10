package com.codetaylor.mc.athenaeum.network.spi.tile;

import com.codetaylor.mc.athenaeum.inventory.spi.IObservableEnergyStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Energy storage data elements need to implement the
 * {@link IObservableEnergyStorage} interface.
 */
public interface ITileDataEnergyStorage
    extends IObservableEnergyStorage,
    INBTSerializable<CompoundNBT> {

}
