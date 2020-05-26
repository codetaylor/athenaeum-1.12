package com.codetaylor.mc.athenaeum.integration.gamestages;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class StagesTest {

  private static final List<String> PLAYER_STAGES = Arrays.asList(
      "a", "b", "c"
  );

  @Test
  public void and() {

    Assert.assertTrue(Stages.and(new Object[]{"a", "b"}).allowed(PLAYER_STAGES));
  }

  @Test
  public void andWithNot() {

    Assert.assertFalse(Stages.and(new Object[]{"a", "b", Stages.not("c")}).allowed(PLAYER_STAGES));
  }

  @Test
  public void andWithNotAnd() {

    Assert.assertTrue(Stages.and(new Object[]{"a", "b", Stages.not(Stages.and(new Object[]{"c", "D"}))}).allowed(PLAYER_STAGES));
  }

  @Test
  public void or() {

    Assert.assertTrue(Stages.or(new Object[]{"a", "D"}).allowed(PLAYER_STAGES));
  }

  @Test
  public void orWithNot() {

    Assert.assertTrue(Stages.or(new Object[]{"D", "E", Stages.not("F")}).allowed(PLAYER_STAGES));
  }
}