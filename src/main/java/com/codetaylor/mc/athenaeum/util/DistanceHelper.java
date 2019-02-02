package com.codetaylor.mc.athenaeum.util;

public final class DistanceHelper {

  public static double getDistanceSq(double x0, double y0, double z0, double x1, double y1, double z1) {

    return (x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1) + (z0 - z1) * (z0 - z1);
  }

  public static double getDistance(double x0, double y0, double z0, double x1, double y1, double z1) {

    return Math.sqrt(DistanceHelper.getDistanceSq(x0, y0, z0, x1, y1, z1));
  }

  private DistanceHelper() {
    //
  }
}
