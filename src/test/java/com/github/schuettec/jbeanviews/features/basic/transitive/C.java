package com.github.schuettec.jbeanviews.features.basic.transitive;

public class C {

  private String dummy;

  public C() {
    super();
  }

  public C(String dummy) {
    super();
    this.dummy = dummy;
  }

  public String getDummy() {
    return dummy;
  }

  public void setDummy(String dummy) {
    this.dummy = dummy;
  }

  @Override
  public String toString() {
    return "C [dummy=" + dummy + "]";
  }

}
