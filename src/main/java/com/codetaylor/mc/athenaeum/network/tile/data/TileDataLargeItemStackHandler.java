package com.codetaylor.mc.athenaeum.network.tile.data;

import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import com.codetaylor.mc.athenaeum.util.StackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.ItemStackHandler;

import java.io.IOException;

/**
 * Normal stack serialization reduces the stack's count to a byte: [-127,127].
 * <p>
 * This supports syncing stack handler's with item stacks that have a count
 * larger than a byte. Specifically, uses an extra int during stack
 * serialization.
 *
 * @param <H>
 */
public class TileDataLargeItemStackHandler<H extends ItemStackHandler & ITileDataItemStackHandler>
    extends TileDataItemStackHandler<H> {

  public TileDataLargeItemStackHandler(H stackHandler) {

    super(stackHandler);
  }

  public TileDataLargeItemStackHandler(H stackHandler, int updateInterval) {

    super(stackHandler, updateInterval);
  }

  @Override
  protected ItemStack readItemStack(PacketBuffer buffer) throws IOException {

    return StackHelper.readLargeItemStack(buffer.readCompoundTag());
  }

  @Override
  protected void writeItemStack(PacketBuffer buffer, ItemStack itemStack) {

    buffer.writeCompoundTag(StackHelper.writeLargeItemStack(itemStack, new NBTTagCompound()));
  }
}
