package com.github.schuettec.jbeanviews.features.basic.transitive;

/**
 *
 */
public class A {
  private B b;
  private String contentA;

  public A(B b, String contentA) {
    super();
    this.b = b;
    this.contentA = contentA;
  }

  public A() {
    super();
  }

  public String getContentA() {
    return contentA;
  }

  public void setContentA(String contentA) {
    this.contentA = contentA;
  }

  public B getB() {
    return b;
  }

  public void setB(B b) {
    this.b = b;
  }

  @Override
  public String toString() {
    return "A [b=" + b + ", contentA=" + contentA + "]";
  }

}
