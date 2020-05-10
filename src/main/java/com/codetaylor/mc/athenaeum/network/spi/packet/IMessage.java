package com.codetaylor.mc.athenaeum.network.spi.packet;

import net.minecraft.network.PacketBuffer;

public interface IMessage<T extends IMessage> {

  void encode(T message, PacketBuffer packetBuffer);

  T decode(T message, PacketBuffer packetBuffer);
}
