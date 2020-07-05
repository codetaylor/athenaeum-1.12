package com.codetaylor.mc.athenaeum.network.tile;

import com.codetaylor.mc.athenaeum.network.tile.client.TileDataServiceClientMonitor;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileData;
import com.codetaylor.mc.athenaeum.network.tile.spi.TileEntityDataContainerBase;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileDataTracker {

  private final TileEntityDataContainerBase tile;
  private final PacketBuffer packetBuffer;

  private ArrayList<ITileData> data;

  /* package */ TileDataTracker(TileEntityDataContainerBase tile) {

    this.tile = tile;
    this.packetBuffer = new PacketBuffer(Unpooled.buffer());
    this.data = new ArrayList<>(1);
  }

  /* package */ void addTileData(ITileData[] toAdd) {

    //noinspection unchecked
    this.data.addAll(Arrays.asList(toAdd));
    this.data.trimToSize();
  }

  public TileEntityDataContainerBase getTile() {

    return this.tile;
  }

  /**
   * Called once per tick on the server.
   * <p>
   * Returns a packet buffer containing the serialized bytes of only the data
   * that has updated. If no data has updated, an empty buffer is returned.
   */
  /* package */ PacketBuffer getUpdateBuffer() {

    int dirtyCount = 0;

    for (int i = 0; i < this.data.size(); i++) {

      this.data.get(i).update();

      if (this.data.get(i).isDirty()) {
        dirtyCount += 1;
      }
    }

    this.packetBuffer.clear();

    if (dirtyCount > 0) {
      this.packetBuffer.writeInt(dirtyCount);

      for (int i = 0; i < this.data.size(); i++) {

        if (this.data.get(i).isDirty()) {
          this.packetBuffer.writeInt(i);
          this.data.get(i).write(this.packetBuffer);
          this.data.get(i).setDirty(false);
        }
      }
    }

    return this.packetBuffer;
  }

  /**
   * Called when an update packet arrives on the client.
   *
   * 2020-07-04
   * This method has been modified to clear the dirty flag on all the data
   * elements that a TE has instead of only clearing data elements that have
   * arrived on the client from the server. This prevents a case in which a
   * client reads a TE from NBT and triggers the dirty flag on something like
   * an item stack handler, then the flag never gets cleared and any code that
   * is meant to run only in the event of a change gets run every tick instead.
   *
   * @param buffer the update buffer
   */
  @SideOnly(Side.CLIENT)
  /* package */ void updateClient(PacketBuffer buffer) throws IOException {

    int dirtyCount = buffer.readInt();

    if (dirtyCount > 0) {

      // Deserialize buffer and stash updated entries.
      for (int i = 0; i < dirtyCount; i++) {
        ITileData data = this.data.get(buffer.readInt());
        data.read(buffer);
        data.setDirty(true);
        TileDataServiceClientMonitor.onClientTrackerUpdateReceived(this.tile.getPos(), data.getClass());
      }

      // Notify the tile that data was updated.
      this.tile.onTileDataUpdate();

      // Clear the dirty flag on updated data; clear the stash at the same time.
      for (int i = this.data.size() - 1; i >= 0; i--) {
        this.data.get(i).setDirty(false);
      }
    }
  }

}
