package com.codetaylor.mc.athenaeum.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import static net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * Created by codetaylor on 12/3/2016.
 */
public class PacketService
    implements IPacketService {

  private static final int DEFAULT_RANGE = 64;

  private ThreadedNetworkWrapper threadedNetworkWrapper;

  public PacketService(ThreadedNetworkWrapper threadedNetworkWrapper) {

    this.threadedNetworkWrapper = threadedNetworkWrapper;
  }

  @Override
  public void sendToAllAround(IMessage message, TileEntity tileEntity, int range) {

    BlockPos pos = tileEntity.getPos();
    int dimension = tileEntity.getWorld().provider.getDimension();
    TargetPoint point = new TargetPoint(dimension, pos.getX(), pos.getY(), pos.getZ(), range);

    this.threadedNetworkWrapper.sendToAllAround(message, point);
  }

  @Override
  public void sendToAllAround(IMessage message, TileEntity tileEntity) {

    this.sendToAllAround(message, tileEntity, DEFAULT_RANGE);
  }

  @Override
  public void sendTo(IMessage message, EntityPlayerMP player) {

    this.threadedNetworkWrapper.sendTo(message, player);
  }

  @Override
  public void sendToServer(IMessage message) {

    this.threadedNetworkWrapper.sendToServer(message);
  }
}