package com.github.schuettec.jbeanviews.features.conversion.supportComplexTypeMapping;

import java.util.List;

public class ViewCollection {
  private List<ComplexTypeView> references;

  public ViewCollection() {
    super();
  }

  public ViewCollection(List<ComplexTypeView> references) {
    super();
    this.references = references;
  }

  public List<ComplexTypeView> getReferences() {
    return references;
  }

  public void setReferences(List<ComplexTypeView> references) {
    this.references = references;
  }

  @Override
  public String toString() {
    return "ViewCollection [references=" + references + "]";
  }

}
