package com.codetaylor.mc.athenaeum.util;

import java.util.Random;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class MathConstants {

  private MathConstants() {
    //
  }

  /**
   * A close to zero double epsilon value
   */
  public static final double DBL_EPSILON = 2.220446049250313E-16d;

  /**
   * A close to zero float epsilon value
   */
  public static final float FLT_EPSILON = 1.1920928955078125E-7f;

  /**
   * A close to one float value
   */
  public static final float FLT_ONE_MINUS_EPSILON = 1.0f - FLT_EPSILON;

  /**
   * For use as zero tolerance value
   */
  public static final float ZERO_TOLERANCE = 0.0001f;

  /**
   * One divided by three floating point value
   */
  public static final float ONE_THIRD = 1.0f / 3.0f;

  /**
   * E as a floating point value
   */
  public static final float E = (float) Math.E;

  /**
   * PI as a floating point value
   */
  public static final float PI = (float) Math.PI;

  /**
   * 2PI as a floating point value
   */
  public static final float TWO_PI = 2.0f * PI;

  /**
   * PI/2 as a floating point value
   */
  public static final float HALF_PI = 0.5f * PI;

  /**
   * PI/4 as a floating point value
   */
  public static final float QUARTER_PI = 0.25f * PI;

  /**
   * 3PI/4 as a floating point value
   */
  public static final float THREE_QUARTER_PI = 0.75f * PI;

  /**
   * 1/PI as a floating point value
   */
  public static final float INV_PI = 1.0f / PI;

  /**
   * 1/(2PI) as a floating point value
   */
  public static final float INV_TWO_PI = 1.0f / TWO_PI;

  /**
   * PI/360 as a floating point value
   */
  public static final float PI_OVER_360 = PI / 360.0f;

  /**
   * Multiply by to convert from degrees to radians
   */
  public static final float DEG_TO_RAD = PI / 180.0f;

  /**
   * Multiply by to convert from radians to degrees
   */
  public static final float RAD_TO_DEG = 180.0f / PI;

  /**
   * Square root of 3 as a floating point value
   */
  public static final float SQRT3 = 1.7320508075688772935274463415059f;

  /**
   * Pre-generated random object
   */
  public static final Random rand = new Random(System.currentTimeMillis());

  // Used for fastFloor, fastRound, fastCeil
  // http://riven8192.blogspot.com/2010/02/fastmath-fast-floor.html
  private static final int BIG_ENOUGH_INT = 16 * 1024;
  private static final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT + 0.0000;
  private static final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5000;
  private static final double BIG_ENOUGH_CEIL = BIG_ENOUGH_INT + 0.9999;

}