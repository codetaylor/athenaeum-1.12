package com.codetaylor.mc.athenaeum.network.api;

import com.codetaylor.mc.athenaeum.network.spi.packet.IPacketService;
import com.codetaylor.mc.athenaeum.internal.network.packet.PacketService;
import com.codetaylor.mc.athenaeum.network.spi.tile.data.service.ITileDataService;
import com.codetaylor.mc.athenaeum.internal.network.tile.TileDataServiceContainer;
import net.minecraft.util.ResourceLocation;

public final class NetworkAPI {

  public static IPacketService createPacketService(String modId, String channelName, String protocolVersion) {

    return PacketService.create(modId, channelName, protocolVersion);
  }

  public static ITileDataService createTileDataService(String modId, String serviceName, IPacketService packetService) {

    return TileDataServiceContainer.register(new ResourceLocation(modId, serviceName), packetService);
  }
}
