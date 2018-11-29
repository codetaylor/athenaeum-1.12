package com.codetaylor.mc.athenaeum.network.tile.data;

import com.codetaylor.mc.athenaeum.network.tile.TileDataBase;
import net.minecraft.network.PacketBuffer;

public class TileDataInteger
    extends TileDataBase {

  private int value;

  public TileDataInteger(int initialValue) {

    this(initialValue, 1);
  }

  public TileDataInteger(int initialValue, int updateInterval) {

    super(updateInterval);
    this.setValue(initialValue);
  }

  public void setValue(int value) {

    this.value = value;
    this.setDirty(true);
  }

  public int getValue() {

    return this.value;
  }

  public int add(int value) {

    this.value += value;
    return this.value;
  }

  @Override
  public void read(PacketBuffer buffer) {

    this.value = buffer.readInt();
  }

  @Override
  public void write(PacketBuffer buffer) {

    buffer.writeInt(this.value);
  }

}
