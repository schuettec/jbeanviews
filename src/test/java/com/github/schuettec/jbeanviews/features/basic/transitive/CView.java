package com.github.schuettec.jbeanviews.features.basic.transitive;

public class CView {

  private String dummy;

  public CView(String dummy) {
    super();
    this.dummy = dummy;
  }

  public CView() {
    super();
  }

  public String getDummy() {
    return dummy;
  }

  public void setDummy(String dummy) {
    this.dummy = dummy;
  }

  @Override
  public String toString() {
    return "CView [dummy=" + dummy + "]";
  }

}
