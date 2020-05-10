package com.codetaylor.mc.athenaeum.network.spi.tiledata.service;

import com.codetaylor.mc.athenaeum.network.spi.packet.CPacketTileEntityBase;
import com.codetaylor.mc.athenaeum.network.spi.packet.IMessage;
import com.codetaylor.mc.athenaeum.network.spi.tiledata.TileDataContainerBase;
import com.codetaylor.mc.athenaeum.network.tile.TileDataServiceContainer;
import com.codetaylor.mc.athenaeum.network.tile.TileDataServiceLogger;
import com.codetaylor.mc.athenaeum.network.tile.TileDataTracker;
import com.codetaylor.mc.athenaeum.network.tile.client.TileDataServiceClientMonitor;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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
  public SCPacketTileData decode(SCPacketTileData message, PacketBuffer packetBuffer) {

    super.decode(message, packetBuffer);
    message.serviceId = packetBuffer.readInt();
    int size = packetBuffer.readInt();
    message.buffer = new PacketBuffer(Unpooled.buffer(size));
    packetBuffer.readBytes(message.buffer, size);
    return message;
  }

  @Override
  public void encode(SCPacketTileData message, PacketBuffer packetBuffer) {

    super.encode(message, packetBuffer);
    packetBuffer.writeInt(message.serviceId);
    packetBuffer.writeInt(message.buffer.writerIndex());
    packetBuffer.writeBytes(message.buffer);
  }

  @Override
  protected IMessage onMessage(SCPacketTileData message, Supplier<NetworkEvent.Context> contextSupplier, TileEntity tileEntity) {

    if (tileEntity instanceof TileDataContainerBase) {

      ITileDataService dataService = TileDataServiceContainer.find(message.serviceId);

      if (dataService != null) {
        TileDataContainerBase tile = (TileDataContainerBase) tileEntity;
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

