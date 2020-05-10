package com.codetaylor.mc.athenaeum.network;

import com.codetaylor.mc.athenaeum.network.packet.IMessage;
import com.codetaylor.mc.athenaeum.network.packet.IMessageHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PacketService
    implements IPacketService {

  private final SimpleChannel channel;

  private int nextRegistrationIndex;

  public static PacketService create(String modId, String channelName, String protocolVersion) {

    ResourceLocation name = new ResourceLocation(modId, channelName);
    Supplier<String> protocolVersionSupplier = () -> protocolVersion;
    SimpleChannel simpleChannel = NetworkRegistry.newSimpleChannel(name, protocolVersionSupplier, protocolVersion::equals, protocolVersion::equals);
    return new PacketService(simpleChannel);
  }

  private PacketService(SimpleChannel channel) {

    this.channel = channel;
  }

  @Override
  public <Q extends IMessage, A extends IMessage> void registerMessage(
      Class<? extends IMessageHandler<Q, A>> messageHandler,
      Class<Q> requestMessageType
  ) {

    this.registerMessage(
        this.instantiateHandler(messageHandler),
        this.instantiateMessage(requestMessageType),
        this.nextRegistrationIndex
    );

    this.nextRegistrationIndex += 1;
  }

  private <Q extends IMessage> Q instantiateMessage(Class<Q> messageType) {

    try {
      return messageType.newInstance();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private <Q extends IMessage, A extends IMessage> IMessageHandler<? super Q, ? extends A> instantiateHandler(
      Class<? extends IMessageHandler<? super Q, ? extends A>> handler
  ) {

    try {
      return handler.newInstance();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  private <Q extends IMessage, A extends IMessage> void registerMessage(
      IMessageHandler<? super Q, ? extends A> messageHandler,
      Q message,
      int id
  ) {

    IMessageHandler<Q, A> wrapper = new ThreadedMessageReplyHandler(this, messageHandler);
    BiConsumer<Q, Supplier<NetworkEvent.Context>> handlerSupplier = wrapper::onMessage;
    BiConsumer<Q, PacketBuffer> encoder = message::encode;
    Class<Q> messageClass = (Class<Q>) message.getClass();
    Function<PacketBuffer, Q> decoder = packetBuffer -> {
      Q m = this.instantiateMessage(messageClass);
      //noinspection unchecked
      return (Q) m.decode(m, packetBuffer);
    };
    this.channel.registerMessage(id, messageClass, encoder, decoder, handlerSupplier);
  }

  /**
   * Convenience method to send a packet to all entities tracking the chunk that contains the given tile entity.
   *
   * @param tileEntity
   * @param message
   */
  @Override
  public void sendToTrackingChunk(TileEntity tileEntity, IMessage message) {

    BlockPos pos = tileEntity.getPos();
    World world = tileEntity.getWorld();

    if (world != null) {
      Chunk chunk = world.getChunk(pos.getX() >> 4, pos.getZ() >> 4);
      this.sendToTrackingChunk(chunk, message);
    }
  }

  @Override
  public void sendToPlayer(ServerPlayerEntity player, IMessage message) {

    this.channel.send(PacketDistributor.PLAYER.with(() -> player), message);
  }

  @Override
  public void sendToDimension(DimensionType dimensionType, IMessage message) {

    this.channel.send(PacketDistributor.DIMENSION.with(() -> dimensionType), message);
  }

  @Override
  public void sendToNear(PacketDistributor.TargetPoint targetPoint, IMessage message) {

    this.channel.send(PacketDistributor.NEAR.with(() -> targetPoint), message);
  }

  @Override
  public void sendToAll(IMessage message) {

    this.channel.send(PacketDistributor.ALL.noArg(), message);
  }

  @Override
  public void sendToServer(IMessage message) {

    this.channel.send(PacketDistributor.SERVER.noArg(), message);
  }

  @Override
  public void sendToTrackingEntity(Entity entity, IMessage message) {

    this.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
  }

  @Override
  public void sendToTrackingEntityAndSelf(Entity entity, IMessage message) {

    this.channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), message);
  }

  @Override
  public void sendToTrackingChunk(Chunk chunk, IMessage message) {

    this.channel.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), message);
  }

}