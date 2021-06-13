package com.github.schuettec.jbeanviews.features.conversion.supportComplexTypeMapping;

public class ComplexTypeView {

  private int length;

  public ComplexTypeView() {
    super();
  }

  public ComplexTypeView(int length) {
    super();
    this.length = length;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  @Override
  public String toString() {
    return "ComplexTypeView [length=" + length + "]";
  }

}
