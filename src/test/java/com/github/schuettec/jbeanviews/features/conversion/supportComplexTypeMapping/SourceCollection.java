package com.github.schuettec.jbeanviews.features.conversion.supportComplexTypeMapping;

import java.util.Set;

public class SourceCollection {

  private Set<ComplexType> references;

  public SourceCollection(Set<ComplexType> references) {
    super();
    this.references = references;
  }

  public SourceCollection() {
    super();
  }

  public Set<ComplexType> getReferences() {
    return references;
  }

  public void setReferences(Set<ComplexType> references) {
    this.references = references;
  }

  @Override
  public String toString() {
    return "SourceCollection [references=" + references + "]";
  }

}
