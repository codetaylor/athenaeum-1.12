package com.codetaylor.mc.athenaeum.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by codetaylor on 12/3/2016.
 */
public interface IPacketRegistry {

  <Q extends IMessage, A extends IMessage> IPacketRegistry register(
      Class<? extends IMessageHandler<Q, A>> messageHandler,
      Class<Q> requestMessageType,
      Side side
  );

  <Q extends IMessage, A extends IMessage> IPacketRegistry register(
      IMessageHandler<Q, A> messageHandler,
      Class<Q> requestMessageType,
      Side side
  );
}