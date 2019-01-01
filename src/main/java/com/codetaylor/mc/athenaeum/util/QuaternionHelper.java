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

  /**
   * Multiplies q0 by q1. The result is returned in res. It should be noted that
   * quaternion multiplication is not commutative so q * p != p * q.
   * <p>
   * It is safe if q0 == res || q1 == res.
   *
   * @param q0  the quaternion to multiply
   * @param q1  the quaternion to multiply
   * @param res the quaternion to store the result in.
   * @return the new quaternion.
   */
  public static Quaternion mult(Quaternion q0, Quaternion q1, Quaternion res) {

    if (res == null) {
      res = new Quaternion();
    }
    float q0w = q0.w, q0x = q0.x, q0y = q0.y, q0z = q0.z;
    float q1w = q1.w, q1x = q1.x, q1y = q1.y, q1z = q1.z;
    res.x = q0x * q1w + q0y * q1z - q0z * q1y + q0w * q1x;
    res.y = -q0x * q1z + q0y * q1w + q0z * q1x + q0w * q1y;
    res.z = q0x * q1y - q0y * q1x + q0z * q1w + q0w * q1z;
    res.w = -q0x * q1x - q0y * q1y - q0z * q1z + q0w * q1w;
    return res;
  }

  private QuaternionHelper() {
    //
  }
}
