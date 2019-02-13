package com.codetaylor.mc.athenaeum.util;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public final class PacketHelper {

  public static void writeString(String string, ByteBuf buffer) {

    byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
    buffer.writeInt(bytes.length);
    buffer.writeBytes(bytes);
  }

  public static String readString(ByteBuf buffer) {

    int length = buffer.readInt();
    ByteBuf byteBuf = buffer.readBytes(length);
    return new String(byteBuf.array(), StandardCharsets.UTF_8);
  }

  private PacketHelper() {
    //
  }

}
