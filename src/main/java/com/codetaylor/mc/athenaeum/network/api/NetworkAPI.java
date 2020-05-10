package com.codetaylor.mc.athenaeum;

import com.codetaylor.mc.athenaeum.network.IPacketService;
import com.codetaylor.mc.athenaeum.network.PacketService;
import com.codetaylor.mc.athenaeum.network.tile.ITileDataService;
import com.codetaylor.mc.athenaeum.network.tile.TileDataServiceContainer;
import net.minecraft.util.ResourceLocation;

public final class AthenaeumAPI {

  public static IPacketService createPacketService(String modId, String channelName, String protocolVersion) {

    return PacketService.create(modId, channelName, protocolVersion);
  }

  public static ITileDataService createTileDataService(String modId, String serviceName, IPacketService packetService) {

    return TileDataServiceContainer.register(new ResourceLocation(modId, serviceName), packetService);
  }
}
