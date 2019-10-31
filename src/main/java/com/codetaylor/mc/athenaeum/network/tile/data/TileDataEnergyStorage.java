package com.codetaylor.mc.athenaeum.network.tile.data;

import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataEnergyStorage;
import com.codetaylor.mc.athenaeum.network.tile.spi.TileDataBase;
import com.google.common.base.Preconditions;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.energy.IEnergyStorage;

import java.io.IOException;

public class TileDataEnergyStorage<T extends IEnergyStorage & ITileDataEnergyStorage>
    extends TileDataBase {

  private T energyStorage;

  public TileDataEnergyStorage(T energyStorage) {

    this(energyStorage, 1);
  }

  public TileDataEnergyStorage(T energyStorage, int updateInterval) {

    super(updateInterval);
    this.energyStorage = energyStorage;
    this.energyStorage.addObserver((handler, slot) -> this.setDirty(true));
    this.setDirty(true);
  }

  public IEnergyStorage getEnergyStorage() {

    return this.energyStorage;
  }

  @Override
  public void setDirty(boolean dirty) {

    super.setDirty(dirty);
  }

  @Override
  public void read(PacketBuffer buffer) throws IOException {

    NBTTagCompound compound = Preconditions.checkNotNull(buffer.readCompoundTag());
    this.energyStorage.deserializeNBT(compound);
  }

  @Override
  public void write(PacketBuffer buffer) {

    buffer.writeCompoundTag(this.energyStorage.serializeNBT());
  }
}
