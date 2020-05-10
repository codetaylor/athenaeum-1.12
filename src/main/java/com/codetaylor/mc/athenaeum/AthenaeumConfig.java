package com.codetaylor.mc.athenaeum;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

public class AthenaeumConfig {

  public static void load(ForgeConfigSpec spec, Path path) {

    final CommentedFileConfig configData = CommentedFileConfig.builder(path)
        .sync()
        .autosave()
        .writingMode(WritingMode.REPLACE)
        .build();

    configData.load();
    spec.setConfig(configData);
  }

  @SubscribeEvent
  public static void onLoad(final ModConfig.Loading configEvent) {
    //
  }

  @SubscribeEvent
  public static void onReload(final ModConfig.ConfigReloading configEvent) {
    //
  }

  // ---------------------------------------------------------------------------
  // - Client Config
  // ---------------------------------------------------------------------------

  public static ForgeConfigSpec CLIENT_CONFIG;

  public static Client CLIENT;

  static {
    ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    CLIENT = new Client(builder);
    CLIENT_CONFIG = builder.build();
  }

  public static class Client {

    public final ForgeConfigSpec.BooleanValue tileDataServiceEnabled;
    public final ForgeConfigSpec.IntValue tileDataServiceUpdateIntervalTicks;
    public final ForgeConfigSpec.IntValue tileDataServiceTrackingIndex;

    public Client(ForgeConfigSpec.Builder builder) {

      builder.push("Tile Data Service");
      this.tileDataServiceEnabled = builder
          .comment(
              "Set to true to enable client-side display of Athenaeum's tile data service.",
              "Used for monitoring TE updates from the server for any TE registered with",
              "the data service.",
              "Default: " + false
          )
          .define("tileDataServiceEnabled", true);
      this.tileDataServiceUpdateIntervalTicks = builder
          .comment(
              "Change the update rate for the display.",
              "Default: " + 20
          )
          .defineInRange("tileDataServiceUpdateIntervalTicks", 20, 10, Short.MAX_VALUE);
      this.tileDataServiceTrackingIndex = builder
          .comment(
              "Set the update index that will be tracked (the blue value) in the display. ",
              "Default: " + 10
          )
          .defineInRange("tileDataServiceUpdateIntervalTicks", 10, 0, Integer.MAX_VALUE);
      builder.pop();
    }
  }
}
