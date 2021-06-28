package com.github.schuettec.jbeanviews.features.basic.transitive;

import java.util.List;

public class B {
  private String contentB;

  private List<C> cs;

  public B() {
    super();
  }

  public B(String contentB, List<C> cs) {
    super();
    this.contentB = contentB;
    this.cs = cs;
  }

  public List<C> getCs() {
    return cs;
  }

  public void setCs(List<C> cs) {
    this.cs = cs;
  }

  public String getContentB() {
    return contentB;
  }

  public void setContentB(String contentB) {
    this.contentB = contentB;
  }

  @Override
  public String toString() {
    return "B [contentB=" + contentB + "]";
  }

}
