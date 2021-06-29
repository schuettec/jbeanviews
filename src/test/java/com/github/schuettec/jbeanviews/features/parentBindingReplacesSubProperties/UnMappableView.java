package com.github.schuettec.jbeanviews.features.parentBindingReplacesSubProperties;

public class UnMappableView {
  private int zahl;

  public UnMappableView(int zahl) {
    super();
    this.zahl = zahl;
  }

  public UnMappableView() {
    super();
  }

  public int getZahl() {
    return zahl;
  }

  public void setZahl(int zahl) {
    this.zahl = zahl;
  }

  @Override
  public String toString() {
    return "UnMappableView [zahl=" + zahl + "]";
  }

}
