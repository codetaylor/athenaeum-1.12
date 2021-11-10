package com.codetaylor.mc.athenaeum.network.tile.data;

import com.codetaylor.mc.athenaeum.network.tile.spi.TileDataBase;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public class TileDataString
    extends TileDataBase {

  private String value = "";

  public TileDataString(@Nonnull String initialValue) {

    this(initialValue, 1);
  }

  public TileDataString(@Nonnull String initialValue, int updateInterval) {

    super(updateInterval);
    this.set(initialValue);
  }

  public void set(@Nonnull String value) {

    if (!this.value.equals(value)) {
      this.value = value;
      this.setDirty(true);
    }
  }

  public String get() {

    return this.value;
  }

  @Override
  public void read(PacketBuffer buffer) {

    int length = buffer.readInt();
    this.value = buffer.readString(length);
  }

  @Override
  public void write(PacketBuffer buffer) {

    buffer.writeInt(this.value.length());
    buffer.writeString(this.value);
  }

}
