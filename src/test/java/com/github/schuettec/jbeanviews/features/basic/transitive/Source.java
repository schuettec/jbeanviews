package com.github.schuettec.jbeanviews.features.basic.transitive;

public class Source {
  private A a;

  public Source() {
    super();
  }

  public Source(A a) {
    super();
    this.a = a;
  }

  public A getA() {
    return a;
  }

  public void setA(A a) {
    this.a = a;
  }

  @Override
  public String toString() {
    return "Source [a=" + a + "]";
  }

}
