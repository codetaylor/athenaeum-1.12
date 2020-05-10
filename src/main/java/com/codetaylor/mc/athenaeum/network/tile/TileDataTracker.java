package com.codetaylor.mc.athenaeum.network.tile;

import com.codetaylor.mc.athenaeum.network.tile.client.TileDataServiceClientMonitor;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileData;
import com.codetaylor.mc.athenaeum.network.tile.spi.TileDataContainerBase;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileDataTracker {

  private final TileDataContainerBase tile;
  private final PacketBuffer packetBuffer;

  private ArrayList<ITileData> data;

  /**
   * Temporarily stores data entries to pass to the tile's update method.
   */
  private List<ITileData> toUpdate;

  /* package */ TileDataTracker(TileDataContainerBase tile) {

    this.tile = tile;
    this.packetBuffer = new PacketBuffer(Unpooled.buffer());
    this.data = new ArrayList<>(1);
    this.toUpdate = new ArrayList<>(1);
  }

  /* package */ void addTileData(ITileData[] toAdd) {

    //noinspection unchecked
    this.data.addAll(Arrays.asList(toAdd));
    this.data.trimToSize();
    this.toUpdate = new ArrayList<>(this.data.size());
  }

  public TileDataContainerBase getTile() {

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
   * @param buffer the update buffer
   */
  @OnlyIn(Dist.CLIENT)
  /* package */ void updateClient(PacketBuffer buffer) throws IOException {

    int dirtyCount = buffer.readInt();

    if (dirtyCount > 0) {

      // Deserialize buffer and stash updated entries.
      for (int i = 0; i < dirtyCount; i++) {
        ITileData data = this.data.get(buffer.readInt());
        data.read(buffer);
        data.setDirty(true);
        this.toUpdate.add(data);
        TileDataServiceClientMonitor.onClientTrackerUpdateReceived(this.tile.getPos(), data.getClass());
      }

      // Notify the tile that data was updated.
      this.tile.onTileDataUpdate();

      // Clear the dirty flag on updated data; clear the stash at the same time.
      for (int i = this.toUpdate.size() - 1; i >= 0; i--) {
        this.toUpdate.remove(i).setDirty(false);
      }
    }
  }

}
