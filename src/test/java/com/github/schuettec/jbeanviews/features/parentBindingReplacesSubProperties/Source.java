package com.github.schuettec.jbeanviews.features.parentBindingReplacesSubProperties;

public class Source {

  private UnMappableSource unmappable;

  public Source(UnMappableSource unmappable) {
    super();
    this.unmappable = unmappable;
  }

  public Source() {
    super();
  }

  public UnMappableSource getUnmappable() {
    return unmappable;
  }

  public void setUnmappable(UnMappableSource unmappable) {
    this.unmappable = unmappable;
  }

  @Override
  public String toString() {
    return "Source [unmappable=" + unmappable + "]";
  }

}
