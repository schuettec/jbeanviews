package com.github.schuettec.jbeanviews.features.basic.changePropertyNames;

/**
 *
 */
public class Source {

  private String string;

  public Source(String string) {
    super();
    this.string = string;
  }

  public Source() {
    super();
  }

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
  }

  @Override
  public String toString() {
    return "Source [string=" + string + "]";
  }

}
