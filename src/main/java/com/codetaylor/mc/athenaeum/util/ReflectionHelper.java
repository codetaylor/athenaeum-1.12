package com.codetaylor.mc.athenaeum.util;

import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public final class ReflectionHelper {

  @Nullable
  public static MethodHandle unreflectGetter(Class<?> referenceClass, String field, Logger logger) {

    try {

      return MethodHandles.lookup().unreflectGetter(referenceClass.getDeclaredField(field));

    } catch (Throwable t) {
      logger.error(String.format("Error unreflecting getter for field: %s", field), t);
    }

    return null;
  }

  @Nullable
  public static MethodHandle findStaticGetter(Class<?> referenceClass, String field, Class<?> typeClass, Logger logger) {

    try {

      return MethodHandles.lookup().findStaticGetter(referenceClass, field, typeClass);

    } catch (Throwable t) {
      logger.error(String.format("Error finding static getter for field: %s", field), t);
    }

    return null;
  }

  private ReflectionHelper() {
    //
  }
}
