package com.remondis.jbeanviews.test.data;

import java.util.Arrays;

public enum Type {
  A("aCustomer"),
  B("bCustomer"),
  C("cCustomer");

  private String key;

  private Type(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public static Type byKey(String key) {
    return Arrays.stream(Type.values())
        .filter(type -> type.getKey()
            .equalsIgnoreCase(key))
        .findFirst()
        .orElseThrow();
  }

}
