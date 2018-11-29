package com.codetaylor.mc.athenaeum.network.tile;

import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public interface ITileData {

  boolean isDirty();

  void setDirty(boolean dirty);

  void forceUpdate();

  void update();

  void read(PacketBuffer buffer) throws IOException;

  void write(PacketBuffer buffer);
}
