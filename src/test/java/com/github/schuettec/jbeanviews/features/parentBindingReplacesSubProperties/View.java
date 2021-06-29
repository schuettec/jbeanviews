package com.github.schuettec.jbeanviews.features.parentBindingReplacesSubProperties;

public class View {

  private UnMappableView unmappable;

  public View(UnMappableView unmappable) {
    super();
    this.unmappable = unmappable;
  }

  public View() {
    super();
  }

  public UnMappableView getUnmappable() {
    return unmappable;
  }

  public void setUnmappable(UnMappableView unmappable) {
    this.unmappable = unmappable;
  }

  @Override
  public String toString() {
    return "View [unmappable=" + unmappable + "]";
  }

}
