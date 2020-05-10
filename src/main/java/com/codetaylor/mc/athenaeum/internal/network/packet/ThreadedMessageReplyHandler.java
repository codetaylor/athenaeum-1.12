package com.codetaylor.mc.athenaeum.internal.network.packet;

import com.codetaylor.mc.athenaeum.network.spi.packet.IMessage;
import com.codetaylor.mc.athenaeum.network.spi.packet.IMessageHandler;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Wraps an {@link IMessageHandler}, executes the handler on the appropriate
 * thread, and sends a reply if a message is returned from the handler.
 *
 * @param <Q>
 * @param <A>
 */
public class ThreadedMessageReplyHandler<Q extends IMessage, A extends IMessage>
    implements IMessageHandler<Q, A> {

  private PacketService packetService;
  private final IMessageHandler<Q, A> handler;

  public ThreadedMessageReplyHandler(PacketService packetService, IMessageHandler<Q, A> handler) {

    this.packetService = packetService;
    this.handler = handler;
  }

  @Override
  public A onMessage(Q message, Supplier<NetworkEvent.Context> contextSupplier) {

    NetworkEvent.Context context = contextSupplier.get();
    context.enqueueWork(new MessageReplyRunner<>(message, contextSupplier, this.packetService, this.handler));
    return null;
  }

  @Override
  public String toString() {

    return this.handler.toString();
  }

  @Override
  public int hashCode() {

    return this.handler.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    if (obj instanceof ThreadedMessageReplyHandler) {
      return this.handler.equals(((ThreadedMessageReplyHandler<?, ?>) obj).handler);

    } else {
      return this.handler.equals(obj);
    }
  }

}
