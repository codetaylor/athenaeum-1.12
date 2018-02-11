package com.codetaylor.mc.athenaeum.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnchantmentHelperTest {

  @Test
  public void testExperienceConversion() {

    testExperienceConversion(0, 0);
    testExperienceConversion(7, 1);
    testExperienceConversion(16, 2);
    testExperienceConversion(27, 3);
    testExperienceConversion(40, 4);
    testExperienceConversion(55, 5);
    testExperienceConversion(72, 6);
    testExperienceConversion(91, 7);
    testExperienceConversion(112, 8);
    testExperienceConversion(135, 9);
    testExperienceConversion(160, 10);
    testExperienceConversion(187, 11);
    testExperienceConversion(216, 12);
    testExperienceConversion(247, 13);
    testExperienceConversion(280, 14);
    testExperienceConversion(315, 15);
    testExperienceConversion(352, 16);
    testExperienceConversion(394, 17);
    testExperienceConversion(441, 18);
    testExperienceConversion(493, 19);
    testExperienceConversion(550, 20);
    testExperienceConversion(612, 21);
    testExperienceConversion(679, 22);
    testExperienceConversion(751, 23);
    testExperienceConversion(828, 24);
    testExperienceConversion(910, 25);
    testExperienceConversion(997, 26);
    testExperienceConversion(1089, 27);
    testExperienceConversion(1186, 28);
    testExperienceConversion(1288, 29);
    testExperienceConversion(1395, 30);
    testExperienceConversion(1507, 31);
    testExperienceConversion(1628, 32);
    testExperienceConversion(1758, 33);
    testExperienceConversion(1897, 34);
    testExperienceConversion(2045, 35);
    testExperienceConversion(2202, 36);
    testExperienceConversion(2368, 37);
    testExperienceConversion(2543, 38);
    testExperienceConversion(2727, 39);
    testExperienceConversion(2920, 40);

  }

  private void testExperienceConversion(int experience, int level) {

    assertEquals(experience, EnchantmentHelper.getExperienceFromLevel(level));
    assertEquals(level, EnchantmentHelper.getLevelFromExperience(experience));
  }

}