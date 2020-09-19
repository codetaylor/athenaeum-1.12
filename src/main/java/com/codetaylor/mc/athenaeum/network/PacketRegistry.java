package com.codetaylor.mc.athenaeum.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by codetaylor on 12/3/2016.
 */
public class PacketRegistry
    implements IPacketRegistry {

  private ThreadedNetworkWrapper threadedNetworkWrapper;

  private int id = 0;

  public PacketRegistry(ThreadedNetworkWrapper threadedNetworkWrapper) {

    this.threadedNetworkWrapper = threadedNetworkWrapper;
  }

  @Override
  public <Q extends IMessage, A extends IMessage> IPacketRegistry register(
      Class<? extends IMessageHandler<Q, A>> messageHandler,
      Class<Q> requestMessageType,
      Side side
  ) {

    this.threadedNetworkWrapper.registerMessage(messageHandler, requestMessageType, this.nextId(), side);
    return this;
  }

  @Override
  public <Q extends IMessage, A extends IMessage> IPacketRegistry register(
      IMessageHandler<Q, A> messageHandler,
      Class<Q> requestMessageType,
      Side side
  ) {

    this.threadedNetworkWrapper.registerMessage(messageHandler, requestMessageType, this.nextId(), side);
    return this;
  }

  private int nextId() {

    return this.id++;
  }

}