package com.codetaylor.mc.athenaeum.util;

import java.util.Random;

public final class RandomHelper {

  private static final ThreadLocal<Random> RANDOM_THREAD_LOCAL;

  static {
    RANDOM_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
      Random random = new Random();

      for (int i = 0; i < 10000; i++) {
        random.nextDouble();
      }

      return random;
    });
  }

  public static Random random() {

    return RANDOM_THREAD_LOCAL.get();
  }

  private RandomHelper() {
    //
  }
}
