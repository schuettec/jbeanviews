package com.github.schuettec.jbeanviews.features.basic.transitive;

public class Source {
  private A a;

  private String omit;

  public Source() {
    super();
  }

  public Source(A a, String omit) {
    super();
    this.a = a;
    this.omit = omit;
  }

  public String getOmit() {
    return omit;
  }

  public void setOmit(String omit) {
    this.omit = omit;
  }

  public A getA() {
    return a;
  }

  public void setA(A a) {
    this.a = a;
  }

  @Override
  public String toString() {
    return "Source [a=" + a + ", omit=" + omit + "]";
  }

}
