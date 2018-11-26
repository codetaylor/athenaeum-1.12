package com.codetaylor.mc.athenaeum.util;

import java.util.Arrays;

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

  public static <T> boolean contains(T[] array, T element) {

    return Arrays.asList(array).contains(element);
  }

  public static int getOrLast(int[] array, int index) {

    if (index >= array.length) {
      return array[array.length - 1];

    } else {
      return array[index];
    }
  }

  public static <T> T getOrLast(T[] array, int index) {

    if (index >= array.length) {
      return array[array.length - 1];

    } else {
      return array[index];
    }
  }

  private ArrayHelper() {
    //
  }

}
