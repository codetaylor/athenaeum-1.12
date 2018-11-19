package com.codetaylor.mc.athenaeum.util;

import org.lwjgl.util.vector.Quaternion;

public final class QuaternionHelper {

  /**
   * Sets the given {@link Quaternion} from the given axis angle.
   *
   * @param q
   * @param x
   * @param y
   * @param z
   * @param angle the angle in radians
   * @return the given {@link Quaternion}
   */
  public static Quaternion setFromAxisAngle(Quaternion q, float x, float y, float z, float angle) {

    q.x = x;
    q.y = y;
    q.z = z;
    float n = (float) Math.sqrt(q.x * q.x + q.y * q.y + q.z * q.z);
    // zero-div may occur.
    float s = (float) (Math.sin(0.5 * angle) / n);
    q.x *= s;
    q.y *= s;
    q.z *= s;
    q.w = (float) Math.cos(0.5 * angle);

    return q;
  }

  private QuaternionHelper() {
    //
  }
}
