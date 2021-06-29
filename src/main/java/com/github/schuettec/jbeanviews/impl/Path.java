package com.github.schuettec.jbeanviews.impl;

import static java.util.Comparator.naturalOrder;

import java.util.Arrays;

public class Path {

  public static boolean startsWith(String viewPath, String anotherViewPath) {
    String[] viewPathSeg = getSegments(viewPath);
    String[] anotherViewPathSeg = getSegments(anotherViewPath);
    int maxCheckLength = anotherViewPathSeg.length;
    if (viewPathSeg.length < maxCheckLength) {
      return false;
    } else {
      return Arrays.equals(viewPathSeg, 0, maxCheckLength, anotherViewPathSeg, 0, maxCheckLength, naturalOrder())
          && !(viewPathSeg.length == anotherViewPathSeg.length);
    }
  }

  private static String[] getSegments(String string) {
    return string.split("\\.");
  }

}
