package com.codetaylor.mc.athenaeum.network.tile.data;

import com.codetaylor.mc.athenaeum.network.tile.spi.TileDataBase;
import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class TileDataItemStack
    extends TileDataBase {

  private ItemStack itemStack;

  public TileDataItemStack(ItemStack itemStack) {

    this(itemStack, 1);
  }

  public TileDataItemStack(ItemStack itemStack, int updateInterval) {

    super(updateInterval);
    this.itemStack = Preconditions.checkNotNull(itemStack);
  }

  public void set(ItemStack itemStack) {

    if (this.itemStack != itemStack) {
      this.itemStack = itemStack;
      this.setDirty(true);
    }
  }

  public ItemStack get() {

    return this.itemStack;
  }

  @Override
  public void read(PacketBuffer buffer) throws IOException {

    this.itemStack = new ItemStack(Preconditions.checkNotNull(buffer.readCompoundTag()));
  }

  @Override
  public void write(PacketBuffer buffer) {

    buffer.writeCompoundTag(this.itemStack.serializeNBT());
  }
}
