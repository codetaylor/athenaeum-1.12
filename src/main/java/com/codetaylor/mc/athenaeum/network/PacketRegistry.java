package com.codetaylor.mc.athenaeum.network;

import com.codetaylor.mc.athenaeum.network.packet.IMessage;
import com.codetaylor.mc.athenaeum.network.packet.IMessageHandler;

public class PacketRegistry
    implements IPacketRegistry {

  private PacketService packetService;

  private int id = 0;

  public PacketRegistry(PacketService packetService) {

    this.packetService = packetService;
  }

  @Override
  public <Q extends IMessage, A extends IMessage> IPacketRegistry register(
      Class<? extends IMessageHandler<Q, A>> messageHandler,
      Class<Q> requestMessageType
  ) {

    this.packetService.registerMessage(messageHandler, requestMessageType, this.nextId());
    return this;
  }

  private int nextId() {

    return this.id++;
  }

}