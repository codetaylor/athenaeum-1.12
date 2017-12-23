/*
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016 Jared
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package com.codetaylor.mc.athenaeum.integration.crafttweaker.mtlib.helpers;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * https://github.com/jaredlll08/MTLib
 */
public class CTStringHelper {

  public static List<String> toLowerCase(List<String> stringList) {

    ListIterator<String> iterator = stringList.listIterator();

    while (iterator.hasNext()) {
      iterator.set(iterator.next().toLowerCase());
    }

    return stringList;
  }

  public static String join(Collection<String> list, String conjunction) {

    StringBuilder sb = new StringBuilder();
    if (conjunction == null) {
      conjunction = ", ";
    }

    if (list != null && !list.isEmpty()) {
      for (String string : list) {
        sb.append(string).append(conjunction);
      }

      sb.setLength(sb.length() - conjunction.length());
    }

    return sb.toString();
  }

  public static String wildcardToRegex(String expression) {

    if (expression == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder();
    sb.append('^');

    for (int i = 0; i < expression.length(); i++) {
      char c = expression.charAt(i);

      switch (c) {
        case '*':
          sb.append(".*");
          break;
        case '?':
          sb.append('.');
          break;

        case '(':
        case ')':
        case '[':
        case ']':
        case '$':
        case '^':
        case '.':
        case '{':
        case '}':
        case '|':
        case '\\':
          sb.append('\\').append(c);
          break;
        default:
          sb.append(c);
          break;
      }
    }

    sb.append('$');
    return sb.toString();
  }
}
