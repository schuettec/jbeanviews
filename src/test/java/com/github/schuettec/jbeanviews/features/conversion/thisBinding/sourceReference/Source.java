package com.github.schuettec.jbeanviews.features.conversion.thisBinding.sourceReference;

public class Source {

  private int zahl1;
  private int zahl2;

  public Source(int zahl1, int zahl2) {
    super();
    this.zahl1 = zahl1;
    this.zahl2 = zahl2;
  }

  public Source() {
    super();
  }

  public int getZahl1() {
    return zahl1;
  }

  public void setZahl1(int zahl1) {
    this.zahl1 = zahl1;
  }

  public int getZahl2() {
    return zahl2;
  }

  public void setZahl2(int zahl2) {
    this.zahl2 = zahl2;
  }

  @Override
  public String toString() {
    return "Source [zahl1=" + zahl1 + ", zahl2=" + zahl2 + "]";
  }

}
