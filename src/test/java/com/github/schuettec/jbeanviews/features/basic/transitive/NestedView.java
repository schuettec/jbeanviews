package com.github.schuettec.jbeanviews.features.basic.transitive;

public class NestedView {

  private String aView;
  private int aViewLength;

  public NestedView(String aView, int aViewLength) {
    super();
    this.aView = aView;
    this.aViewLength = aViewLength;
  }

  public NestedView() {
    super();
  }

  public String getaView() {
    return aView;
  }

  public void setaView(String aView) {
    this.aView = aView;
  }

  public int getaViewLength() {
    return aViewLength;
  }

  public void setaViewLength(int aViewLength) {
    this.aViewLength = aViewLength;
  }

  @Override
  public String toString() {
    return "NestedView [aView=" + aView + ", aViewLength=" + aViewLength + "]";
  }

}
