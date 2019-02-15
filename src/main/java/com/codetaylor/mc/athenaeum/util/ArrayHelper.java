package com.codetaylor.mc.athenaeum.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public final class ArrayHelper {

  public static <E> E combine(E[] a) {

    if (a.length == 0) {
      throw new IllegalArgumentException("Zero-length array");
    }

    E result = a[0];

    for (int i = 1; i < a.length; i++) {
      result = ArrayHelper.combine(result, a[i]);
    }

    return result;
  }

  /**
   * Combines two arrays of the same or most general type and returns a new array.
   * <p>
   * https://stackoverflow.com/a/80503
   *
   * @param a
   * @param b
   * @param <E>
   * @return
   */
  public static <E> E combine(E a, E b) {

    if (!a.getClass().isArray() || !b.getClass().isArray()) {
      throw new IllegalArgumentException();
    }

    Class<?> resCompType;
    Class<?> aCompType = a.getClass().getComponentType();
    Class<?> bCompType = b.getClass().getComponentType();

    if (aCompType.isAssignableFrom(bCompType)) {
      resCompType = aCompType;

    } else if (bCompType.isAssignableFrom(aCompType)) {
      resCompType = bCompType;

    } else {
      throw new IllegalArgumentException();
    }

    int aLen = Array.getLength(a);
    int bLen = Array.getLength(b);

    @SuppressWarnings("unchecked")
    E result = (E) Array.newInstance(resCompType, aLen + bLen);
    //noinspection SuspiciousSystemArraycopy
    System.arraycopy(a, 0, result, 0, aLen);
    //noinspection SuspiciousSystemArraycopy
    System.arraycopy(b, 0, result, aLen, bLen);

    return result;
  }

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

  public static boolean containsInt(int[] array, int element) {

    for (int i = 0; i < array.length; i++) {

      if (array[i] == element) {
        return true;
      }
    }
    return false;
  }

  public static int getOrLast(int[] array, int index) {

    if (index >= array.length) {
      return array[array.length - 1];

    } else {
      return array[index];
    }
  }

  public static double getOrLast(double[] array, int index) {

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

  public static <T> T randomElement(T[] array, Random random) {

    return array[random.nextInt(array.length)];
  }

  private ArrayHelper() {
    //
  }

}
