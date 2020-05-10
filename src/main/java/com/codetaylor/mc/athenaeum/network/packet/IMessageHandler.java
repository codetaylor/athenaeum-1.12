package com.codetaylor.mc.athenaeum.network.packet;

import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IMessageHandler<Q, A extends IMessage> {

  A onMessage(Q message, Supplier<NetworkEvent.Context> contextSupplier);
}
