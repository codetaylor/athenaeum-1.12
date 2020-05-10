package com.codetaylor.mc.athenaeum.internal.network.packet;

import com.codetaylor.mc.athenaeum.network.spi.packet.IMessage;
import com.codetaylor.mc.athenaeum.network.spi.packet.IMessageHandler;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Executes the message handler and sends a reply if a message is returned from the handler.
 */
final class MessageReplyRunner<Q extends IMessage, A extends IMessage>
    implements Runnable {

  private final Q message;
  private final Supplier<NetworkEvent.Context> contextSupplier;
  private final PacketService packetService;
  private final IMessageHandler<Q, A> handler;

  public MessageReplyRunner(Q message, Supplier<NetworkEvent.Context> contextSupplier, PacketService packetService, IMessageHandler<Q, A> handler) {

    this.message = message;
    this.contextSupplier = contextSupplier;
    this.packetService = packetService;
    this.handler = handler;
  }

  @Override
  public void run() {

    final A reply = this.handler.onMessage(this.message, this.contextSupplier);

    if (reply != null) {
      NetworkEvent.Context context = this.contextSupplier.get();
      LogicalSide receptionSide = context.getDirection().getReceptionSide();

      if (receptionSide == LogicalSide.CLIENT) {
        this.packetService.sendToServer(reply);

      } else {
        this.packetService.sendToPlayer(context.getSender(), reply);
      }
    }
  }
}
