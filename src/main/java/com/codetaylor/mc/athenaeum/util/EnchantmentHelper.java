package com.codetaylor.mc.athenaeum.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;
import java.util.Locale;

public class EnchantmentHelper {

  public static int getPlayerExperienceTotal(EntityPlayer player) {

    int experienceFromLevel = EnchantmentHelper.getExperienceFromLevel(player.experienceLevel);
    int experienceBarCapacity = EnchantmentHelper.getExperienceBarCapacity(player.experienceLevel);
    return (int) (experienceFromLevel + (player.experience * experienceBarCapacity));
  }

  public static void adjustPlayerExperience(EntityPlayer player, int amount) {

    int experience = EnchantmentHelper.getPlayerExperienceTotal(player);
    player.experienceTotal = Math.max(0, experience + amount);
    player.experienceLevel = EnchantmentHelper.getLevelFromExperience(player.experienceTotal);
    player.experience = (player.experienceTotal - EnchantmentHelper.getExperienceFromLevel(player.experienceLevel));
    player.experience /= (float) EnchantmentHelper.getExperienceBarCapacity(player.experienceLevel);
  }

  public static int getExperienceBarCapacity(int level) {

    if (level >= 30) {
      return 112 + (level - 30) * 9;
    }

    if (level >= 15) {
      return 37 + (level - 15) * 5;
    }

    return 7 + level * 2;
  }

  private static int sum(long n, long a0, long d) {

    return (int) (n * (2 * a0 + (n - 1) * d) / 2);
  }

  public static int getExperienceFromLevel(int level) {

    if (level == 0) {
      return 0;
    }

    if (level <= 15) {
      return EnchantmentHelper.sum(level, 7, 2);
    }

    if (level <= 30) {
      return 315 + EnchantmentHelper.sum(level - 15, 37, 5);
    }

    return 1395 + EnchantmentHelper.sum(level - 30, 112, 9);
  }

  /**
   * Taken from EntityPlayer class and used to test the accuracy of the
   * {@link EnchantmentHelper#getExperienceFromLevel(int)} method.
   */
  public static int xpBarCap(int level) {

    if (level >= 30) {
      return 112 + (level - 30) * 9;

    } else {
      return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
    }
  }

  public static int getExperienceToNextLevel(int currentLevel) {

    return EnchantmentHelper.getExperienceToLevel(currentLevel, currentLevel + 1);
  }

  public static int getExperienceToLevel(int startingLevel, int targetLevel) {

    int startingLevelExperience = EnchantmentHelper.getExperienceFromLevel(targetLevel);
    int targetLevelExperience = EnchantmentHelper.getExperienceFromLevel(startingLevel);
    return startingLevelExperience - targetLevelExperience;
  }

  public static int getLevelFromExperience(int experience) {

    int level = 0;

    while (true) {

      int experienceForNextLevel = EnchantmentHelper.getExperienceToNextLevel(level);

      if (experience < experienceForNextLevel) {
        return level;
      }

      level += 1;
      experience -= experienceForNextLevel;
    }
  }

  public static String getExperienceString(int experience) {

    int level = EnchantmentHelper.getLevelFromExperience(experience);

    if (level == 0) {
      return experience + "xp";
    }

    int remainingExperience = experience - EnchantmentHelper.getExperienceFromLevel(level);

    if (remainingExperience == 0) {
      return level + "L";
    }

    float fraction = remainingExperience / (float) EnchantmentHelper.getExperienceToNextLevel(level);

    return String.format(Locale.US, "%.2fL", level + fraction);
  }

  public static boolean isEnchantable(ItemStack itemStack) {

    boolean hasEnchantability = itemStack.getItem().getItemEnchantability(itemStack) > 0;
    boolean isBook = itemStack.getItem() == Items.BOOK || itemStack.getItem() == Items.ENCHANTED_BOOK;
    return hasEnchantability && (isBook || itemStack.isItemEnchantable());
  }

  public static boolean isCompatible(Enchantment firstEnchantment, Enchantment secondEnchantment) {

    return firstEnchantment.isCompatibleWith(secondEnchantment);
  }

  public static float getEnchantPower(World world, BlockPos position) {

    float power = 0;

    for (int offsetZ = -1; offsetZ <= 1; ++offsetZ) {

      for (int offsetX = -1; offsetX <= 1; ++offsetX) {

        if ((offsetZ != 0 || offsetX != 0)
            && world.isAirBlock(position.add(offsetX, 0, offsetZ))
            && world.isAirBlock(position.add(offsetX, 1, offsetZ))) {

          power += ForgeHooks.getEnchantPower(world, position.add(offsetX * 2, 0, offsetZ * 2));
          power += ForgeHooks.getEnchantPower(world, position.add(offsetX * 2, 1, offsetZ * 2));

          if (offsetX != 0 && offsetZ != 0) {
            power += ForgeHooks.getEnchantPower(world, position.add(offsetX * 2, 0, offsetZ));
            power += ForgeHooks.getEnchantPower(world, position.add(offsetX * 2, 1, offsetZ));
            power += ForgeHooks.getEnchantPower(world, position.add(offsetX, 0, offsetZ * 2));
            power += ForgeHooks.getEnchantPower(world, position.add(offsetX, 1, offsetZ * 2));
          }
        }
      }
    }

    return power;
  }

  /**
   * For each enchantment level in the enchantment given, adds a book with said enchantment to the result list.
   *
   * @param enchantment the enchantment
   * @param result      the resulting list
   * @return the resulting list
   */
  public static List<ItemStack> getAllBooksForEnchantment(Enchantment enchantment, List<ItemStack> result) {

    for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
      result.add(ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, i)));
    }

    return result;
  }
}
