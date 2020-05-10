package com.codetaylor.mc.athenaeum.network.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class SPacketTileEntityBase<Q extends SPacketTileEntityBase>
    extends PacketBlockPosBase<Q> {

  public SPacketTileEntityBase() {
    // serialization
  }

  public SPacketTileEntityBase(BlockPos blockPos) {

    super(blockPos);
  }

  @Override
  public IMessage onMessage(Q message, Supplier<NetworkEvent.Context> contextSupplier) {

    NetworkEvent.Context context = contextSupplier.get();
    ServerPlayerEntity player = context.getSender();

    TileEntity tileEntity = player.getEntityWorld().getTileEntity(message.blockPos);
    return this.onMessage(message, contextSupplier, tileEntity);
  }

  protected abstract IMessage onMessage(
      Q message,
      Supplier<NetworkEvent.Context> contextSupplier,
      TileEntity tileEntity
  );
}
