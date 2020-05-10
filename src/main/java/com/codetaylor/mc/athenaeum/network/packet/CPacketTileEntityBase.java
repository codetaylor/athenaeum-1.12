package com.codetaylor.mc.athenaeum.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class CPacketTileEntityBase<Q extends CPacketTileEntityBase>
    extends PacketBlockPosBase<Q> {

  public CPacketTileEntityBase() {
    // serialization
  }

  public CPacketTileEntityBase(BlockPos blockPos) {

    super(blockPos);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public IMessage onMessage(Q message, Supplier<NetworkEvent.Context> contextSupplier) {

    ClientPlayerEntity player = Minecraft.getInstance().player;
    World world = player.getEntityWorld();

    if (world.isBlockLoaded(message.blockPos)) {
      TileEntity tileEntity = world.getTileEntity(message.blockPos);
      return this.onMessage(message, contextSupplier, tileEntity);
    }

    return null;
  }

  protected abstract IMessage onMessage(
      Q message,
      Supplier<NetworkEvent.Context> contextSupplier,
      TileEntity tileEntity
  );
}
