package com.github.schuettec.jbeanviews.regression.npeOnUnmappedReadOnlyProperty;

public class B {
  private String string;

  public B() {
    super();
  }

  public B(String string) {
    super();
    this.string = string;
  }

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
  }

}
