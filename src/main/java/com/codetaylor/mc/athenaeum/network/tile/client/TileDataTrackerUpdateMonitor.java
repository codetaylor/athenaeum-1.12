package com.codetaylor.mc.athenaeum.network.tile.client;

import com.codetaylor.mc.athenaeum.network.tile.spi.ITileData;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.util.math.BlockPos;

/* package */ class TileDataTrackerUpdateMonitor {

  private static final int TRACKER_UPDATE_INTERVAL_TICKS = 20;

  /**
   * Contains tracker update counts, indexed by world position.
   */
  private final Object2ObjectArrayMap<BlockPos, Object2IntArrayMap<Class>> privateTrackerUpdateMap = new Object2ObjectArrayMap<>();

  /**
   * Public tracker, updated from the private tracker on interval.
   */
  private final Object2ObjectArrayMap<BlockPos, Object2IntArrayMap<Class>> publicTrackerUpdateMap = new Object2ObjectArrayMap<>();

  private int updateCounter;

  void update() {

    this.updateCounter += 1;

    if (this.updateCounter >= TRACKER_UPDATE_INTERVAL_TICKS) {
      this.updateCounter = 0;

      this.publicTrackerUpdateMap.clear();

      ObjectIterator<Object2ObjectMap.Entry<BlockPos, Object2IntArrayMap<Class>>> iterator;
      iterator = this.privateTrackerUpdateMap.object2ObjectEntrySet().fastIterator();

      while (iterator.hasNext()) {
        Object2ObjectMap.Entry<BlockPos, Object2IntArrayMap<Class>> entry = iterator.next();
        this.publicTrackerUpdateMap.put(entry.getKey(), entry.getValue());
      }

      this.privateTrackerUpdateMap.clear();
    }
  }

  /* package */ void onClientTrackerUpdateReceived(BlockPos pos, Class<? extends ITileData> tileDataClass) {

    Object2IntArrayMap<Class> map = this.privateTrackerUpdateMap.computeIfAbsent(pos, blockPos -> {
      Object2IntArrayMap<Class> newMap = new Object2IntArrayMap<>();
      newMap.defaultReturnValue(0);
      return newMap;
    });
    map.put(tileDataClass, map.getInt(tileDataClass) + 1);
  }

  Object2ObjectArrayMap<BlockPos, Object2IntArrayMap<Class>> getPublicTrackerUpdateMap() {

    return this.publicTrackerUpdateMap;
  }
}
