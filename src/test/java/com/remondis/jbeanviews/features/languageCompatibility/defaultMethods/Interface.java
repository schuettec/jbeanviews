package com.remondis.jbeanviews.features.languageCompatibility.defaultMethods;

public interface Interface {

  public static final String DEFAULT_STRING = "defaultString";

  public default String getString() {
    return DEFAULT_STRING;
  }

  public default void setString(String string) {

  }
}
