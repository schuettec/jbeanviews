package com.github.schuettec.jbeanviews.features.ambiguousImplicitBindings.sameLevelHeuristic;

public class IdentifiableView {

  private Long id;

  public IdentifiableView(Long id) {
    super();
    this.id = id;
  }

  public IdentifiableView() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "IdentifiableView [id=" + id + "]";
  }

}
