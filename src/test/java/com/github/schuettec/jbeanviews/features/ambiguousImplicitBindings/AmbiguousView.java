package com.github.schuettec.jbeanviews.features.ambiguousImplicitBindings;

public class AmbiguousView {
  private String name;
  private String forename;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getForename() {
    return forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }

  @Override
  public String toString() {
    return "AmbiguousView [name=" + name + ", forename=" + forename + "]";
  }

}
