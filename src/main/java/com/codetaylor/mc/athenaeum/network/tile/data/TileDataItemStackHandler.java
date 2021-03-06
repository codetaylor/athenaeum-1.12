package com.codetaylor.mc.athenaeum.network.tile.data;

import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import com.codetaylor.mc.athenaeum.network.tile.spi.TileDataBase;
import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.ItemStackHandler;

import java.io.IOException;
import java.util.BitSet;

public class TileDataItemStackHandler<H extends ItemStackHandler & ITileDataItemStackHandler>
    extends TileDataBase {

  private H stackHandler;
  private BitSet dirtySlots;

  public TileDataItemStackHandler(H stackHandler) {

    this(stackHandler, 1);
    this.dirtySlots = new BitSet(stackHandler.getSlots());
  }

  public TileDataItemStackHandler(H stackHandler, int updateInterval) {

    super(updateInterval);
    this.stackHandler = stackHandler;
    this.stackHandler.addObserver((handler, slot) -> {
      this.setDirty(true);
      this.dirtySlots.set(slot);
    });
    this.setDirty(true);
  }

  public ItemStackHandler getStackHandler() {

    return this.stackHandler;
  }

  @Override
  public void setDirty(boolean dirty) {

    super.setDirty(dirty);

    if (!dirty) {
      this.dirtySlots.clear();
    }
  }

  @Override
  public void read(PacketBuffer buffer) throws IOException {

    int dirtyCount = buffer.readInt();

    for (int i = 0; i < dirtyCount; i++) {
      int slot = buffer.readInt();
      boolean clear = buffer.readBoolean();

      if (clear) {
        this.stackHandler.setStackInSlot(slot, ItemStack.EMPTY);

      } else {
        this.stackHandler.setStackInSlot(slot, this.readItemStack(buffer));
      }
    }
  }

  protected ItemStack readItemStack(PacketBuffer buffer) throws IOException {

    return new ItemStack(Preconditions.checkNotNull(buffer.readCompoundTag()));
  }

  @Override
  public void write(PacketBuffer buffer) {

    final int dirtyCount = this.dirtySlots.cardinality();

    buffer.writeInt(dirtyCount);

    if (dirtyCount > 0) {

      for (int i = this.dirtySlots.nextSetBit(0); i >= 0; i = this.dirtySlots.nextSetBit(i + 1)) {
        buffer.writeInt(i);
        ItemStack itemStack = this.stackHandler.getStackInSlot(i);

        buffer.writeBoolean(itemStack.isEmpty());

        if (!itemStack.isEmpty()) {
          this.writeItemStack(buffer, itemStack);
        }
      }
    }
  }

  protected void writeItemStack(PacketBuffer buffer, ItemStack itemStack) {

    buffer.writeCompoundTag(itemStack.serializeNBT());
  }

}
