package com.codetaylor.mc.athenaeum.network.tile.spi;

import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the base tile data element.
 * <p>
 * It implements the default, expected behavior of tile data elements and all
 * tile data elements should extend it.
 */
public abstract class TileDataBase
    implements ITileData {

  public interface IChangeObserver<D extends TileDataBase> {

    void onDirtyStateChanged(D data);

    class OnDirtyMarkTileDirty<D extends TileDataBase>
        implements IChangeObserver<D> {

      private final TileEntity tile;

      public OnDirtyMarkTileDirty(TileEntity tile) {

        this.tile = tile;
      }

      @Override
      public void onDirtyStateChanged(D data) {

        if (data.isDirty()) {
          this.tile.markDirty();
        }
      }
    }
  }

  private final int updateInterval;
  private int updateCounter;
  private boolean dirty;
  private boolean forceUpdate;
  private List<IChangeObserver> changeObservers;

  protected TileDataBase(int updateInterval) {

    this.updateInterval = updateInterval;
  }

  public void addChangeObserver(IChangeObserver observer) {

    if (this.changeObservers == null) {
      this.changeObservers = new ArrayList<>(1);
    }

    this.changeObservers.add(observer);
  }

  @Override
  public void setDirty(boolean dirty) {

    boolean changed = (this.dirty != dirty);

    this.dirty = dirty;

    if (this.changeObservers != null
        && changed) {
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0; i < this.changeObservers.size(); i++) {
        //noinspection unchecked
        this.changeObservers.get(i).onDirtyStateChanged(this);
      }
    }
  }

  @Override
  public boolean isDirty() {

    return this.dirty && (this.updateCounter == 0);
  }

  @Override
  public void forceUpdate() {

    this.forceUpdate = true;
  }

  @Override
  public void update() {

    if (this.forceUpdate) {
      this.updateCounter = 0;
      this.forceUpdate = false;

    } else {
      this.updateCounter += 1;

      if (this.updateCounter >= this.updateInterval) {
        this.updateCounter = 0;
      }
    }
  }
}
