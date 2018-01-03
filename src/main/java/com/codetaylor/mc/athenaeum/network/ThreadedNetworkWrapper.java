package com.codetaylor.mc.athenaeum.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * https://github.com/SleepyTrousers/EnderCore/blob/1.10/src/main/java/com/enderio/core/common/network/ThreadedNetworkWrapper.java
 * <p>
 * Created by codetaylor on 12/3/2016.
 */
public class ThreadedNetworkWrapper {

  private final SimpleNetworkWrapper parent;

  public ThreadedNetworkWrapper(String channelName) {

    this.parent = new SimpleNetworkWrapper(channelName);
  }

  private final class Wrapper<Q extends IMessage, A extends IMessage>
      implements IMessageHandler<Q, A> {

    private final IMessageHandler<Q, A> wrapped;

    public Wrapper(IMessageHandler<Q, A> wrapped) {

      this.wrapped = wrapped;
    }

    @Override
    public A onMessage(final Q message, final MessageContext ctx) {

      final IThreadListener target = ctx.side == Side.CLIENT ? Minecraft.getMinecraft() : FMLCommonHandler.instance()
          .getMinecraftServerInstance();

      if (target != null) {
        target.addScheduledTask(new Runner(message, ctx));
      }
      return null;
    }

    @Override
    public String toString() {

      return this.wrapped.toString();
    }

    @Override
    public int hashCode() {

      return this.wrapped.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

      if (obj instanceof Wrapper) {
        return this.wrapped.equals(((Wrapper<?, ?>) obj).wrapped);

      } else {
        return this.wrapped.equals(obj);
      }
    }

    private final class Runner
        implements Runnable {

      private final Q message;
      private final MessageContext ctx;

      public Runner(final Q message, final MessageContext ctx) {

        this.message = message;
        this.ctx = ctx;
      }

      @Override
      public void run() {

        final A reply = Wrapper.this.wrapped.onMessage(this.message, this.ctx);

        if (reply != null) {

          if (this.ctx.side == Side.CLIENT) {
            ThreadedNetworkWrapper.this.sendToServer(reply);

          } else {
            final EntityPlayerMP player = this.ctx.getServerHandler().player;

            if (player != null) {
              ThreadedNetworkWrapper.this.sendTo(reply, player);
            }
          }
        }
      }

    }

  }

  public <Q extends IMessage, A extends IMessage> void registerMessage(
      Class<? extends IMessageHandler<Q, A>> messageHandler,
      Class<Q> requestMessageType,
      int discriminator,
      Side side
  ) {

    this.registerMessage(this.instantiate(messageHandler), requestMessageType, discriminator, side);
  }

  private <Q extends IMessage, A extends IMessage> IMessageHandler<? super Q, ? extends A> instantiate(
      Class<? extends IMessageHandler<? super Q, ? extends A>> handler
  ) {

    try {
      return handler.newInstance();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public <Q extends IMessage, A extends IMessage> void registerMessage(
      IMessageHandler<? super Q, ? extends A> messageHandler,
      Class<Q> requestMessageType,
      int discriminator,
      Side side
  ) {

    this.parent.registerMessage(
        (Wrapper<Q, A>) new Wrapper(messageHandler),
        requestMessageType,
        discriminator,
        side
    );
  }

  public Packet<?> getPacketFrom(IMessage message) {

    return this.parent.getPacketFrom(message);
  }

  public void sendToAll(IMessage message) {

    this.parent.sendToAll(message);
  }

  public void sendTo(IMessage message, EntityPlayerMP player) {

    this.parent.sendTo(message, player);
  }

  public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {

    this.parent.sendToAllAround(message, point);
  }

  public void sendToDimension(IMessage message, int dimensionId) {

    this.parent.sendToDimension(message, dimensionId);
  }

  public void sendToServer(IMessage message) {

    this.parent.sendToServer(message);
  }
}