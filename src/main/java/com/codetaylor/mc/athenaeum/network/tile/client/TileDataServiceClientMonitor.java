package com.codetaylor.mc.athenaeum.network.tile.client;

import com.codetaylor.mc.athenaeum.ModAthenaeumConfig;
import com.codetaylor.mc.athenaeum.network.tile.TileDataServiceLogger;
import com.codetaylor.mc.athenaeum.network.tile.TileDataTracker;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileData;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(value = Side.CLIENT)
@SideOnly(Side.CLIENT)
public class TileDataServiceClientMonitor {

  private static final short TOTAL_INTERVAL_COUNT = 2 * 60;
  private static final int CACHE_CLEANUP_INTERVAL_TICKS = 10 * 20;

  private static final TileDataTrackerUpdateMonitor TRACKER_UPDATE_MONITOR;

  /**
   * Monitors all network traffic for all tile data services.
   */
  public static final TileDataServiceClientMonitor TOTAL;

  /**
   * Monitors network traffic, indexed by world position.
   */
  private static final LoadingCache<BlockPos, TileDataServiceClientMonitor> PER_POS_TOTAL;

  private static final CacheLoader<BlockPos, TileDataServiceClientMonitor> CACHE_LOADER = new CacheLoader<BlockPos, TileDataServiceClientMonitor>() {

    public TileDataServiceClientMonitor load(@Nonnull BlockPos pos) {

      return new TileDataServiceClientMonitor(() -> ModAthenaeumConfig.TILE_DATA_SERVICE.UPDATE_INTERVAL_TICKS, TOTAL_INTERVAL_COUNT);
    }
  };

  private static int cacheCleanupCounter;

  static {
    TRACKER_UPDATE_MONITOR = new TileDataTrackerUpdateMonitor();

    TOTAL = new TileDataServiceClientMonitor(() -> ModAthenaeumConfig.TILE_DATA_SERVICE.UPDATE_INTERVAL_TICKS, TOTAL_INTERVAL_COUNT);

    PER_POS_TOTAL = CacheBuilder.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(10, TimeUnit.MINUTES)
        .build(CACHE_LOADER);
  }

  // ---------------------------------------------------------------------------
  // - Events
  // ---------------------------------------------------------------------------

  @SubscribeEvent
  public static void onEvent(TickEvent.ClientTickEvent event) {

    // Update all the monitors when the client ticks, limit phase so it only
    // updates once.

    if (event.phase == TickEvent.Phase.START) {

      if (!Minecraft.getMinecraft().isGamePaused()
          && ModAthenaeumConfig.TILE_DATA_SERVICE.ENABLED) {
        TOTAL.update();

        for (TileDataServiceClientMonitor value : PER_POS_TOTAL.asMap().values()) {
          value.update();
        }

        TRACKER_UPDATE_MONITOR.update();
      }

      cacheCleanupCounter += 1;

      if (cacheCleanupCounter >= CACHE_CLEANUP_INTERVAL_TICKS) {
        cacheCleanupCounter = 0;
        PER_POS_TOTAL.cleanUp();
      }
    }
  }

  /**
   * Called when a packet from the tile entity data service is received on
   * the client.
   *
   * @param tracker the tracker that received the packet
   * @param pos     the pos of the TE
   * @param size    the size of the packet's TE update buffer in bytes
   */
  public static void onClientPacketReceived(TileDataTracker tracker, BlockPos pos, int size) {

    if (ModAthenaeumConfig.TILE_DATA_SERVICE.ENABLED) {

      // --- Total ---

      TOTAL.receiveBytes(size);

      // --- Per Position ---

      TileDataServiceClientMonitor monitor = null;

      try {
        monitor = PER_POS_TOTAL.get(pos);

      } catch (ExecutionException e) {
        TileDataServiceLogger.LOGGER.error("", e);
      }

      if (monitor != null) {
        monitor.receiveBytes(size);
      }
    }
  }

  public static void onClientTrackerUpdateReceived(BlockPos pos, Class<? extends ITileData> tileDataClass) {

    if (ModAthenaeumConfig.TILE_DATA_SERVICE.ENABLED) {
      TRACKER_UPDATE_MONITOR.onClientTrackerUpdateReceived(pos, tileDataClass);
    }
  }

  // ---------------------------------------------------------------------------
  // - Static Accessors
  // ---------------------------------------------------------------------------

  @Nullable
  public static TileDataServiceClientMonitor findMonitorForPosition(BlockPos pos) {

    if (PER_POS_TOTAL.asMap().containsKey(pos)) {

      try {
        return PER_POS_TOTAL.get(pos);

      } catch (ExecutionException e) {
        TileDataServiceLogger.LOGGER.error("", e);
      }
    }

    return null;
  }

  public static TileDataTrackerUpdateMonitor getTrackerUpdateMonitor() {

    return TRACKER_UPDATE_MONITOR;
  }

  // ---------------------------------------------------------------------------
  // - Monitor Instance
  // ---------------------------------------------------------------------------

  private final IntArrayList totalBytesReceivedPerSecond;
  private final UpdateIntervalProvider updateIntervalTicks;
  private final int totalIntervalCount;

  private int totalBytesReceived;
  private short tickCounter;

  public TileDataServiceClientMonitor(UpdateIntervalProvider updateIntervalTicks, short totalIntervalCount) {

    totalBytesReceivedPerSecond = new IntArrayList(totalIntervalCount);
    this.updateIntervalTicks = updateIntervalTicks;
    this.totalIntervalCount = totalIntervalCount;
  }

  /**
   * Call once per tick to update the monitor.
   */
  public void update() {

    this.tickCounter += 1;

    if (this.tickCounter >= this.updateIntervalTicks.getUpdateInterval()) {
      this.tickCounter = 0;
      this.totalBytesReceivedPerSecond.add(0, this.totalBytesReceived);
      this.totalBytesReceived = 0;

      if (this.totalBytesReceivedPerSecond.size() > this.totalIntervalCount) {
        this.totalBytesReceivedPerSecond.removeInt(this.totalBytesReceivedPerSecond.size() - 1);
      }
    }
  }

  private void receiveBytes(int size) {

    this.totalBytesReceived += size;
  }

  public int size() {

    return this.totalBytesReceivedPerSecond.size();
  }

  public int get(int index) {

    return this.totalBytesReceivedPerSecond.getInt(index);
  }

  public int getTotalIntervalCount() {

    return this.totalIntervalCount;
  }

  interface UpdateIntervalProvider {

    short getUpdateInterval();
  }

}
