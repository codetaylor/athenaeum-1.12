package com.codetaylor.mc.athenaeum.network.tile.spi;

import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public interface ITileData {

  /**
   * @return true if this data needs to be serialized
   */
  boolean isDirty();

  /**
   * Sets the dirty flag of this data.
   *
   * @param dirty the dirty flag
   */
  void setDirty(boolean dirty);

  /**
   * If this is called at any time, this data is guaranteed to serialize during
   * the next update, regardless of the dirty flag.
   */
  void forceUpdate();

  /**
   * Called on the server every tick to update this data's internal state.
   */
  void update();

  /**
   * Reads data from the given buffer.
   *
   * @param buffer the buffer to read
   * @throws IOException on failure
   */
  void read(PacketBuffer buffer) throws IOException;

  /**
   * Writes data the given buffer.
   *
   * @param buffer the buffer to write to
   */
  void write(PacketBuffer buffer);
}
