package com.codetaylor.mc.athenaeum.network;

import com.codetaylor.mc.athenaeum.network.packet.IMessage;
import com.codetaylor.mc.athenaeum.network.packet.IMessageHandler;

public interface IPacketRegistry {

  <Q extends IMessage, A extends IMessage> IPacketRegistry register(
      Class<? extends IMessageHandler<Q, A>> messageHandler,
      Class<Q> requestMessageType
  );
}