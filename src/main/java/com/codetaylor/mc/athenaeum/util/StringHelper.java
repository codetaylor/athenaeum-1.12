package com.codetaylor.mc.athenaeum.util;

public class StringHelper {

  public static String capitalizeFirstLetter(String input) {

    return input.substring(0, 1).toUpperCase() + input.substring(1);
  }

  private StringHelper() {
    //
  }

}
