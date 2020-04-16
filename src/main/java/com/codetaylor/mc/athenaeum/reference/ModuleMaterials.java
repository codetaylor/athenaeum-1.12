package com.codetaylor.mc.athenaeum.reference;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ModuleMaterials {

  // @formatter:off
  public static final Item.ToolMaterial FLINT = EnumHelper.addToolMaterial("athenaeum:FLINT", 1, 150, 3.8f, 1.0f, 5);
  public static final Item.ToolMaterial BONE = EnumHelper.addToolMaterial("athenaeum:BONE", 1, 150, 3.8f, 1.0f, 5);
  public static final Item.ToolMaterial OBSIDIAN = EnumHelper.addToolMaterial("athenaeum:OBSIDIAN", 2, 1400, 6.0f, 2.0f, 18);

  // Thermal Foundation tool material stats
  // https://github.com/CoFH/ThermalFoundation/blob/master/src/main/java/cofh/thermalfoundation/init/TFEquipment.java
  public static final Item.ToolMaterial ALUMINUM = EnumHelper.addToolMaterial("athenaeum:ALUMINUM", 1, 225, 10.0F, 1.0F, 14);
  public static final Item.ToolMaterial BRONZE = EnumHelper.addToolMaterial("athenaeum:BRONZE", 2, 500, 6.0F, 2.0F, 15);
  public static final Item.ToolMaterial CONSTANTAN = EnumHelper.addToolMaterial("athenaeum:CONSTANTAN",2, 275, 6.0F, 1.5F, 20);
  public static final Item.ToolMaterial COPPER = EnumHelper.addToolMaterial("athenaeum:COPPER", 1, 175, 4.0F, 0.75F, 6);
  public static final Item.ToolMaterial ELECTRUM = EnumHelper.addToolMaterial("athenaeum:ELECTRUM", 0, 100, 14.0F, 0.5F, 30);
  public static final Item.ToolMaterial INVAR = EnumHelper.addToolMaterial("athenaeum:INVAR", 2, 450, 7.0F, 3.0F, 16);
  public static final Item.ToolMaterial LEAD = EnumHelper.addToolMaterial("athenaeum:LEAD", 1, 150, 5.0F, 1.0F, 9);
  public static final Item.ToolMaterial NICKEL = EnumHelper.addToolMaterial("athenaeum:NICKEL", 2, 300, 6.5F, 2.5F, 18);
  public static final Item.ToolMaterial PLATINUM = EnumHelper.addToolMaterial("athenaeum:PLATINUM", 4, 1700, 9.0F, 4.0F, 9);
  public static final Item.ToolMaterial SILVER = EnumHelper.addToolMaterial("athenaeum:SILVER", 2, 200, 6.0F, 1.5F, 20);
  public static final Item.ToolMaterial STEEL = EnumHelper.addToolMaterial("athenaeum:STEEL", 2, 500, 6.5F, 2.5F, 10);
  public static final Item.ToolMaterial TIN = EnumHelper.addToolMaterial("athenaeum:TIN", 1, 200, 4.5F, 1.0F, 7);

  // Botania tool material stats
  // https://github.com/Vazkii/Botania/blob/938aab69e5c46d782af3fdb9d647ccd754651853/src/main/java/vazkii/botania/api/BotaniaAPI.java
  public static final Item.ToolMaterial MANASTEEL = EnumHelper.addToolMaterial("athenaeum:MANASTEEL", 3, 300, 6.2F, 2F, 20);
  public static final Item.ToolMaterial ELEMENTIUM = EnumHelper.addToolMaterial("athenaeum:ELEMENTIUM", 3, 720, 6.2F, 2F, 20);
  public static final Item.ToolMaterial TERRASTEEL = EnumHelper.addToolMaterial("athenaeum:TERRASTEEL", 4, 2300, 9F, 3F, 26);

  // Mekanism tool material stats
  // https://github.com/mekanism/Mekanism/blob/1.12/src/main/java/mekanism/common/config/ToolsConfig.java
  public static final Item.ToolMaterial OSMIUM = EnumHelper.addToolMaterial("athenaeum:OSMIUM", 2, 500, 10, 4, 12);

  // NuclearCraft tool material stats
  // https://github.com/codetaylor/artisan-worktables/issues/208
  public static final Item.ToolMaterial BORON = EnumHelper.addToolMaterial("athenaeum:BORON", 2, 550, 8, 2.5f, 6);
  public static final Item.ToolMaterial TOUGHALLOY = EnumHelper.addToolMaterial("athenaeum:TOUGHALLOY", 3, 950, 10, 3, 15);
  public static final Item.ToolMaterial HARDCARBON = EnumHelper.addToolMaterial("athenaeum:HARDCARBON", 3, 1250, 11, 3, 12);
  public static final Item.ToolMaterial BORONNITRIDE = EnumHelper.addToolMaterial("athenaeum:BORONNITRIDE", 4, 1950, 12, 2.5f, 20);

  // @formatter:on
}
