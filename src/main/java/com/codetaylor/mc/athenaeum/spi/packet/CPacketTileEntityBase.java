package com.codetaylor.mc.athenaeum.spi.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class CPacketTileEntityBase<REQ extends CPacketTileEntityBase>
    extends PacketBlockPosBase<REQ> {

  public CPacketTileEntityBase() {
    // serialization
  }

  public CPacketTileEntityBase(BlockPos blockPos) {

    super(blockPos);
  }

  @Override
  public IMessage onMessage(
      REQ message,
      MessageContext ctx
  ) {

    EntityPlayerSP player = Minecraft.getMinecraft().player;
    World world = player.getEntityWorld();

    if (world.isBlockLoaded(message.blockPos)) {
      TileEntity tileEntity = world.getTileEntity(message.blockPos);
      return this.onMessage(message, ctx, tileEntity);
    }

    return null;
  }

  protected abstract IMessage onMessage(
      REQ message,
      MessageContext ctx,
      TileEntity tileEntity
  );
}
