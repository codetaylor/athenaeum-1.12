package com.codetaylor.mc.athenaeum.network.tile;

import com.codetaylor.mc.athenaeum.network.tile.client.TileDataServiceClientMonitor;
import com.codetaylor.mc.athenaeum.network.tile.spi.TileEntityDataContainerBase;
import com.codetaylor.mc.athenaeum.spi.packet.CPacketTileEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SCPacketTileData
    extends CPacketTileEntityBase<SCPacketTileData> {

  private int serviceId;
  private PacketBuffer buffer;

  @SuppressWarnings("unused")
  public SCPacketTileData() {
    // serialization
  }

  public SCPacketTileData(int serviceId, BlockPos origin, PacketBuffer buffer) {

    super(origin);
    this.serviceId = serviceId;
    this.buffer = buffer;
  }

  @Override
  public void fromBytes(ByteBuf buf) {

    super.fromBytes(buf);

    this.serviceId = buf.readInt();
    int size = buf.readInt();
    this.buffer = new PacketBuffer(Unpooled.buffer(size));
    buf.readBytes(this.buffer, size);
  }

  @Override
  public void toBytes(ByteBuf buf) {

    super.toBytes(buf);

    buf.writeInt(this.serviceId);
    buf.writeInt(this.buffer.writerIndex());
    buf.writeBytes(this.buffer);
  }

  @SideOnly(Side.CLIENT)
  @Override
  protected IMessage onMessage(SCPacketTileData message, MessageContext ctx, TileEntity tileEntity) {

    if (tileEntity instanceof TileEntityDataContainerBase) {

      ITileDataService dataService = TileDataServiceContainer.find(message.serviceId);

      if (dataService != null) {
        TileEntityDataContainerBase tile = (TileEntityDataContainerBase) tileEntity;
        TileDataTracker tracker = dataService.getTracker(tile);

        if (tracker != null) {

          try {
            tracker.updateClient(message.buffer);
            TileDataServiceClientMonitor.onClientPacketReceived(tracker, message.blockPos, message.buffer.writerIndex());

          } catch (Exception e) {
            TileDataServiceLogger.LOGGER.error("", e);
          }
        }
      }
    }

    return null;
  }
}

