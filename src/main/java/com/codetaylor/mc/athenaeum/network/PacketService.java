package com.codetaylor.mc.athenaeum.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
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
  public void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {

    this.threadedNetworkWrapper.sendToAllAround(message, new TargetPoint(dimension, x, y, z, range));
  }

  @Override
  public void sendToAllAround(IMessage message, int dimension, double x, double y, double z) {

    this.sendToAllAround(message, dimension, x, y, z, DEFAULT_RANGE);
  }

  @Override
  public void sendToAllAround(IMessage message, int dimension, BlockPos blockPos, double range) {

    this.sendToAllAround(message, dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ(), range);
  }

  @Override
  public void sendToAllAround(IMessage message, int dimension, BlockPos blockPos) {

    this.sendToAllAround(message, dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ());
  }

  @Override
  public void sendToAllAround(IMessage message, TileEntity tileEntity, int range) {

    BlockPos pos = tileEntity.getPos();
    World world = tileEntity.getWorld();
    WorldProvider provider = world.provider;
    int dimension = provider.getDimension();
    this.sendToAllAround(message, dimension, pos.getX(), pos.getY(), pos.getZ(), range);
  }

  @Override
  public void sendToAllAround(IMessage message, TileEntity tileEntity) {

    this.sendToAllAround(message, tileEntity, DEFAULT_RANGE);
  }

  @Override
  public void sendToDimension(IMessage message, TileEntity tileEntity) {

    World world = tileEntity.getWorld();
    WorldProvider provider = world.provider;
    int dimension = provider.getDimension();
    this.sendToDimension(message, dimension);
  }

  @Override
  public void sendToDimension(IMessage message, int dimension) {

    this.threadedNetworkWrapper.sendToDimension(message, dimension);
  }

  @Override
  public void sendToAll(IMessage message) {

    this.threadedNetworkWrapper.sendToAll(message);
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