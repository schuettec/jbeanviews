package com.github.schuettec.jbeanviews.features.ambiguousImplicitBindings.sameLevelHeuristic;

public class Source {

  private Long id;
  private Identifiable identifiable;

  public Source(Long id, Identifiable identifiable) {
    super();
    this.id = id;
    this.identifiable = identifiable;
  }

  public Source() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Identifiable getIdentifiable() {
    return identifiable;
  }

  public void setIdentifiable(Identifiable identifiable) {
    this.identifiable = identifiable;
  }

  @Override
  public String toString() {
    return "Source [id=" + id + ", identifiable=" + identifiable + "]";
  }

}
