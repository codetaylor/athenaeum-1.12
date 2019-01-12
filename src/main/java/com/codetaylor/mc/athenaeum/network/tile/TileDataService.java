package com.codetaylor.mc.athenaeum.network.tile;

import com.codetaylor.mc.athenaeum.network.IPacketService;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileData;
import com.codetaylor.mc.athenaeum.network.tile.spi.TileDataContainerBase;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class TileDataService
    implements ITileDataService {

  private final int serviceId;
  private final IPacketService packetService;

  private final List<TileDataTracker> dataTrackerList;
  private final Map<TileDataContainerBase, TileDataTracker> dataTrackerMap;

  public TileDataService(int serviceId, IPacketService packetService) {

    this.serviceId = serviceId;
    this.packetService = packetService;
    this.dataTrackerList = new ArrayList<>();
    this.dataTrackerMap = new IdentityHashMap<>();
  }

  @Override
  public int getServiceId() {

    return this.serviceId;
  }

  @Override
  @Nullable
  public TileDataTracker getTracker(TileDataContainerBase tile) {

    return this.dataTrackerMap.get(tile);
  }

  @Override
  public void register(TileDataContainerBase tile, ITileData[] data) {

    if (data.length > 0) {

      TileDataTracker tracker = this.dataTrackerMap.get(tile);

      if (tracker == null) {
        tracker = new TileDataTracker(tile);
        this.dataTrackerList.add(tracker);
        this.dataTrackerMap.put(tile, tracker);
      }

      tracker.addTileData(data);
    }
  }

  @Override
  public void update() {

    for (int i = 0; i < this.dataTrackerList.size(); i++) {

      TileDataTracker tracker = this.dataTrackerList.get(i);
      TileDataContainerBase tile = tracker.getTile();

      // --- Bookkeeping ---

      if (tile.isInvalid()) {
        // Move the last element to this position, remove the last element,
        // decrement the iteration index.
        this.dataTrackerList.set(i, this.dataTrackerList.get(this.dataTrackerList.size() - 1));
        this.dataTrackerList.remove(this.dataTrackerList.size() - 1);
        i -= 1;
        continue;
      }

      //noinspection ConstantConditions
      if (tile.getWorld() == null) {
        // Rarely does the tile not have a world yet when first placed.
        // This should postpone the initial update until the tile has a world.
        continue;
      }

      // --- Update Packet ---

      PacketBuffer updateBuffer = tracker.getUpdateBuffer();

      if (updateBuffer.writerIndex() > 0) {

        SCPacketTileData packet = new SCPacketTileData(this.serviceId, tile.getPos(), updateBuffer);
        this.packetService.sendToDimension(packet, tile);
      }
    }
  }

}
