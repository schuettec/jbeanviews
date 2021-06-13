package com.github.schuettec.jbeanviews.features.ambiguousImplicitBindings.sameLevelHeuristic;

public class View {
  private Long id;
  private IdentifiableView identifiable;

  public View(Long id, IdentifiableView identifiable) {
    super();
    this.id = id;
    this.identifiable = identifiable;
  }

  public View() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public IdentifiableView getIdentifiable() {
    return identifiable;
  }

  public void setIdentifiable(IdentifiableView identifiable) {
    this.identifiable = identifiable;
  }

  @Override
  public String toString() {
    return "View [id=" + id + ", identifiable=" + identifiable + "]";
  }

}
