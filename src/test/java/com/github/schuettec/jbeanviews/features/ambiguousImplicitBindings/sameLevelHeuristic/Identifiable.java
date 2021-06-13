package com.github.schuettec.jbeanviews.features.ambiguousImplicitBindings.sameLevelHeuristic;

public class Identifiable {

  private Long id;

  public Identifiable(Long id) {
    super();
    this.id = id;
  }

  public Identifiable() {
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
    return "Identifiable [id=" + id + "]";
  }

}
