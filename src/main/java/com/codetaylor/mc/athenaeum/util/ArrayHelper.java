package com.codetaylor.mc.athenaeum.util;

public final class ArrayHelper {

  public static String[] copy(String[] toCopy) {

    String[] result = new String[toCopy.length];
    System.arraycopy(toCopy, 0, result, 0, toCopy.length);
    return result;
  }

  public static int[] copy(int[] toCopy) {

    int[] result = new int[toCopy.length];
    System.arraycopy(toCopy, 0, result, 0, toCopy.length);
    return result;
  }

  private ArrayHelper() {
    //
  }

}
