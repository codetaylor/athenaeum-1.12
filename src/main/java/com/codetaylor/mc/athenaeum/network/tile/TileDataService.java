package com.codetaylor.mc.athenaeum.network.tile;

import com.codetaylor.mc.athenaeum.network.IPacketService;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileData;
import com.codetaylor.mc.athenaeum.network.tile.spi.TileEntityDataContainerBase;
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

  private final ThreadLocal<List<TileDataTracker>> dataTrackerList;
  private final ThreadLocal<Map<TileEntityDataContainerBase, TileDataTracker>> dataTrackerMap;

  public TileDataService(int serviceId, IPacketService packetService) {

    this.serviceId = serviceId;
    this.packetService = packetService;

    this.dataTrackerList = ThreadLocal.withInitial(ArrayList::new);
    this.dataTrackerMap = ThreadLocal.withInitial(IdentityHashMap::new);
  }

  @Override
  public int getServiceId() {

    return this.serviceId;
  }

  @Override
  @Nullable
  public TileDataTracker getTracker(TileEntityDataContainerBase tile) {

    return this.dataTrackerMap.get().get(tile);
  }

  @Override
  public void register(TileEntityDataContainerBase tile, ITileData[] data) {

    if (data.length > 0) {
      Map<TileEntityDataContainerBase, TileDataTracker> map = this.dataTrackerMap.get();
      TileDataTracker tracker = map.get(tile);

      if (tracker == null) {
        tracker = new TileDataTracker(tile);
        this.dataTrackerList.get().add(tracker);
        map.put(tile, tracker);
      }

      tracker.addTileData(data);
    }
  }

  @Override
  public void update() {

    List<TileDataTracker> trackers = this.dataTrackerList.get();

    for (int i = 0; i < trackers.size(); i++) {

      TileDataTracker tracker = trackers.get(i);

      // TODO: Watch
      // I'm disabling this to see if the ThreadLocal solves these issues.
      /*
      if (tracker == null) {

        // TODO: How can this be null?

        // When placing a Compacting Bin, the game crashed because this value
        // was null. I don't yet understand how this can happen.

        // Could it be a threading issue?

        continue;
      }
      */

      TileEntityDataContainerBase tile = tracker.getTile();

      // --- Bookkeeping ---

      if (tile.isInvalid()) {
        // Move the last element to this position, remove the last element,
        // decrement the iteration index.
        trackers.set(i, trackers.get(trackers.size() - 1));
        trackers.remove(trackers.size() - 1);
        // Also remove the tile and tracker from the map.
        this.dataTrackerMap.get().remove(tile);
        i -= 1;
        continue;
      }

      // TODO: Watch
      // I'm disabling this to see if the ThreadLocal solves these issues.

      // 2019-01-21
      // A null pointer exception occurred with the following commented out,
      // even after switching to ThreadLocal. Could it be that creating the
      // bloom tile and not adding it to the world caused this? I would think
      // that if that were the case, the crash would have happened consistently
      // after first creating that bloom logic.

      // 2019-02-05
      // This could be caused by making tile data dirty during a call to the
      // tile's readFromNBT method. I was able to consistently reproduce this
      // crash when doing so.

      // 2019-02-21
      // Crashed again with a null world in the sendToDimension call down below.

      //noinspection ConstantConditions
      if (tile.getWorld() == null) {
        // Rarely the game will crash because the TE has a null world in the call
        // to sendToDimension below.
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
