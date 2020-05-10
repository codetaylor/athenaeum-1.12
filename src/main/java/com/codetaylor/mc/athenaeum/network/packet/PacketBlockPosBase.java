package com.codetaylor.mc.athenaeum.network.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public abstract class PacketBlockPosBase<Q extends PacketBlockPosBase>
    implements IMessage<Q>,
    IMessageHandler<Q, IMessage> {

  protected BlockPos blockPos;

  public PacketBlockPosBase() {
    // serialization
  }

  public PacketBlockPosBase(BlockPos blockPos) {

    this.blockPos = blockPos;
  }

  @Override
  public Q decode(Q message, PacketBuffer packetBuffer) {

    message.blockPos = packetBuffer.readBlockPos();
    return message;
  }

  @Override
  public void encode(Q message, PacketBuffer packetBuffer) {

    packetBuffer.writeBlockPos(message.blockPos);
  }
}
