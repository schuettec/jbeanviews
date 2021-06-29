package com.github.schuettec.jbeanviews.features.parentBindingReplacesSubProperties;

import java.util.Arrays;

public class UnMappableSource {

  private byte[] bytes;

  public UnMappableSource(byte[] bytes) {
    super();
    this.bytes = bytes;
  }

  public UnMappableSource() {
    super();
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  @Override
  public String toString() {
    return "UnMappableSource [bytes=" + Arrays.toString(bytes) + "]";
  }

}
